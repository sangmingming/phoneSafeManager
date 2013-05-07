package com.sam.safemanager.ui;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


import com.sam.safemanager.R;
import com.sam.safemanager.engine.SDPathService;
import com.sam.safemanager.util.FileSizeUtil;
import com.sam.safemanager.util.TextFormater;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.RemoteException;
import android.os.StatFs;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.app.Activity;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;

public class CleanActivity extends Activity implements OnClickListener{
	private TextView tvDesc,tvCache,tvSD;
	private LinearLayout ll;
	private SeekBar skbProgress;
	private Button btnClean,btnStop;
	private CheckBox ckCache,ckSD;
	private PackageManager pm;
	private long cacheSize = 0;
	private long sdSize = 0;
	private boolean hasSd = false;
	private String sdPath;
	List<PackageInfo> packageinfos;
	private List<String> pathlists = new ArrayList<String>();
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				tvCache.setText("程序缓存:"+TextFormater.getDataSize(cacheSize));
				tvSD.setText("SD卡垃圾文件:"+TextFormater.getDataSize(sdSize));
				btnClean.setVisibility(View.VISIBLE);
				btnClean.setText("一键清理");
				break;
			}
		};
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_clean);
		findView();
		hasSd = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if(hasSd)
			sdPath = Environment.getExternalStorageDirectory().getPath();
	}


	private void findView() {
		tvDesc = (TextView) findViewById(R.id.tv_msg);
		tvCache = (TextView) findViewById(R.id.tv_cache);
		tvSD = (TextView) findViewById(R.id.tv_sd);
		ll = (LinearLayout) findViewById(R.id.ll_clean);
		skbProgress = (SeekBar) findViewById(R.id.skb);
		btnClean = (Button) findViewById(R.id.btn_clean);
		btnStop = (Button) findViewById(R.id.btn_stop);
		ckCache = (CheckBox) findViewById(R.id.ck_cache);
		ckSD = (CheckBox) findViewById(R.id.ck_sd);
		btnClean.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		ll.setVisibility(View.GONE);
	}


	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_clean:
			if(btnClean.getText().toString().equals("立即扫描")){
				ll.setVisibility(View.VISIBLE);
				btnClean.setVisibility(View.GONE);
				findCache();
			}
				
			break;
		
		}
		
	}
	
	private void findCache(){
			pm = getPackageManager();
			// 1.获取所有的安装好的应用程序
			packageinfos = pm
						.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
			skbProgress.setMax(packageinfos.size()*2);
			skbProgress.setProgress(0);
			
			for (PackageInfo info : packageinfos) {
				getAppCacheSize(info.packageName);
				if(hasSd){
					String path = SDPathService.getsdPath(info.packageName);
					skbProgress.setProgress(skbProgress.getProgress()+1);
					if( path != null){
						path = sdPath+path;
						pathlists.add(path);
						File f = new File(path);
						try {
							//sdSize += FileSizeUtil.getFileSize(f);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
	}
	
	/**
	 * 根据包名获取应用程序的体积信息 注意: 这个方法是一个异步的方法 程序的体积要花一定时间才能获取到.
	 * 
	 * @param packname
	 */
	private void getAppCacheSize(final String packname) {
		try {
			Method method = PackageManager.class.getMethod(
					"getPackageSizeInfo", new Class[] { String.class,
							IPackageStatsObserver.class });

			method.invoke(pm, new Object[] { packname,
					new IPackageStatsObserver.Stub() {

						public void onGetStatsCompleted(PackageStats pStats,
								boolean succeeded) throws RemoteException {
							// 注意这个操作是一个异步的操作
							cacheSize += pStats.cacheSize;
							skbProgress.setProgress(skbProgress.getProgress()+1);
							if(packname.equals(packageinfos.get(packageinfos.size()-1).packageName)){
								handler.sendEmptyMessage(1);
							}
							
						}
					} });

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 删除sd卡上的垃圾文件
	 */
	private void clearSDcache(){
		for(String path:pathlists){
			File p = new File(path);
			deleteFile(p);
		}
	}
	
	/**
	 * 递归删除一个文件夹中的所有文件
	 * @param f
	 */
	private void deleteFile(File f){
		if(f.isDirectory()){
			File[] flist = f.listFiles();
			for(File fs:flist){
				if(fs.isDirectory()){
					deleteFile(fs);
					fs.delete();
				}else{
					fs.delete();
				}
			}
		}
	}
	
	private void clearCache() {  

        try {  
            Method localMethod = pm.getClass().getMethod("freeStorageAndNotify", Long.TYPE,  
                    IPackageDataObserver.class);  
            Long localLong = getEnvironmentSize()-1;  
            Object[] arrayOfObject = new Object[2];  
            arrayOfObject[0] = localLong;  
            localMethod.invoke(pm, localLong, new IPackageDataObserver.Stub() {  

                @Override  
                public void onRemoveCompleted(String packageName, boolean succeeded) throws RemoteException { 
                }  
            });  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 

    }
	
	private long getEnvironmentSize()
    {
      File dataFile = Environment.getDataDirectory();
      if (dataFile == null)
        return 0;
      else
      {
        StatFs localStatFs = new StatFs(dataFile.getPath());
        long l2 = localStatFs.getBlockSize();
        return localStatFs.getBlockCount() * l2;
      }
    }
	
	
	

}
