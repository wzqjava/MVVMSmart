package com.wzq.mvvmsmart.net.listener;


import com.wzq.mvvmsmart.net.base.BaseResponse;

/**
 * created 王志强 2020.04.30
 * 有些开发者喜欢用Listener, 可以在写具体接口的时候
 * 在viewMode中的Observer里使用这个接口进行回调, (但建议使用LiveData观察ViewMode,不建议用listener)
 */
public interface OnServerResponseListener<T> {
    /**
     * 服务器成功返回
     * @param what 请求标记位
     * @param isQualified 返回的数据是否合格
     * @param baseResponse 网络返回Response
     */
    void success(int what, boolean isQualified, BaseResponse<T> baseResponse);

    /**
     * 返回错误
     *
     * @param what
     * @param throwable 错误类型有可能是移动端也有可能是后端
     */
    void error(int what, Throwable throwable);

    /**
     * 重连
     */
    void reTry(int what);
}
