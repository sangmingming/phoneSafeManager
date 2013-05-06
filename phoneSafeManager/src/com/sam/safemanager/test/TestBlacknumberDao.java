package com.sam.safemanager.test;

import java.util.List;

import com.sam.safemanager.db.dao.BlackNumberDao;

import android.test.AndroidTestCase;

public class TestBlacknumberDao extends AndroidTestCase {

	public void testAdd() throws Exception{
		BlackNumberDao dao = new BlackNumberDao(getContext());
		long number =13512345678l;
		for(int i=0;i<100;i++){
		   dao.add(number+i+"");
		}
	}
	
	public void testfindall() throws Exception{
		BlackNumberDao dao = new BlackNumberDao(getContext());
		List<String> numbers = dao.getAllNumbers();
		System.out.println(numbers.size());
		assertEquals(100, numbers.size());
	}
	
	public void testDelete() throws Exception{
		BlackNumberDao dao = new BlackNumberDao(getContext());
		dao.delete("13512345777");
	}
	
	public void testupdate() throws Exception{
		BlackNumberDao dao = new BlackNumberDao(getContext());
		dao.update("13512345776", "13512345779");
	}
}
