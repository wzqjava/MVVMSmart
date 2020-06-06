package com.wzq.sample.utils

/**
 * 作者：王志强
 * 创建时间：2019/12/23
 * 文件描述：
 */
object TestUtils {
    fun getGirlImgUrl(): String {
        val index = (Math.random() * girlImgs.size).toInt()
        return girlImgs[index]
    }

    var girlImgs = arrayOf( // --------------------sex girl------------------------
            "http://ww1.sinaimg.cn/large/0065oQSqly1g2pquqlp0nj30n00yiq8u.jpg",
            "https://ww1.sinaimg.cn/large/0065oQSqly1g2hekfwnd7j30sg0x4djy.jpg",
            "https://pixabay.com/get/55e0d0404a57ad14f6da8c7dda79367a1d39d8e65b526c48702873d2954ecc5ebf_640.jpg",
            "https://pixabay.com/get/54e6d2444d55ab14f6da8c7dda79367a1d39d8e65b526c48702873d2954ecc5ebf_640.jpg",
            "https://pixabay.com/get/52e9dc444f54b108f5d08460962933761339dded504c704c72277dd69444c35f_640.jpg",
            "https://pixabay.com/get/54e2d5454856a414f6da8c7dda79367a1d39d8e65b526c48702873d2954ecc5ebf_640.jpg",
            "https://pixabay.com/get/52e6dd454955aa14f6da8c7dda79367a1d39d8e65b526c48702873d2954ecc5ebf_640.jpg",
            "https://pixabay.com/get/54e8d54b4a55af14f6da8c7dda79367a1d39d8e65b526c48702873d2954ecc5ebf_640.jpg",
            "https://pixabay.com/get/52e6dc4b4e55ac14f6da8c7dda79367a1d39d8e65b526c48702873d2954ecc5ebf_640.jpg",
            "https://pixabay.com/get/52e6dc4b4e54ab14f6da8c7dda79367a1d39d8e65b526c48702873d2954ecc5ebf_640.jpg",
            "https://pixabay.com/get/54e7d5474854ae14f6da8c7dda79367a1d39d8e65b526c48702873d2954ecc5ebf_640.jpg",  //----------------------not sex girl----------
            "http://f.expoon.com/sub/news/2016/01/21/887844_230x162_0.jpg",
            "http://f.expoon.com/sub/news/2016/01/21/580828_230x162_0.jpg",
            "http://f.expoon.com/sub/news/2016/01/21/745921_230x162_0.jpg",
            "http://f.expoon.com/sub/news/2016/01/21/158040_230x162_0.jpg",
            "http://f.expoon.com/sub/news/2016/01/21/158040_230x162_0.jpg",
            "http://f.expoon.com/sub/news/2016/01/21/865222_230x162_0.jpg",
            "http://f.expoon.com/sub/news/2016/01/20/370858_230x162_0.jpg",
            "http://f.expoon.com/sub/news/2016/01/20/868385_230x162_0.jpg",
            "http://f.expoon.com/sub/news/2016/01/20/768695_230x162_0.jpg"
    )
}