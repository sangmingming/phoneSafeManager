package com.sam.safemanager.util;

import com.sam.safemanager.R;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyToast {

	/**
	 * 显示自定义的土司
	 * @param context 上下文
	 * @param iconid 图标的id
	 * @param text 显示的文本
	 */
	public static void showToast(Context context,int iconid, String text){
		View view = View.inflate(context, R.layout.my_toast, null);
	   	TextView tv = (TextView) view.findViewById(R.id.tv_my_toast);
		ImageView iv = (ImageView) view.findViewById(R.id.iv_my_toast);
		iv.setImageResource(iconid);
		tv.setText(text);
		Toast toast = new Toast(context);
		toast.setDuration(0);
		toast.setView(view);
		toast.show();
	}
	
}
