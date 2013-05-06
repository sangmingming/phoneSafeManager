package com.sam.safemanager.ui;

import java.util.ArrayList;
import java.util.List;

import com.sam.safemanager.R;
import com.sam.safemanager.adapter.AppManagerAdapter;
import com.sam.safemanager.bean.AppInfo;
import com.sam.safemanager.engine.AppInfoProvider;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class AppManagerActivity extends Activity implements OnClickListener {
	protected static final int GET_ALL_APP_FINISH = 80;
	protected static final int GET_USER_APP_FINISH = 81;
	private static final String TAG = "AppManagerActivity";
	private ListView lv_app_manager;
	private LinearLayout ll_loading;
	private AppInfoProvider provider;
	private List<AppInfo> appinfos;
	private List<AppInfo> userAppinfos;
	private AppManagerAdapter adapter;
	private PopupWindow localPopupWindow;
	private TextView tv_app_manager_title;
	private boolean isloading = false;
	private String packname;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case GET_ALL_APP_FINISH:
				ll_loading.setVisibility(View.INVISIBLE);
				// TODO: 把数据设置给listview的数组适配器
				adapter = new AppManagerAdapter(appinfos,
						AppManagerActivity.this);
				lv_app_manager.setAdapter(adapter);
				isloading = false;
				break;
			case GET_USER_APP_FINISH:
				ll_loading.setVisibility(View.INVISIBLE);
				// TODO: 把数据设置给listview的数组适配器
				adapter = new AppManagerAdapter(userAppinfos,
						AppManagerActivity.this);
				lv_app_manager.setAdapter(adapter);
				isloading = false;
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_manager);
		tv_app_manager_title = (TextView) this
				.findViewById(R.id.tv_app_manager_title);
		tv_app_manager_title.setOnClickListener(this);
		lv_app_manager = (ListView) this.findViewById(R.id.lv_app_manager);
		// lv_app_manager.setAdapter(adapter);
		ll_loading = (LinearLayout) this
				.findViewById(R.id.ll_app_manager_loading);

		initUI(true);

		lv_app_manager.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				dismissPopUpwindow();

				// 获取当前view对象在窗体中的位置
				int[] arrayOfInt = new int[2];
				view.getLocationInWindow(arrayOfInt);

				int i = arrayOfInt[0] + 60;
				int j = arrayOfInt[1];

				AppInfo info = (AppInfo) lv_app_manager
						.getItemAtPosition(position);
				// String packname = info.getPackname();
				/*
				 * TextView tv = new TextView (AppManagerActivity.this);
				 * tv.setTextSize(20); tv.setTextColor(Color.RED);
				 * tv.setText(packname);
				 */
				View popupview = View.inflate(AppManagerActivity.this,
						R.layout.popup_item, null);
				LinearLayout ll_start = (LinearLayout) popupview
						.findViewById(R.id.ll_start);
				LinearLayout ll_uninstall = (LinearLayout) popupview
						.findViewById(R.id.ll_uninstall);
				LinearLayout ll_share = (LinearLayout) popupview
						.findViewById(R.id.ll_share);

				// 把当前条目在listview中的位置设置给view对象
				ll_share.setTag(position);
				ll_uninstall.setTag(position);
				ll_start.setTag(position);

				ll_start.setOnClickListener(AppManagerActivity.this);
				ll_uninstall.setOnClickListener(AppManagerActivity.this);
				ll_share.setOnClickListener(AppManagerActivity.this);

				LinearLayout ll = (LinearLayout) popupview
						.findViewById(R.id.ll_popup);
				ScaleAnimation sa = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f);
				sa.setDuration(200);
				localPopupWindow = new PopupWindow(popupview, android.view.WindowManager.LayoutParams.WRAP_CONTENT, android.view.WindowManager.LayoutParams.WRAP_CONTENT);
				// 一定要记得给popupwindow设置背景颜色
				// Drawable background = new ColorDrawable(Color.TRANSPARENT);
				Drawable background = getResources().getDrawable(
						R.drawable.local_popup_bg);
				localPopupWindow.setBackgroundDrawable(background);
				localPopupWindow.showAtLocation(view, Gravity.LEFT
						| Gravity.TOP, i, j);
				ll.startAnimation(sa);

			}
		});

		lv_app_manager.setOnScrollListener(new OnScrollListener() {

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				dismissPopUpwindow();

			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				dismissPopUpwindow();

			}
		});
	}

	/**
	 * 
	 * @param flag
	 *            true 代表的是更新所有的程序 false 代表的是更新用户的程序
	 */
	private void initUI(final boolean flag) {
		ll_loading.setVisibility(View.VISIBLE);
		new Thread() {
			@Override
			public void run() {
				isloading = true;
				if (flag) {
					provider = new AppInfoProvider(AppManagerActivity.this);
					appinfos = provider.getAllApps();
					//
					Message msg = new Message();
					msg.what = GET_ALL_APP_FINISH;
					handler.sendMessage(msg);
				}else {
					provider = new AppInfoProvider(AppManagerActivity.this);
					appinfos = provider.getAllApps();
					userAppinfos = getUserApps(appinfos);
					Message msg = new Message();
					msg.what = GET_USER_APP_FINISH;
					handler.sendMessage(msg);
				}
			}
		}.start();
	}

	private void dismissPopUpwindow() {
		/*
		 * 保证只有一个popupwindow的实例存在
		 */
		if (localPopupWindow != null) {
			localPopupWindow.dismiss();
			localPopupWindow = null;
		}
	}

	public void onClick(View v) {
		if (isloading) {
			return;
		}

		int positon = 0;
		if (v.getTag() != null) {
			positon = (Integer) v.getTag();
		}
		// 判断当前列表的状态
		String titletext;

		AppInfo item;

		TextView tv;
		if (v instanceof TextView) {
			tv = (TextView) v;
			titletext = tv.getText().toString();
			if ("所有程序".equals(titletext)) {
				item = appinfos.get(positon);
				packname = item.getPackname();
			} else {
				item = userAppinfos.get(positon);
				packname = item.getPackname();
			}
		} else {
			if ("所有程序".equals(tv_app_manager_title.getText().toString())) {
				item = appinfos.get(positon);
				packname = item.getPackname();
			} else {
				item = userAppinfos.get(positon);
				packname = item.getPackname();
			}
		}

		dismissPopUpwindow();
		switch (v.getId()) {
		case R.id.tv_app_manager_title:
			tv = (TextView) v;
			titletext = tv.getText().toString();

			if ("所有程序".equals(titletext)) {
				// 切换到用户程序
				tv.setText("用户程序");
				// 更新listview的列表
				userAppinfos = getUserApps(appinfos);
				adapter.setAppInfos(userAppinfos);
				adapter.notifyDataSetChanged();

			} else {
				// 切换到所有程序
				tv.setText("所有程序");
				adapter.setAppInfos(appinfos);
				adapter.notifyDataSetChanged();
			}

			break;
		case R.id.ll_share:
			Log.i(TAG, "分享" + packname);
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			// shareIntent.putExtra("android.intent.extra.SUBJECT", "分享");
			shareIntent.setType("text/plain");
			// 需要指定意图的数据类型
			shareIntent.putExtra(Intent.EXTRA_SUBJECT, "分享");
			shareIntent.putExtra(Intent.EXTRA_TEXT,
					"推荐你使用一个程序" + item.getAppname());
			shareIntent = Intent.createChooser(shareIntent, "分享");
			startActivity(shareIntent);
			break;
		case R.id.ll_uninstall:

			// 需求不能卸载系统的应用程序
			if (item.isSystemApp()) {
				Toast.makeText(this, "系统应用不能被删除", 0).show();
			} else {
				Log.i(TAG, "卸载" + packname);
				String uristr = "package:" + packname;
				Uri uri = Uri.parse(uristr);
				Intent deleteIntent = new Intent();
				deleteIntent.setAction(Intent.ACTION_DELETE);
				deleteIntent.setData(uri);
				startActivityForResult(deleteIntent, 0);
			}
			break;
		case R.id.ll_start:
			Log.i(TAG, "运行" + packname);
			// getPackageManager().queryIntentActivities(intent, flags);
			try {
				PackageInfo info = getPackageManager().getPackageInfo(
						packname,
						PackageManager.GET_UNINSTALLED_PACKAGES
								| PackageManager.GET_ACTIVITIES);
				ActivityInfo[] activityinfos = info.activities;
				if (activityinfos.length > 0) {
					ActivityInfo startActivity = activityinfos[0];
					Intent intent = new Intent();
					intent.setClassName(packname, startActivity.name);
					startActivity(intent);
				} else {
					Toast.makeText(this, "当前应用程序无法启动", 0).show();
				}
			} catch (Exception e) {
				Toast.makeText(this, "应用程序无法启动", 0).show();
				e.printStackTrace();
			}

			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if ("所有程序".equals(tv_app_manager_title.getText().toString())) {
			// 刷新当前的程序列表
			initUI(true);
		} else {
			// 更新用户程序的列表信息
			initUI(false);
		}
	}

	/**
	 * 获取所有的用户安装的app
	 * 
	 * @param appinfos
	 * @return
	 */
	private List<AppInfo> getUserApps(List<AppInfo> appinfos) {
		List<AppInfo> userAppinfos = new ArrayList<AppInfo>();
		for (AppInfo appinfo : appinfos) {
			if (!appinfo.isSystemApp()) {
				userAppinfos.add(appinfo);
			}
		}
		return userAppinfos;
	}

	@Override
	protected void onDestroy() {
		dismissPopUpwindow();
		super.onDestroy();
	}

	
	
}
