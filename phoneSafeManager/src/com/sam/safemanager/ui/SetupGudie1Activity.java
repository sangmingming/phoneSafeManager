package com.sam.safemanager.ui;

import com.sam.safemanager.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SetupGudie1Activity extends Activity implements OnClickListener {
	private Button bt_next;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setupguide1);
		bt_next = (Button) this.findViewById(R.id.bt_next);
		bt_next.setOnClickListener(this);
	}
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_next:
			Intent intent = new Intent(this,SetupGudie2Activity.class);
			//一定要把当前的activity从任务栈里面移除
			finish();
			startActivity(intent);
			//设置activity切换时候的动画效果
			overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
			break;

		}
		
	}

}
