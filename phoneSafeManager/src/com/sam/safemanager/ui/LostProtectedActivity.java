/**
 * 
 */
package com.sam.safemanager.ui;


import com.sam.safemanager.R;
import com.sam.safemanager.util.Logger;
import com.sam.safemanager.util.MD5Encoder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * @author Sam
 * @date 2013-5-4
 * @weibo:码农明明桑
 */
public class LostProtectedActivity extends Activity implements OnClickListener {
	private SharedPreferences sp;
	private Dialog dialog;
	private EditText et_pwd;
	private EditText et_pwd_confirm;
	private TextView tv_lost_protected_number;
	private TextView tv_reentry_setup_guide;
	private CheckBox cb_isprotecting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		if(isPWDSteup()){
			showNormalEntryDialog();
		}else{
			showFirstEntryDialog();
		}
	}
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_first_dialog_cancle:
			dialog.dismiss();
			finish();
			break;
		case R.id.bt_first_dialog_ok:
			String pwd = et_pwd.getText().toString().trim();
			String pwd_confirm = et_pwd_confirm.getText().toString().trim();
			if("".equals(pwd)||"".equals(pwd_confirm)){
				Toast.makeText(getApplicationContext(), "密码不能为空", 0).show();
				return;
			}else{
				if(pwd.equals(pwd_confirm)){
					Editor editor = sp.edit();
					editor.putString("password", MD5Encoder.encode(pwd));
					editor.commit();
				}
				else{
					Toast.makeText(getApplicationContext(), "两次密码不同", 0).show();
					return;
				}
			}
			dialog.dismiss();
			break;
		case R.id.bt_normal_dialog_cancle:
			dialog.dismiss();
			finish();
			break;
		case R.id.bt_normal_dialog_ok:
			String password = et_pwd.getText().toString().trim();
			if("".equals(password)){
				Toast.makeText(getApplicationContext(), "请输入密码", 0).show();
				return;
			}else{
				String realpwd = sp.getString("password", "");
				if (realpwd.equals(MD5Encoder.encode(password))){
					
					if(isSteup()){ // true 
						Logger.i("加载手机防盗主界面");
						setContentView(R.layout.lost_protected);
						tv_lost_protected_number = (TextView) this.findViewById(R.id.tv_lost_protected_number);
						tv_reentry_setup_guide = (TextView )this.findViewById(R.id.tv_reentry_setup_guide);
						cb_isprotecting = (CheckBox )this.findViewById(R.id.cb_isprotecting);
						// 初始化这些控件 
						String number = sp.getString("safenumber", "");
						tv_lost_protected_number.setText("安全手机号码为:"+number);
						//重新进入设置向导的点击事件
						tv_reentry_setup_guide.setOnClickListener(this);
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
						
						
					}else{
						Logger.i("激活设置向导界面");
						finish();
						Intent intent = new Intent(LostProtectedActivity.this,SetupGudie1Activity.class);
						startActivity(intent);
					}
					
					
				}else{
					Toast.makeText(getApplicationContext(), "密码错误", 0).show();
					return;
				}
			}
			dialog.dismiss();
			break;
		case R.id.tv_reentry_setup_guide:
			finish();
			Intent intent = new Intent(LostProtectedActivity.this,SetupGudie1Activity.class);
			startActivity(intent);
		}
		
	}
	
	/**
	 * 检查sharedpreference里面是否有密码的设置
	 * @return
	 */
	private boolean isPWDSteup(){
	   String password =	sp.getString("password",null);
	   if(password==null){
		   return false;
	   }else{
		   if("".equals(password)){
			   return false;
		   }else{
			   return true;
		   }
	   }
	}
	
	/**
	 * 判断用户是否进行过设置向导
	 * @return
	 */
	private boolean isSteup(){
		return  sp.getBoolean("issteupalready", false);
	}
	
	/**
	 * 正常登陆的对话框
	 */
	private void showNormalEntryDialog() {
		dialog = new Dialog(this);
		dialog.setCancelable(false);
		//dialog.setContentView(R.layout.first_entry_dialog);
		View view = View.inflate(this, R.layout.normal_entry_dialog, null);
		et_pwd = (EditText) view.findViewById(R.id.et_normal_entry_pwd);
		Button bt_normal_ok = (Button) view.findViewById(R.id.bt_normal_dialog_ok);
		Button bt_normal_cancle =  (Button) view.findViewById(R.id.bt_normal_dialog_cancle);
		bt_normal_ok.setOnClickListener(this);
		bt_normal_cancle.setOnClickListener(this);
		dialog.setContentView(view);
		dialog.show();
		
	}


	/**
	 * 第一次进入程序时候的对话框
	 */
	private void showFirstEntryDialog() {
		dialog = new Dialog(this);
		dialog.setCancelable(false);
		//dialog.setContentView(R.layout.first_entry_dialog);
		View view = View.inflate(this, R.layout.first_entry_dialog, null);
		et_pwd = (EditText) view.findViewById(R.id.et_first_entry_pwd);
		et_pwd_confirm = (EditText) view.findViewById(R.id.et_first_entry_pwd_confirm);
		Button bt_ok = (Button) view.findViewById(R.id.bt_first_dialog_ok);
		Button bt_cancle =  (Button) view.findViewById(R.id.bt_first_dialog_cancle);
		bt_ok.setOnClickListener(this);
		bt_cancle.setOnClickListener(this);
		dialog.setContentView(view);
		dialog.show();
	}

}
