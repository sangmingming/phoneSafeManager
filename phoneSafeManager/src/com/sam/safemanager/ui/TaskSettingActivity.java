package com.sam.safemanager.ui;


import com.sam.safemanager.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class TaskSettingActivity extends Activity {
	private TextView tv_show_system_app,tv_kill_process;
	private CheckBox cb_show_system_checked,cb_kill_process;
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_setting);
		cb_show_system_checked = 	(CheckBox) this.findViewById(R.id.cb_show_system_checked);
		tv_show_system_app = 	(TextView) this.findViewById(R.id.tv_show_system_app);
		cb_kill_process = 	(CheckBox) this.findViewById(R.id.cb_kill_process);
		tv_kill_process = 	(TextView) this.findViewById(R.id.tv_kill_process);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		
		boolean showsystemapp = sp.getBoolean("showsystemapp", false);
		if(showsystemapp){
			cb_show_system_checked.setChecked(true);
			tv_show_system_app.setText("显示系统进程");
		}else{
			cb_show_system_checked.setChecked(false);
			tv_show_system_app.setText("不显示系统进程");
		}
		boolean killprocess = sp.getBoolean("killprocess", false);
		if(killprocess){
			cb_kill_process.setChecked(true);
			tv_kill_process.setText("锁屏自动清理内存");
		}else{
			cb_kill_process.setChecked(false);
			tv_kill_process.setText("锁屏不清理内存");
		}
		cb_show_system_checked.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					tv_show_system_app.setText("显示系统进程");
					Editor editor = sp.edit();
					editor.putBoolean("showsystemapp", true);
					editor.commit();
					setResult(200);
				}else{
					tv_show_system_app.setText("不显示系统进程");
					Editor editor = sp.edit();
					editor.putBoolean("showsystemapp", false);
					editor.commit();
					setResult(200);
				}
			}
		});
		cb_kill_process.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					tv_kill_process.setText("锁屏清理内存");
					Editor editor = sp.edit();
					editor.putBoolean("killprocess", true);
					editor.commit();
				}else{
					tv_kill_process.setText("不显示系统进程");
					Editor editor = sp.edit();
					editor.putBoolean("killprocess", false);
					editor.commit();

				}
				
			}
		});
	}

}
