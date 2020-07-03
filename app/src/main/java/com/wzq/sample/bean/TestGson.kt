package com.wzq.sample.bean


import com.google.gson.annotations.SerializedName

/**
 * @hloong 测试GSON解析
 */
data class TestGson(
    @SerializedName("error")
    var error: String = "",
    @SerializedName("message")
    var message: Long =0,
    @SerializedName("status")
    var status: Int = 0,
    @SerializedName("success")
    var success: Boolean = false,
    @SerializedName("timestamp")
    var timestamp:Double=0.0,
    @SerializedName("type")
    var type: Float = 0f
)