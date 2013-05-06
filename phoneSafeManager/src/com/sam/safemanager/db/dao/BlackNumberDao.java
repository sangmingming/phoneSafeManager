package com.sam.safemanager.db.dao;

import java.util.ArrayList;
import java.util.List;

import com.sam.safemanager.db.BlackNumberDBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BlackNumberDao {
	private Context context;
	private BlackNumberDBHelper dbHelper;

	public BlackNumberDao(Context context) {
		this.context = context;
		dbHelper = new BlackNumberDBHelper(context);
	}

	/**
	 * 查询
	 */
	public boolean find(String number) {
		boolean result = false;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery(
					"select number from blacknumber where number=?",
					new String[] { number });
			if (cursor.moveToNext()) {
				result = true;
			}
			cursor.close();

			db.close();
		}
		return result;
	}

	/**
	 * 添加
	 */
	public void add(String number){
		if(find(number)){
			return ;
		}
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if(db.isOpen()){
			db.execSQL("insert into blacknumber (number) values (?)", new Object[]{number});
			db.close();
		}
	}
	/**
	 * 删除
	 */
	public void delete(String number){

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if(db.isOpen()){
			db.execSQL("delete from blacknumber where number=?", new Object[]{number});
			db.close();
		}
	}
	
	/**
	 * 更新操作
	 * @param oldnumber 旧的号码
	 * @param newNumber 新的号码
	 */
	public void  update(String oldnumber ,String newNumber){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if(db.isOpen()){
			db.execSQL("update blacknumber set number=? where number=?  ", new Object[]{newNumber,oldnumber});
			db.close();
		}
	}
	
	
	/**
	 * 查找全部号码
	 */
	public List<String> getAllNumbers(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		List<String> numbers = new ArrayList<String>();
		if (db.isOpen()) {
		  Cursor cursor =	db.rawQuery("select number from blacknumber", null);
			while (cursor.moveToNext()) {
				String number = cursor.getString(0);
				numbers.add(number);
			}
			cursor.close();
			db.close();
		}
		return numbers;
	}
}
