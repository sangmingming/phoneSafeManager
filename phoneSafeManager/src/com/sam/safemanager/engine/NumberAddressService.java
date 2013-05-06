/**
 * 
 */
package com.sam.safemanager.engine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sam.safemanager.db.dao.AddressDao;

/**
 * @author Sam
 * @date 2013-5-6
 * @weibo:码农明明桑
 */
public class NumberAddressService {
	
	public static final String DB_PATH = "/data/data/com.sam.safemanager/databases/naddress.db";
	/**
	 * 
	 * @param number
	 *            要查询的电话号码
	 * @return 电话号码的归属地
	 */
	public static String getAddress(String number) {
		String mobilepattern = "^1[3458]\\d{6,12}$";
		String threepattern = "^0\\d{2,12}$";
		String address = number;
		if(number.matches(threepattern)){
			address = threePhone(number);
			if(address == number && number.length()>3){
				address = fourPhone(number);
			}
		}else if(number.matches(mobilepattern)){
			address = mobilePhone(number);
		}
		return address;
	}
	
	public static String threePhone(String num){
		String address = num;
		SQLiteDatabase db = AddressDao.getAddressDB(DB_PATH);
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery(
					"select city from address_tb where area=? limit 1",
					new String[] { num.substring(0, 3) });
			if (cursor.moveToNext()) {
				address = cursor.getString(0);
			}
			cursor.close();
			db.close();

		}
		return address;
	}
	
	public static String fourPhone(String num){
		String address = num;
		SQLiteDatabase db = AddressDao.getAddressDB(DB_PATH);
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery(
					"select city from address_tb where area=? limit 1",
					new String[] { num.substring(0, 4) });
			if (cursor.moveToNext()) {
				address = cursor.getString(0);
			}
			cursor.close();
			db.close();

		}
		return address;
	}
	
	public static String mobilePhone(String num){
		String address = num;
		int  outkey = 1;
		
		SQLiteDatabase db = AddressDao.getAddressDB(DB_PATH);
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery(
					"select outkey from numinfo where mobileprefix=?",
					new String[] { num.substring(0, 7) });
			if (cursor.moveToNext()) {
				outkey = cursor.getInt(0);
			}
			cursor.close();
			Cursor cursor2 = db.rawQuery(
					"select city from address_tb where _id=?",
					new String[] { outkey+"" });
			if (cursor2.moveToNext()) {
				address = cursor2.getString(0);
			}
			cursor2.close();
			db.close();
		}
		return address;
	}

}