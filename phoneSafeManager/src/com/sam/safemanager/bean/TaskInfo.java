package com.sam.safemanager.bean;

import android.graphics.drawable.Drawable;

/**
 * memorysize 是以kb作为单位的
 * @author zehua
 *
 */
public class TaskInfo {

	private String appname;
	private Drawable appicon;
	private int pid; // process id 进程的id
	private int memorysize;
	private boolean ischecked;
	private String packname;
	
	private boolean systemapp;
	
	
	
	public boolean isSystemapp() {
		return systemapp;
	}
	public void setSystemapp(boolean systemapp) {
		this.systemapp = systemapp;
	}
	public String getPackname() {
		return packname;
	}
	public void setPackname(String packname) {
		this.packname = packname;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public Drawable getAppicon() {
		return appicon;
	}
	public void setAppicon(Drawable appicon) {
		this.appicon = appicon;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getMemorysize() {
		return memorysize;
	}
	public void setMemorysize(int memorysize) {
		this.memorysize = memorysize;
	}
	public boolean isIschecked() {
		return ischecked;
	}
	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}
	
}
