package com.sam.safemanager.ui;

import com.sam.safemanager.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class DragViewActivity extends Activity implements OnTouchListener {
	private static final String TAG = "DragViewActivity";
	private ImageView iv_drag_view;
	private TextView tv_drag_view;
	int startx; // 记录下来第一次手指触摸屏幕的位置
	int starty;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.drag_view);
//		Drawable drawable = new ColorDrawable(Color.RED);
//		getWindow().setBackgroundDrawable(drawable);
		iv_drag_view = (ImageView) this.findViewById(R.id.iv_drag_view);
		tv_drag_view = (TextView) this.findViewById(R.id.tv_drag_view);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		iv_drag_view.setOnTouchListener(this);
	}

	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		// 重新加载上次imageview在窗体中的位置 
		int x = sp.getInt("lastx", 0);
		int y = sp.getInt("lasty", 0);
		Log.i(TAG, "x="+x);
		Log.i(TAG, "y="+y);
//		iv_drag_view.layout(iv_drag_view.getLeft()+x, iv_drag_view.getTop()+y, iv_drag_view.getRight()+x, iv_drag_view.getBottom()+y);
//		// 重新渲染 这个view对象
//		iv_drag_view.invalidate();
		
		//通过布局 更改 iv_drag_view 在窗体中的位置
		LayoutParams params = (LayoutParams) iv_drag_view.getLayoutParams();
		params.leftMargin = x;
		params.topMargin = y;
		iv_drag_view.setLayoutParams(params);
	}




	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		// 如果手指放在imageview上拖动
		
		
		case R.id.iv_drag_view:
			// 
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startx = (int) event.getRawX(); // 获取手指第一次接触屏幕在x方向的坐标
				starty = (int) event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE: // 手指没有离开屏幕 在屏幕移动
				int x = (int) event.getRawX(); 
				int y = (int) event.getRawY();
				
				if(y<240){
					// tv_drag_view 设置textview在窗体的下面
					tv_drag_view.layout(tv_drag_view.getLeft(), 260, tv_drag_view.getRight(), 280);
				}else{
					tv_drag_view.layout(tv_drag_view.getLeft(), 60, tv_drag_view.getRight(), 80);
				}
				
				
				//获取手指移动的距离
				int dx = x-startx;
				int dy = y-starty;
				int l= iv_drag_view.getLeft();
				int t= iv_drag_view.getTop();
				int r = iv_drag_view.getRight();
				int b = iv_drag_view.getBottom();
				
				iv_drag_view.layout(l+dx, t+dy, r+dx, b+dy);
				
				startx = (int) event.getRawX(); // 获取到移动后的位置
				starty = (int) event.getRawY();
				
				break;
			case MotionEvent.ACTION_UP: // 手指离开屏幕对应的事件
				Log.i(TAG,"手指离开屏幕");
				// 记录下来最后 图片在窗体中的位置 
				
				int lasty = iv_drag_view.getTop();
				int lastx = iv_drag_view.getLeft();
				Editor editor = sp.edit();
				editor.putInt("lastx", lastx);
				editor.putInt("lasty", lasty);
				editor.commit();
				break;
			}
			
			
			break;

		}

		return true; // 不会中断触摸事件的返回
	}

}
