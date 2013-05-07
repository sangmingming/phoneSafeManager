package com.sam.safemanager.test;

import java.util.List;

import com.sam.safemanager.bean.TaskInfo;
import com.sam.safemanager.engine.TaskInfoProvider;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.test.AndroidTestCase;

public class TestTaskInfoProvider extends AndroidTestCase{

	public void testGetAlltask() throws Exception{
		TaskInfoProvider provider = new TaskInfoProvider(getContext());
		ActivityManager am = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo>  runingappinfos = am.getRunningAppProcesses();
		
		List<TaskInfo> taskinfos =provider.getAllTasks(runingappinfos);
		System.out.println(taskinfos.size());
		for(TaskInfo taskinfo : taskinfos){
			System.out.println(taskinfo.getAppname());
		}
	}
}
