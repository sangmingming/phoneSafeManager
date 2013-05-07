package com.sam.safemanager.adapter;

import java.util.List;

import com.sam.safemanager.R;
import com.sam.safemanager.bean.AppInfo;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppManagerAdapter extends BaseAdapter {

	private static final String TAG = "AppManagerAdapter";
	private List<AppInfo> appinfos;
	private Context context;
	private static ImageView iv;
	private static TextView tv;

	public AppManagerAdapter(List<AppInfo> appinfos, Context context) {
		this.appinfos = appinfos;
		this.context = context;
	}

	/**
	 * ��������������������
	 * @param appinfos
	 */
	public void setAppInfos(List<AppInfo> appinfos){
		this.appinfos = appinfos;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return appinfos.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return appinfos.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * View convertView (ת��view���� ,��ʷview����Ļ���) convertview �����϶���ʱ�򱻻��յ���view����
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		AppInfo info = appinfos.get(position);
		View view;
		if (convertView == null) {
			//Log.i(TAG,"ͨ����Դ�ļ� ����view����");
			view = View.inflate(context, R.layout.app_item, null);
		}else{
			//Log.i(TAG,"ʹ����ʷ����view����");
			view = convertView;
		}
		iv = (ImageView) view.findViewById(R.id.iv_app_icon);
		tv = (TextView) view.findViewById(R.id.tv_app_name);
		iv.setImageDrawable(info.getIcon());
		tv.setText(info.getAppname());
		return view;

	}

}