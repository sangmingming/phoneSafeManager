package com.sam.safemanager.test;

import com.sam.safemanager.engine.NumberAddressService;

import android.test.AndroidTestCase;

public class TestNumQuery extends AndroidTestCase {
	public void testGetAddress(){
		
		assertEquals("上海市", NumberAddressService.getAddress("021"));
		assertEquals("上海市", NumberAddressService.getAddress("0210"));
		assertEquals("上海市", NumberAddressService.getAddress("02100"));
		assertEquals("上海市", NumberAddressService.getAddress("021000000"));
		assertEquals("安徽省合肥市", NumberAddressService.getAddress("055100"));
		assertEquals("安徽省合肥市", NumberAddressService.getAddress("05510"));
		assertEquals("安徽省合肥市", NumberAddressService.getAddress("0551"));
		assertEquals("安徽省合肥市", NumberAddressService.getAddress("15155185170"));
	}

}
