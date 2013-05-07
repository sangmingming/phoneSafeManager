/**
 * 
 */
package com.sam.safemanager.engine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sam.safemanager.db.dao.AddressDao;

/**
 * @author Sam
 * @date 2013-5-7
 * @weibo:ÂëÅ©Ã÷Ã÷É£
 */
public class SDPathService {
	public static final String DB_PATH = "/data/data/com.sam.safemanager/databases/clearpath.db";
	public static String getsdPath(String packname){
		String address = null;
		SQLiteDatabase db = AddressDao.getAddressDB(DB_PATH);
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery(
					"select filepath from softdetail where apkname=?",
					new String[] {packname});
			if (cursor.moveToNext()) {
				address = cursor.getString(0);
			}
			cursor.close();
			db.close();

		}
		return address;
	}

}
