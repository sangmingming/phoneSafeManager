package com.sam.safemanager.ui;


import com.sam.safemanager.R;
import com.sam.safemanager.receiver.MyAdmin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class SetupGudie4Activity extends Activity implements OnClickListener {
	private CheckBox cb_isprotecting;
	private Button bt_setup_finish;
	private Button bt_pre;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setupguide4);
		cb_isprotecting =  (CheckBox) this.findViewById(R.id.cb_isprotecting);
		bt_setup_finish = (Button)this.findViewById(R.id.bt_setup_finish);
		bt_pre = (Button)this.findViewById(R.id.bt_pre);
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		// 初始化checkbox的状态
		boolean isprotecting = sp.getBoolean("isprotecting", false);
		if(isprotecting){
			cb_isprotecting.setText("手机防盗保护中");
			cb_isprotecting.setChecked(true);
		}
		
		cb_isprotecting.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					cb_isprotecting.setText("手机防盗保护中");
					Editor editor = sp.edit();
					editor.putBoolean("isprotecting", true);
					editor.commit();
				}else {
					cb_isprotecting.setText("没有开启防盗保护");
					Editor editor = sp.edit();
					editor.putBoolean("isprotecting", false);
					editor.commit();
				}
				
			}
		});
		
		bt_pre.setOnClickListener(this);
		bt_setup_finish.setOnClickListener(this);
		
	}
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_setup_finish:

			
			if(cb_isprotecting.isChecked()){
				finish();
				finishSetup();
			}else{
				AlertDialog.Builder builder = new Builder(SetupGudie4Activity.this);
				builder.setTitle("提醒");
				builder.setMessage("强烈建议开启手机防盗,是否完成设置?");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){

					public void onClick(DialogInterface dialog, int which) {
						finish();
						finishSetup();
					}});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

					public void onClick(DialogInterface dialog, int which) {
						
					}});
				builder.create().show();
				return;
			}
			
			
			
			break;
		case R.id.bt_pre:
			Intent intent3 = new Intent(this,SetupGudie3Activity.class);
			//一定要把当前的activity从任务栈里面移除
			finish();
			startActivity(intent3);
			//设置activity切换时候的动画效果
			overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
			break;

		}
		
	}
	private void finishSetup() {
		// 设置一个标示 用户已经完成过设置向导 
		
		Editor editor = sp.edit();
		editor.putBoolean("issteupalready", true);
		editor.commit();
		DevicePolicyManager manager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
//TODO
			ComponentName mAdminName = new ComponentName(this, MyAdmin.class);

			if (!manager.isAdminActive(mAdminName)) {
				Intent intent = new Intent(
						DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
				intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName);
				startActivity(intent);
			}
	}

}
