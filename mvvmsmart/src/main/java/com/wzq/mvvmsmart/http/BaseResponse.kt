package com.wzq.mvvmsmart.http

/**
 * 该类仅供参考，实际业务返回的固定字段, 根据需求来定义，
 */
class BaseResponse<T> {
    var code = 0
    var message: String? = null
    var result: T? = null
        private set
    val isOk: Boolean
        get() = code == 0

    fun setResult(result: T) {
        this.result = result
    }

}