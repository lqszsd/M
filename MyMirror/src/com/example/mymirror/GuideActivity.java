package com.example.mymirror;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

public class GuideActivity extends Activity {

	private Handler handler=new Handler(new Handler.Callback() {
		
		public boolean handleMessage(Message msg) {
			if (msg.what==1) {
				Intent intent=new Intent(GuideActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
			return false;
		}
	});
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		
		
		
		handler.sendEmptyMessageDelayed(1, 3000);
		
	}

	
}
