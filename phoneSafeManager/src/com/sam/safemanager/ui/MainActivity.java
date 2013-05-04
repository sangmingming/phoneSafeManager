/**
 * 
 */
package com.sam.safemanager.ui;

import com.sam.safemanager.R;
import com.sam.safemanager.adapter.MainUIAdapter;
import com.sam.safemanager.util.Logger;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * @author Sam
 * @date 2013-4-21
 * @weibo:��ũ����ɣ
 */
public class MainActivity extends Activity implements OnItemClickListener{
	private GridView gv_main;
	private MainUIAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_main);
		
		gv_main = (GridView) findViewById(R.id.gv_main);
		adapter = new MainUIAdapter(this);
		gv_main.setAdapter(adapter);
		gv_main.setOnItemClickListener(this);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch(position){
		case 0:
			Logger.i("�ֻ�����");
			break;
		case 1:
			Logger.i("ͨѶ��ʿ");
			break;
		case 2:
			Logger.i("��������");
			break;
		case 3:
			Logger.i("�������");
			break;
		case 4:
			Logger.i("��������");
			break;
		case 5:
			Logger.i("ϵͳ�Ż�");
			break;
		case 6:
			Logger.i("�߼�����");
			break;
		case 7:
			Logger.i("��������");
			break;
		case 8:
			Logger.i("��������");
			break;
		}
		
	}
	

}