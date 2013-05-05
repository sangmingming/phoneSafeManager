package com.sam.safemanager.engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;

/**
 * 保证这个类只存在一个实例 
 * @author zehua
 *
 */
public class GPSInfoProvider {
	LocationManager manager;
	private static GPSInfoProvider mGPSInfoProvider;
	private static Context context;
	private static MyLoactionListener listener;
  //1.私有化构造方法
	
	private GPSInfoProvider(){};
	
  //2. 提供一个静态的方法 可以返回他的一个实例
	public static synchronized GPSInfoProvider getInstance(Context context){
		if(mGPSInfoProvider==null){
			mGPSInfoProvider = new GPSInfoProvider();
			GPSInfoProvider.context = context;
		}
		return mGPSInfoProvider;
	}
	
	
	// 获取gps 信息 
	public String getLocation(){
		manager =(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		//manager.getAllProviders(); // gps //wifi //
		String provider = getProvider(manager);
		// 注册位置的监听器 
		manager.requestLocationUpdates(provider,60000, 50, getListener());
		
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		String location = sp.getString("location", "");
		return location;
	}
	

	
	// 停止gps监听
	public void stopGPSListener(){
		manager.removeUpdates(getListener());
	}
	
	
	private synchronized MyLoactionListener getListener(){
		if(listener==null){
			listener = new MyLoactionListener();
		}
		return listener;
	}
	
	private class MyLoactionListener implements LocationListener{

		/**
		 * 当手机位置发生改变的时候 调用的方法
		 */
		public void onLocationChanged(Location location) {
			String latitude ="latitude "+ location.getLatitude(); //weidu 
			String longtitude = "longtitude "+ location.getLongitude(); //jingdu
			SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putString("location", latitude+" - "+ longtitude);
			editor.commit(); //最后一次获取到的位置信息 存放到sharedpreference里面
		}

		/**
		 * 某一个设备的状态发生改变的时候 调用 可用->不可用  不可用->可用
		 */
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}

		/**
		 * 某个设备被打开
		 */
		public void onProviderEnabled(String provider) {
			
		}

		/**某个设备被禁用
		 * 
		 */
		public void onProviderDisabled(String provider) {
			
		}
		
	}
	
	/**\
	 * 
	 * @param manager 位置管理服务
	 * @return 最好的位置提供者
	 */
	private String getProvider(LocationManager manager){
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
		criteria.setSpeedRequired(true);
		criteria.setCostAllowed(true);
		return  manager.getBestProvider(criteria, true);
	}
}

















