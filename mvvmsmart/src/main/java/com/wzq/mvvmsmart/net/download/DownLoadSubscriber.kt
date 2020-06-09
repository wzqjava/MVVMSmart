package com.wzq.mvvmsmart.net.download

import io.reactivex.observers.DisposableObserver

class DownLoadSubscriber<T>(private val fileCallBack: ProgressCallBack<*>?) : DisposableObserver<T>() {
    public override fun onStart() {
        super.onStart()
        fileCallBack?.onStart()
    }

    override fun onComplete() {
        fileCallBack?.onCompleted()
    }

    override fun onError(e: Throwable) {
        fileCallBack?.onError(e)
    }

    override fun onNext(t: T) {
        fileCallBack?.onSuccess(t)
    }

}