package com.wzq.mvvmsmart.utils

import android.text.TextUtils
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object KLog {
    private var IS_SHOW_LOG = false
    private const val DEFAULT_MESSAGE = "execute"
    private val LINE_SEPARATOR = System.getProperty("line.separator")
    private const val JSON_INDENT = 4
    private const val V = 0x1
    private const val D = 0x2
    private const val I = 0x3
    private const val W = 0x4
    private const val E = 0x5
    private const val A = 0x6
    private const val JSON = 0x7
    fun init(isShowLog: Boolean) {
        IS_SHOW_LOG = isShowLog
    }

    fun v() {
        printLog(V, null, DEFAULT_MESSAGE)
    }

    fun v(msg: Any?) {
        printLog(V, null, msg)
    }

    fun v(tag: String?, msg: String?) {
        printLog(V, tag, msg)
    }

    fun d() {
        printLog(D, null, DEFAULT_MESSAGE)
    }

    fun d(msg: Any?) {
        printLog(D, null, msg)
    }

    fun d(tag: String?, msg: Any?) {
        printLog(D, tag, msg)
    }

    fun i() {
        printLog(I, null, DEFAULT_MESSAGE)
    }

    fun i(msg: Any?) {
        printLog(I, null, msg)
    }

    fun i(tag: String?, msg: Any?) {
        printLog(I, tag, msg)
    }

    fun w() {
        printLog(W, null, DEFAULT_MESSAGE)
    }

    fun w(msg: Any?) {
        printLog(W, null, msg)
    }

    fun w(tag: String?, msg: Any?) {
        printLog(W, tag, msg)
    }

    fun e() {
        printLog(E, null, DEFAULT_MESSAGE)
    }

    fun e(msg: Any?) {
        printLog(E, null, msg)
    }

    fun e(tag: String?, msg: Any?) {
        printLog(E, tag, msg)
    }

    fun a() {
        printLog(A, null, DEFAULT_MESSAGE)
    }

    fun a(msg: Any?) {
        printLog(A, null, msg)
    }

    fun a(tag: String?, msg: Any?) {
        printLog(A, tag, msg)
    }

    fun json(jsonFormat: String?) {
        printLog(JSON, null, jsonFormat)
    }

    fun json(tag: String?, jsonFormat: String?) {
        printLog(JSON, tag, jsonFormat)
    }

    private fun printLog(type: Int, tagStr: String?, objectMsg: Any?) {
        if (!IS_SHOW_LOG) {
            return
        }
        val stackTrace = Thread.currentThread().stackTrace
        val index = 4
        val className = stackTrace[index].fileName
        var methodName = stackTrace[index].methodName
        val lineNumber = stackTrace[index].lineNumber
        val tag = tagStr ?: className
        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1)
        val stringBuilder = StringBuilder()
        stringBuilder.append("[ (").append(className).append(":").append(lineNumber).append(")#").append(methodName).append(" ] ")
        val msg: String = objectMsg?.toString() ?: "Log with null Object"
        if ( type != JSON) {
            stringBuilder.append(msg)
        }
        val logStr = stringBuilder.toString()
        when (type) {
            V -> Log.v(tag, logStr)
            D -> Log.d(tag, logStr)
            I -> Log.i(tag, logStr)
            W -> Log.w(tag, logStr)
            E -> Log.e(tag, logStr)
            A -> Log.wtf(tag, logStr)
            JSON -> {
                if (TextUtils.isEmpty(msg)) {
                    Log.d(tag, "Empty or Null json content")
                    return
                }
                var message: String? = null
                try {
                    if (msg.startsWith("{")) {
                        val jsonObject = JSONObject(msg)
                        message = jsonObject.toString(JSON_INDENT)
                    } else if (msg.startsWith("[")) {
                        val jsonArray = JSONArray(msg)
                        message = jsonArray.toString(JSON_INDENT)
                    }
                } catch (e: JSONException) {
                    e(tag, """
     ${e.cause!!.message}
     $msg
     """.trimIndent())
                    return
                }
                printLine(tag, true)
                message = logStr + LINE_SEPARATOR + message
                val lines = message.split(LINE_SEPARATOR!!).toTypedArray()
                val jsonContent = StringBuilder()
                for (line in lines) {
                    jsonContent.append("║ ").append(line).append(LINE_SEPARATOR)
                }
                //Log.i(tag, jsonContent.toString());
                if (jsonContent.toString().length > 3200) {
                    Log.w(tag, "jsonContent.length = " + jsonContent.toString().length)
                    val chunkCount = jsonContent.toString().length / 3200
                    var i = 0
                    while (i <= chunkCount) {
                        val max = 3200 * (i + 1)
                        if (max >= jsonContent.toString().length) {
                            Log.w(tag, jsonContent.toString().substring(3200 * i))
                        } else {
                            Log.w(tag, jsonContent.toString().substring(3200 * i, max))
                        }
                        i++
                    }
                } else {
                    Log.w(tag, jsonContent.toString())
                }
                printLine(tag, false)
            }
        }
    }

    private fun printLine(tag: String, isTop: Boolean) {
        if (isTop) {
            Log.w(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════")
        } else {
            Log.w(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════")
        }
    }
}