package com.sam.safemanager.receiver;

import com.sam.safemanager.util.TaskUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class LockScreenReceiver extends BroadcastReceiver {

	private static final String TAG = "LockScreenReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {

		//在屏幕锁屏的时候 会调用这个onReceve 方法

		Log.i(TAG,"锁屏");
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		boolean killprocess = sp.getBoolean("killprocess", false);
		if(killprocess){
			TaskUtil.killAllProcess(context);
			Log.i(TAG,"杀死所有进程");
		}
	}
}
