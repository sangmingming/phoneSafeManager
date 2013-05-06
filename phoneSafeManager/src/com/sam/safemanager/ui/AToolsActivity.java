package com.sam.safemanager.ui;

import com.sam.safemanager.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AToolsActivity extends Activity implements OnClickListener{
	private TextView tvQuery;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_atools);
		tvQuery = (TextView) this.findViewById(R.id.tv_atools_query);
		tvQuery.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.tv_atools_query:
			Intent atoolsIntent = new Intent(AToolsActivity.this,QueryNumActivity.class);
			startActivity(atoolsIntent);
			break;
			
		}
		
	}

}
