package com.wzq.sample.bean

/**
 *<p>作者：王志强<p>
 * <p>创建时间：2020/6/4<p>
 * <p>文件描述：<p>
 *
 */
class NewsData : ArrayList<NewsDataItem>()

data class NewsDataItem(val news_id: String, val news_summary: String, val news_title: String, val pic_url: String)