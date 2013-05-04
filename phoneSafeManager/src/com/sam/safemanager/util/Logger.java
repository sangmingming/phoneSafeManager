package com.sam.safemanager.util;

import android.util.Log;

/**
 * 封装，用于指定指定等级的log信息的输出
 * @author Sam
 * @date 2013-4-24
 * @weibo:码农明明桑
 */
public class Logger {
	public static final int LOG_LEVEL = 0;
	public static final int VERBOSE = 1;
	public static final int DEBUG = 2;
	public static final int INFO = 3;
	public static final int WARN = 4;
	public static final int ERROR = 5;
	public static final int ASSERT = 6;
	public static final String TAG = "com.sam.safemanager";
	
	public static void v(String msg){
		if(VERBOSE>=LOG_LEVEL){
			Log.v(TAG, msg);
		}
	}
	
	public static void d(String msg){
		if(DEBUG>=LOG_LEVEL){
			Log.d(TAG, msg);
		}
	}
	
	public static void i(String msg){
		if(INFO>=LOG_LEVEL){
			Log.i(TAG, msg);
		}
	}
	
	public static void w(String msg){
		if(WARN>=LOG_LEVEL){
			Log.w(TAG, msg);
		}
	}
	
	public static void e(String msg){
		if(ERROR>=LOG_LEVEL){
			Log.e(TAG, msg);
		}
	}
	
}
