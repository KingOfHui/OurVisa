package com.eshel.ourvisa.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Process;

import com.eshel.ourvisa.BaseApplication;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by guoshiwen on 2017/10/13.
 */

public class ProcessUtil {

	private static ActivityManager mAm;

	/**
	 * 获取进程ID
	 */
	public static int getPid(){
		return android.os.Process.myPid();
	}

	/**
	 * 获取线程ID
	 */
	public static int getTid(){
		return Process.myTid();
	}

	/**
	 * 判断 APP 是否在前台
	 */
	public static boolean appIsForeground(){
		Context context = BaseApplication.getContext();
		if(ObjUtil.isNull(mAm))
			mAm = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);

		if(ObjUtil.isNull(mAm))
			return false;
		List<ActivityManager.RunningTaskInfo> tasks = mAm.getRunningTasks(1);
		if(!tasks.isEmpty()){
			ComponentName topActivity = tasks.get(0).topActivity;
			if(topActivity.getPackageName().equals(context.getPackageName())){
				return true;
			}
		}
		return false;
	}

	public static void moveAppToForeground(){
		Context context = BaseApplication.getContext();
		if(ObjUtil.isNull(mAm))
			mAm = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
		if(ObjUtil.isNull(mAm))
			return;
		List<ActivityManager.RunningTaskInfo> tasks = mAm.getRunningTasks(1000);
		for (ActivityManager.RunningTaskInfo task : tasks) {
			if(task.topActivity.getPackageName().equals(context.getPackageName())){
				mAm.moveTaskToFront(task.id, ActivityManager.MOVE_TASK_WITH_HOME);
			}
		}
	}
	public static String getCurrentProcessName(Context context){
		int pid = android.os.Process.myPid();
		if(ObjUtil.isNull(mAm))
			mAm = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
		if(ObjUtil.isNull(mAm))
			return "";
		for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : mAm.getRunningAppProcesses()) {
			if(runningAppProcessInfo.pid == pid){
				return runningAppProcessInfo.processName;
			}
		}
		return "";
	}

	public static boolean isMainProcess(Context context) {
		if (context == null) {
			return false;
		}

		String packageName = context.getApplicationContext().getPackageName();
		String processName = getCurrentProcessName(context);
		return packageName.equals(processName);
	}
}