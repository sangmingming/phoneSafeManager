package com.sam.safemanager.ui;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.sam.safemanager.R;
import com.sam.safemanager.util.ImageUtil;
import com.sam.safemanager.util.TextFormater;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TrafficManagerActivity extends Activity {

	private TextView tv_mobile_total;
	private TextView tv_wifi_total;
	private ListView lv_content;
	private String mobiletraffic;
	private String wifitraffic;
	private PackageManager pm;
	private  TrafficAdapter adapter;
	
	private Timer timer;
	private TimerTask task;
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			adapter.notifyDataSetChanged();
		}
		
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		pm = getPackageManager();
		setContentView(R.layout.traffic_manager);
		tv_mobile_total = (TextView) this.findViewById(R.id.tv_mobile_total);
		tv_wifi_total = (TextView) this.findViewById(R.id.tv_wifi_total);
		lv_content = (ListView) this.findViewById(R.id.content);
		
		setTotalDataInfo();
		 adapter = new TrafficAdapter();
		    
	   
	    lv_content.addHeaderView(View.inflate(this, R.layout.traffic_title, null));
	    lv_content.setAdapter(adapter);
	}

	private void setTotalDataInfo() {
		long mobilerx = TrafficStats.getMobileRxBytes();
		long mobiletx = TrafficStats.getMobileTxBytes();
		
		long mobiletotal = mobilerx+mobiletx;
		
		mobiletraffic = (TextFormater.getDataSize(mobiletotal));
		tv_mobile_total.setText(mobiletraffic);
		
		 long totalrx = TrafficStats.getTotalRxBytes();
		 long toatltx = TrafficStats.getTotalTxBytes();
		 
		 long total = toatltx+ totalrx;
		 
		 
		 long wifitotal = total-mobiletotal;
		 
		 wifitraffic =  (TextFormater.getDataSize(wifitotal));
		 tv_wifi_total.setText(wifitraffic);
	}

	
	
	@Override
	protected void onStart() {
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				
				// 发送一个消息给主线程
				Message msg = Message.obtain();
				handler.sendMessage(msg);
			}
		};
		timer.schedule(task, 1000, 2000);
		
		super.onStart();
	}





	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		timer.cancel();
		timer =null;
		task = null;
	}





	private class TrafficAdapter extends BaseAdapter{
		 List<ResolveInfo> resovleInfos;
		
		
		
		
		public TrafficAdapter() {
			super();

			 PackageManager pm = getPackageManager();
		     Intent intent = new Intent();
		     intent.setAction("android.intent.action.MAIN");
		     intent.addCategory("android.intent.category.LAUNCHER");
		     resovleInfos  =  pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return resovleInfos.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
		    View view = null;
		    if(convertView==null){
		    	view = View.inflate(getApplicationContext(), R.layout.traffic_item, null);
		    }else{
		    	view = convertView;
		    }
		    ViewHolder holder = new ViewHolder();
		    holder.iv = (ImageView) view.findViewById(R.id.iv_traffic_icon);
		    holder.tv_name =(TextView) view.findViewById(R.id.tv_traffic_name);
		    holder.tv_tx = (TextView) view.findViewById(R.id.tv_traffic_tx);
		    holder.tv_rx = (TextView) view.findViewById(R.id.tv_traffic_rx);
		    ResolveInfo info = resovleInfos.get(position);
		    
			String appname = info.loadLabel(pm).toString();
        	holder.tv_name.setText(appname);
			   
        	Drawable appicon = info.loadIcon(pm);
        	Bitmap resizeicon = ImageUtil.getResizedBitmap((BitmapDrawable) appicon,TrafficManagerActivity.this);
        	holder.iv.setImageBitmap(resizeicon);
        	String packname = info.activityInfo.packageName;
        	try {
			  PackageInfo packinfo =	pm.getPackageInfo(packname, 0);
			  int uid = packinfo.applicationInfo.uid;
			  holder.tv_rx.setText( TextFormater.getDataSize( TrafficStats.getUidRxBytes(uid)));
			  holder.tv_tx.setText( TextFormater.getDataSize( TrafficStats.getUidTxBytes(uid)));
			   
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return view;
		}
		
	}
	
	static class ViewHolder{
		ImageView iv;
		TextView tv_name;
		TextView tv_tx;
		TextView tv_rx;
		
	}
}
