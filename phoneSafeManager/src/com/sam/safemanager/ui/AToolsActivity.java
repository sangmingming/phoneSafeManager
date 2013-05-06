package com.sam.safemanager.ui;


import com.sam.safemanager.R;
import com.sam.safemanager.service.AddressService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AToolsActivity extends Activity implements OnClickListener{
	private TextView tvQuery,tvAddress;
	private LinearLayout llGuishu;
	private CheckBox ckAddress;
	private SharedPreferences sp;
	private boolean showAddress = false;
	private Intent addServiceIntent;
	private TextView tv_atools_select_bg;
	private TextView tv_atools_change_location;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_atools);
		tvQuery = (TextView) this.findViewById(R.id.tv_atools_query);
		tvQuery.setOnClickListener(this);
		
		llGuishu = (LinearLayout) findViewById(R.id.ll_guishudi);
		tvAddress = (TextView) findViewById(R.id.tv_address);
		ckAddress = (CheckBox) findViewById(R.id.ck_address);
		
		llGuishu.setOnClickListener(this);
		ckAddress.setOnClickListener(this);
		tv_atools_change_location = (TextView) this
				.findViewById(R.id.tv_atools_change_location);
		tv_atools_change_location.setOnClickListener(this);
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		tv_atools_select_bg = (TextView) this
				.findViewById(R.id.tv_atools_select_bg);
		tv_atools_select_bg.setOnClickListener(this);
		
		addServiceIntent = new Intent(this,AddressService.class);
		ckAddress.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) 
					startService(addServiceIntent);
				else
					stopService(addServiceIntent);	
			}
		});
		sp = getSharedPreferences("config", MODE_PRIVATE);
		showAddress = sp.getBoolean("show_address", false);
		initAddressSet();
	}
	
	
	/**
	 * 读取设置数据
	 */
	private void initAddressSet(){
		if(showAddress){
			tvAddress.setText("显示来电归属地");
			ckAddress.setChecked(true);
		}else{
			tvAddress.setText("不显示来电归属地");
			ckAddress.setChecked(false);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.tv_atools_query:
			Intent atoolsIntent = new Intent(AToolsActivity.this,QueryNumActivity.class);
			startActivity(atoolsIntent);
			break;
		case R.id.ll_guishudi:
		case R.id.ck_address:
			if(showAddress){
				showAddress = false;
			}else{
				showAddress = true;
			}
			initAddressSet();
			break;
		case R.id.tv_atools_select_bg:
			AlertDialog.Builder builder = new Builder(this);
			builder.setTitle("归属地提示显示风格");
			String[] items = new String[] { "半透明", "活力橙", "苹果绿" };
			builder.setSingleChoiceItems(items, 0,
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							Editor editor = sp.edit();
							editor.putInt("background", which);
							editor.commit();
						}
					});

			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {

						}
					});
			builder.create().show();
			break;

		case R.id.tv_atools_change_location:
			// 更改来电归属地的显示位置
			Intent intent = new Intent(this, DragViewActivity.class);
			startActivity(intent);

			break;
			
		}
		
	}

}
