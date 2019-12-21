package com.wzq.mvvmsmart.http.download;

import android.util.Log;

import com.jeremyliao.liveeventbus.LiveEventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import okhttp3.ResponseBody;


public abstract class ProgressCallBack<T> {

    private String destFileDir; // 本地文件存放路径
    private String destFileName; // 文件名

    public ProgressCallBack(LifecycleOwner lifecycleOwner, String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        subscribeLoadProgress(lifecycleOwner);
    }

    public abstract void onSuccess(T t);

    public abstract void progress(long progress, long total);

    public void onStart() {
    }

    public void onCompleted() {
    }

    public abstract void onError(Throwable e);

    public void saveFile(ResponseBody body) {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = body.byteStream();
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                Log.e("saveFile", e.getMessage());
            }
        }
    }

    /**
     * 订阅加载的进度条
     *
     * @param lifecycleOwner
     */
    public void subscribeLoadProgress(LifecycleOwner lifecycleOwner) {
        LiveEventBus
                .get("key_name", DownLoadStateBean.class)
                .observe(lifecycleOwner, new Observer<DownLoadStateBean>() {
                    @Override
                    public void onChanged(@Nullable DownLoadStateBean progressLoadBean) {
                        progress(progressLoadBean.getBytesLoaded(), progressLoadBean.getTotal());
                    }
                });
    }


}