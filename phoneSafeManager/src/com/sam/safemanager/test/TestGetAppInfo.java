package com.sam.safemanager.test;

import com.sam.safemanager.engine.AppInfoProvider;

import android.test.AndroidTestCase;

public class TestGetAppInfo extends AndroidTestCase {
	public void getApps() throws Exception{
		AppInfoProvider provider = new AppInfoProvider(getContext());
		provider.getAllApps();
	}
}
