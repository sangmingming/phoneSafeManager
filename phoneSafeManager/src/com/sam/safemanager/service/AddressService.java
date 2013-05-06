/**
 * 
 */
package com.sam.safemanager.service;



import java.lang.reflect.Method;


import com.android.internal.telephony.ITelephony;
import com.sam.safemanager.R;
import com.sam.safemanager.db.dao.BlackNumberDao;
import com.sam.safemanager.engine.NumberAddressService;
import com.sam.safemanager.ui.CallSmsActivity;
import com.sam.safemanager.util.Logger;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Sam
 * @date 2013-5-6
 * @weibo:码农明明桑
 */
public class AddressService extends Service {
	private TelephonyManager manager;
	private MyPhoneListener listener;
	private View view;
	private WindowManager windowManager;
	private SharedPreferences sp;
	private BlackNumberDao dao;
	private long firstRingTime;
	private long endRingTime;
	
	@Override
	public void onCreate() {
		super.onCreate();
		//注册系统电话管理服务的监听器
		listener = new MyPhoneListener();
		sp = getSharedPreferences("config", MODE_PRIVATE);
		dao = new BlackNumberDao(getApplicationContext());
		manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		manager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	@Override
	public void onDestroy() {
		manager.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
		super.onDestroy();
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class MyPhoneListener extends PhoneStateListener{

		/**
		 * 电话状态发生改变调用的方法
		 */
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch(state){
			case TelephonyManager.CALL_STATE_IDLE: //处于静止状态，无电话
				endRingTime = System.currentTimeMillis();
				long  calltime = endRingTime-firstRingTime;
				Logger.i("calltime ="+calltime);
				if(firstRingTime<endRingTime && calltime<5000 && calltime >0){
					Logger.i("响一声的电话");
					endRingTime = 0;
					firstRingTime = 0;
					// 弹出来notification 通知用户这是一个骚扰电话
					showNotification(incomingNumber);
				}
				
				if(view!=null){
					windowManager.removeView(view);
					view = null;
				}
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK: //
				if(view!=null){
					windowManager.removeView(view);
					view = null;
				}
				break;
			case TelephonyManager.CALL_STATE_RINGING://铃响状态
				firstRingTime = System.currentTimeMillis();
				if(dao.find(incomingNumber)){
					//挂断电话
					endCall();
					
					//deleteCallLog(incomingNumber);\
					
					//注册一个内容观察者 观察call_log的uri的信息 
					
					getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI, true, new MyObserver(new Handler(),incomingNumber));
					
				}else{
					String address = NumberAddressService.getAddress(incomingNumber);
					showLocation(address);
				}
				break;
			}
			super.onCallStateChanged(state, incomingNumber);
		}
		
	}
	
	private void showLocation(String address){
		WindowManager.LayoutParams params = new LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				0, 0, WindowManager.LayoutParams.TYPE_TOAST,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, 
				PixelFormat.TRANSLUCENT);
		params.gravity = Gravity.LEFT | Gravity.TOP;

        params.x =     sp.getInt("lastx", 0);
        params.y =     sp.getInt("lasty", 0);

        view = View.inflate(getApplicationContext(), R.layout.show_location, null);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_location);

        int backgroundid = sp.getInt("background", 0);
        if(backgroundid==0){
        	ll.setBackgroundResource(R.drawable.call_locate_gray);
        }else if(backgroundid==1){
        	ll.setBackgroundResource(R.drawable.call_locate_orange);
        }else {
        	ll.setBackgroundResource(R.drawable.call_locate_green);
        }
        
        TextView tv = (TextView) view.findViewById(R.id.tv_location);
        tv.setTextSize(24);
        tv.setText(address);
        windowManager.addView(view , params);
		
	}
	/**
	 * 弹出notification 通知用户添加黑名单号码
	 * @param incomingNumber
	 */
	public void showNotification(String incomingNumber) {
		//1. 获取notification的管理服务
		NotificationManager  manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		//2 把一个要想显示的notification 对象创建出来
		int icon =R.drawable.notification;
		CharSequence tickerText = "发现响一声号码";
		long when = System.currentTimeMillis();

		Notification notification = new Notification(icon, tickerText, when);
		// 3 .配置notification的一些参数
		Context context = getApplicationContext();
		CharSequence contentTitle = "响一声号码";
		CharSequence contentText = incomingNumber;
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		
		Intent notificationIntent = new Intent(this, CallSmsActivity.class);
		// 把响一声的号码 设置到intent对象里面
		notificationIntent.putExtra("number", incomingNumber);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		
		// 4. 通过manger把notification 激活
		manager.notify(0, notification);
	}

	/**
	 * 根据电话号码删除呼叫记录
	 * @param incomingNumber 要删除呼叫记录的号码
	 */
	public void deleteCallLog(String incomingNumber) {
		ContentResolver  resolver = getContentResolver();
		Cursor cursor = resolver.query(CallLog.Calls.CONTENT_URI, null, "number=?", new String[]{incomingNumber}, null);
		if(cursor.moveToFirst()){//查询到了呼叫记录
			String id =cursor.getString(cursor.getColumnIndex("_id"));
			resolver.delete(CallLog.Calls.CONTENT_URI, "_id=?",new String[]{id});
		}
	}

	public void endCall() {
		try {
			Method method = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
			IBinder binder = (IBinder)method.invoke(null, new Object[]{TELEPHONY_SERVICE});
			ITelephony telephony = ITelephony.Stub.asInterface(binder);
			telephony.endCall();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private class MyObserver extends ContentObserver
	{
		private String incomingnumber;
		public MyObserver(Handler handler,String incomingnumber) {
			super(handler);
			this.incomingnumber = incomingnumber;
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			deleteCallLog(incomingnumber);
			
			//当删除了呼叫记录后 反注册内容观察者
			getContentResolver().unregisterContentObserver(this);
		}
		
	}
	

}
