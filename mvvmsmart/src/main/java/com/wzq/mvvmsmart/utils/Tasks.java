package com.wzq.mvvmsmart.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * 一些任务：需要运行在进程的UI主线程，或者运行在进程的HandlerThread
 */
public final class Tasks {

  private static Handler sMainHandler;

  private static final Object sLock = new Object();

  private static Handler sThreadHandler;


  public static boolean post2UI(Runnable r) {
    return sMainHandler.post(r);
  }


  public static boolean postDelayed2UI(Runnable r, long delayMillis) {
    return sMainHandler.postDelayed(r, delayMillis);
  }

  /**
   * 取消UI线程任务
   */
  public static void cancelTask(Runnable r) {
    initThread();
    sMainHandler.removeCallbacks(r);
  }


  public static boolean post2Thread(Runnable r) {
    initThread();
    return sThreadHandler.post(r);
  }


  public static boolean postDelayed2Thread(Runnable r, long delayMillis) {
    initThread();
    return sThreadHandler.postDelayed(r, delayMillis);
  }

  /**
   * 取消后台线程任务
   */
  public static void cancelThreadTask(Runnable r) {
    initThread();
    sThreadHandler.removeCallbacks(r);
  }

  /**
   * 内部接口
   */
  public static void init() {
    sMainHandler = new Handler(Looper.getMainLooper());
  }

  private static void initThread() {
    synchronized (sLock) {
      if (sThreadHandler == null) {
        HandlerThread handlerThread = new HandlerThread("daemon-handler-thread");
        handlerThread.start();
        sThreadHandler = new Handler(handlerThread.getLooper());
      }
    }
  }
}
