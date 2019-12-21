package com.wzq.mvvmsmart.http.download;

import com.jeremyliao.liveeventbus.LiveEventBus;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 服务器返回的response的自定义类，能拿到当前下载的文件大小和类型等信息
 * ProgressInterceptor里用到
 */
public class ProgressResponseBody extends ResponseBody {
    private ResponseBody responseBody;

    private BufferedSource bufferedSource;
    private String tag;

    public ProgressResponseBody(ResponseBody responseBody) {
        this.responseBody = responseBody;
    }

    public ProgressResponseBody(ResponseBody responseBody, String tag) {
        this.responseBody = responseBody;
        this.tag = tag;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long bytesReaded = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                //使用RxBus的方式，实时发送当前已读取(上传/下载)的字节数据
//                RxBus.getDefault().post(new DownLoadStateBean(contentLength(), bytesReaded, tag));
                LiveEventBus.get("key_name").post(new DownLoadStateBean(contentLength(), bytesReaded, tag));
                return bytesRead;
            }
        };
    }
}
