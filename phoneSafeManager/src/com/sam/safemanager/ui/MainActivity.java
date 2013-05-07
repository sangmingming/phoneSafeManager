/**
 * 
 */
package com.sam.safemanager.ui;

import com.sam.safemanager.R;
import com.sam.safemanager.adapter.MainUIAdapter;
import com.sam.safemanager.util.Logger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * @author Sam
 * @date 2013-4-21
 * @weibo:码农明明桑
 */
public class MainActivity extends Activity implements OnItemClickListener{
	private GridView gv_main;
	private MainUIAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_main);
		
		gv_main = (GridView) findViewById(R.id.gv_main);
		adapter = new MainUIAdapter(this);
		gv_main.setAdapter(adapter);
		gv_main.setOnItemClickListener(this);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch(position){
		case 0:   //手机防盗
			Intent toLost = new Intent(MainActivity.this,LostProtectedActivity.class);
			startActivity(toLost);
			break;
		case 1:
			Logger.i("通讯卫士");
			Intent callsmsIntent = new Intent(MainActivity.this,
					CallSmsActivity.class);
			startActivity(callsmsIntent);
			break;
		case 2:
			Logger.i("软件管理");
			Intent appmanagerIntent = new Intent(MainActivity.this,
					AppManagerActivity.class);
			startActivity(appmanagerIntent);
			break;
		case 3:
			Logger.i("任务管理");
			Intent taskmanagerIntent = new Intent(MainActivity.this,
					TaskManagerActivity.class);
			startActivity(taskmanagerIntent);
			break;
		case 4:
			Logger.i("流量管理");
			Intent trafficmanagerIntent = new Intent(MainActivity.this,
					TrafficManagerActivity.class);
			startActivity(trafficmanagerIntent);
			break;
		case 5:
			Logger.i("系统优化");
			Intent cleanIntent = new Intent(MainActivity.this,CleanActivity.class);
			startActivity(cleanIntent);
			break;
		case 6:
			Logger.i("高级工具");
			Intent atoolsIntent = new Intent(MainActivity.this,AToolsActivity.class);
			startActivity(atoolsIntent);
			break;
		case 7:
			Logger.i("设置中心");
			break;
		
		}
		
	}
	

}
