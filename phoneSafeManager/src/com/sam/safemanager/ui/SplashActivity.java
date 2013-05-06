package com.sam.safemanager.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.sam.safemanager.R;
import com.sam.safemanager.engine.NumberAddressService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

/**
 * @author Sam
 * @date 2013-4-21
 * @weibo:ÂëÅ©Ã÷Ã÷É£
 */
public class SplashActivity extends Activity {
	private LinearLayout ll_splash_main;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_splash);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				copyDb();
				
			}
		}).start();
		ll_splash_main = (LinearLayout) this.findViewById(R.id.ll);
		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
		aa.setDuration(2000);
		ll_splash_main.startAnimation(aa);
		new Handler().postDelayed(new Runnable(){

			@Override
			public void run() {
				Intent toMain = new Intent(SplashActivity.this,MainActivity.class);
				startActivity(toMain);
				SplashActivity.this.finish();
			}
			
		}, 1000);
	}
	
	private void copyDb(){
		String path = "/data/data/com.sam.safemanager/databases/";
		File f = new File(NumberAddressService.DB_PATH);
		if(!f.exists()){
			File fd = new File(path);
			if(!fd.exists())
				fd.mkdirs();
			FileOutputStream os = null;
			try {
				f.createNewFile();
				os = new FileOutputStream(NumberAddressService.DB_PATH);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				InputStream is = SplashActivity.this.getAssets().open("naddress.db");
				byte[] buffer = new byte[1024];
				int count = 0;
				while((count = is.read(buffer))>0){
					os.write(buffer,0,count);
					os.flush();
				}
				is.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

}
