package com.sam.safemanager.engine;

import java.util.ArrayList;
import java.util.List;

import com.sam.safemanager.bean.ContactInfo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;


public class ContactInfoService {
	private Context context;
	
	
	
	public ContactInfoService(Context context) {
		this.context = context;
	}



	public List<ContactInfo> getContactInfos(){
		ContentResolver resolver = context.getContentResolver();
		//1.获取联系人的id
		//2.根据联系人的id 获取联系人名字
		//3.根据联系人的id 数据的type 获取到对应的数据(电话,email);
		List<ContactInfo> infos = new ArrayList<ContactInfo>();
		ContactInfo info ;
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri datauri = Uri.parse("content://com.android.contacts/data");
		Cursor cursor = resolver.query(uri, null, null, null, null);
		while (cursor.moveToNext()) {
		  info = new ContactInfo();
		   String id =	cursor.getString(cursor.getColumnIndex("_id"));
		   String name =	cursor.getString(cursor.getColumnIndex("display_name"));
		   info.setName(name);
		   Cursor datacursor =  resolver.query(datauri, null, "raw_contact_id=?", new String[]{id}, null);
		   while (datacursor.moveToNext()) {

			//mimetype
			String type = datacursor.getString(datacursor.getColumnIndex("mimetype"));
			if("vnd.android.cursor.item/phone_v2".equals(type)){
				String number = datacursor.getString(datacursor.getColumnIndex("data1"));
				info.setPhone(number);
			}
		}
		   datacursor.close();
		   infos.add(info);
		   info = null;
		}
		cursor.close();
		return infos;
	}
}
