/**
 * 
 */
package com.sam.safemanager.adapter;

import com.sam.safemanager.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Sam
 * @date 2013-5-2
 * @weibo:码农明明桑
 */
public class MainUIAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private static ImageView iv_icon;
	private static TextView tv_name;

	private static String[] names = { "手机防盗", "通讯卫士", "软件管理", "任务管理", "流量管理",
		 "系统优化", "高级工具", "设置中心" };
	private static int[] icons = { R.drawable.widget09, R.drawable.widget02,
		R.drawable.widget01, R.drawable.widget07, R.drawable.widget05,
		 R.drawable.widget06, R.drawable.widget03,
		R.drawable.widget08 };
	
	public MainUIAdapter(Context context){
		inflater = LayoutInflater.from(context);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return names.length;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = inflater.inflate(R.layout.grid_main_item, null);
		}
		iv_icon = (ImageView) convertView.findViewById(R.id.iv_main_icon);
		tv_name = (TextView) convertView.findViewById(R.id.tv_main_name);
		iv_icon.setImageResource(icons[position]);
		tv_name.setText(names[position]);
		return convertView;
	}

}
