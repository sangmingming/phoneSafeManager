package com.sam.safemanager.engine;

import java.util.ArrayList;
import java.util.List;

import com.sam.safemanager.bean.AppInfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class AppInfoProvider {
	private static final String TAG = "AppInfoProvider";
	private Context context;
	private PackageManager packmanager;
	
	
	
	public AppInfoProvider(Context context) {
		this.context = context;
		packmanager = context.getPackageManager();
	}

	/**
	 * 返回当前手机里面安装的所有的程序信息的集合
	 * @return 应用程序的集合
	 */
	public List<AppInfo> getAllApps(){
		List<AppInfo> appinfos = new ArrayList<AppInfo>();
		List<PackageInfo> packinfos = packmanager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		for(PackageInfo info :packinfos){
			AppInfo myApp = new AppInfo();
			String packname = info.packageName;
			myApp.setPackname(packname);
			ApplicationInfo appinfo = info.applicationInfo;
			Drawable icon = appinfo.loadIcon(packmanager);
			myApp.setIcon(icon);
			String appname = appinfo.loadLabel(packmanager).toString();
			myApp.setAppname(appname);
			 if(filterApp(appinfo)){
				 myApp.setSystemApp(false);
			 }else{
				 myApp.setSystemApp(true);
			 }
			appinfos.add(myApp);
		}
		return appinfos;
	}
	
	
	/**
	 * 判断某个应用程序是 不是三方的应用程序
	 * @param info
	 * @return 
	 */
    public boolean filterApp(ApplicationInfo info) {
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        }
        return false;
    }
}
