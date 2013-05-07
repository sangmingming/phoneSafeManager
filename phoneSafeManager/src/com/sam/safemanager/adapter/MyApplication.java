/**
 * 
 */
package com.sam.safemanager.adapter;

import com.sam.safemanager.bean.TaskInfo;
import com.sam.safemanager.receiver.LockScreenReceiver;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * @author Sam
 * @date 2013-5-7
 * @weibo:ÂëÅ©Ã÷Ã÷É£
 */
public class MyApplication extends Application {
	public TaskInfo taskinfo;

	@Override
	public void onCreate() {
		super.onCreate();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        filter.setPriority(1000);
        LockScreenReceiver recevier = new LockScreenReceiver();
        registerReceiver(recevier, filter);
	}
}
