package com.wzq.sample.ui.login;

import com.wzq.mvvmsmart.base.BaseModelMVVM;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;

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

    public Completable login() {
      return new Completable() {
          @Override
          protected void subscribeActual(CompletableObserver observer) {

          }
      };
    }

    public void saveUserName(String name) {

    }

    public void savePassword(String pwd) {

    }
}
