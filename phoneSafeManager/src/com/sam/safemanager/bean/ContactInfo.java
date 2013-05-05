package com.sam.safemanager.bean;

public class ContactInfo {
	private String name;
	private String phone;

	public ContactInfo() {

	}

	public ContactInfo(String name, String phone) {
		this.name = name;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
