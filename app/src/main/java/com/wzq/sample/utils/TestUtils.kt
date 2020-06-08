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

    var girlImgs = arrayOf(
            // --------------------sex girl------------------------
//            "xxx",
            "http://ww1.sinaimg.cn/large/0065oQSqly1g2pquqlp0nj30n00yiq8u.jpg",
            "https://ww1.sinaimg.cn/large/0065oQSqly1g2hekfwnd7j30sg0x4djy.jpg",
            "https://gank.io/images/aebca647b3054757afd0e54d83e0628e",
            "https://gank.io/images/f4f6d68bf30147e1bdd4ddbc6ad7c2a2",
            "https://gank.io/images/dc75cbde1d98448183e2f9514b4d1320",
            "https://gank.io/images/6b2efa591564475fb8bc32291fb0007c",
            "https://gank.io/images/d6bba8cf5b8c40f9ad229844475e9149",
            "https://gank.io/images/9fa43020cf724c69842eec3e13f6d21c",
            "https://gank.io/images/19c99c447e0a40a6b3ff89951957cfb1",
            "https://gank.io/images/f0c192e3e335400db8a709a07a891b2e",
            "https://gank.io/images/bdb35e4b3c0045c799cc7a494a3db3e0",
            "https://gank.io/images/3fdbaffdf3374578a82313621123dace",
            "https://gank.io/images/f08e8ab6030d41a0ada3e6cecea0e60c",
            "https://gank.io/images/e92911f5ff9446d5a899b652b1934b93",
            //----------------------not sex girl----------
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