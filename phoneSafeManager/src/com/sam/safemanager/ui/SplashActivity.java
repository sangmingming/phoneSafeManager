package com.sam.safemanager.ui;

import com.sam.safemanager.R;

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

}
