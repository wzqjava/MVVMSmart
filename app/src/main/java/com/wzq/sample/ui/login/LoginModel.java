package com.wzq.sample.ui.login;

import android.annotation.SuppressLint;

import com.wzq.mvvmsmart.base.BaseModelMVVM;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * <p>作者：王志强<p>
 * <p>创建时间：2020/4/13<p>
 * <p>文件描述：<p>
 */
class LoginModel extends BaseModelMVVM {


    String getUserName() {
        return "1";
    }

    String getPassword() {
        return "1";
    }

    @SuppressLint("CheckResult")
    public Observable login() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });

    }

    void saveUserName(String name) {

    }

    void savePassword(String pwd) {

    }
}
