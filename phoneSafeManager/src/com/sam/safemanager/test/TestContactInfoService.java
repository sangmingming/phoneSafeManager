package com.sam.safemanager.test;

import java.util.List;

import com.sam.safemanager.bean.ContactInfo;
import com.sam.safemanager.engine.ContactInfoService;


import android.test.AndroidTestCase;

public class TestContactInfoService extends AndroidTestCase {
	public void testGetContacts() throws Exception{
		ContactInfoService service = new ContactInfoService(getContext());
		List<ContactInfo> infos =  service.getContactInfos();
		for(ContactInfo info : infos ){
			System.out.println(info.getName());
			System.out.println(info.getPhone());
			
		}
	}
}
