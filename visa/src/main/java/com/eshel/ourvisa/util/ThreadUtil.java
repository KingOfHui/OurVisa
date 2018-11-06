package com.eshel.ourvisa.util;

import android.os.Handler;
import android.os.Looper;

import com.eshel.ourvisa.BaseApplication;

/**
 * Created by guoshiwen on 2017/10/13.
 */

public class ThreadUtil {
	public static boolean isMainThread(){
		return Looper.myLooper() == Looper.getMainLooper();
	}

	public static Handler getHandler() {
		return BaseApplication.getContext().getHandler();
	}

	public static void postMain(Runnable runnable){
		getHandler().post(runnable);
	}

	public static void postMainDelayed(Runnable runnable, long time){
		getHandler().postDelayed(runnable,time);
	}
}
