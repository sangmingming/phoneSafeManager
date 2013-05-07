package com.sam.safemanager.ui;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import com.sam.safemanager.MyApplication;
import com.sam.safemanager.R;
import com.sam.safemanager.bean.TaskInfo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class AppDetailActivity extends Activity {

	private TextView tv_app_detail_name,tv_app_detail_packname;
	private ScrollView sv_app_detail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_detail);
		
		tv_app_detail_name = (TextView) this.findViewById(R.id.tv_app_detail_name);
		tv_app_detail_packname = (TextView) this.findViewById(R.id.tv_app_detail_packname);
		sv_app_detail = (ScrollView) this.findViewById(R.id.sv_app_detail);
		
		
	    MyApplication myapp =	(MyApplication) getApplication();
	    TaskInfo taskinfo =  myapp.taskinfo;
	    tv_app_detail_name.setText(taskinfo.getAppname());
	    String packname = taskinfo.getPackname();
	    tv_app_detail_packname.setText(packname);
	    
        try {
        	Class clazz = getClass().getClassLoader().loadClass("android.widget.AppSecurityPermissions");
		
        	Constructor constructor = clazz.getConstructor(new Class[]{Context.class,String.class});
        	
        	Object object = constructor.newInstance(new Object[]{this,packname});
        	
        	
        	Method method = clazz.getDeclaredMethod("getPermissionsView", new Class[]{});
        	
            View view =	(View) method.invoke(object, new Object[]{});
            
            sv_app_detail.addView(view);
        
        } catch (Exception e) {
			e.printStackTrace();
		}
        myapp.taskinfo = null;
	}

}
