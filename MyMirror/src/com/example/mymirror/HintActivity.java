package com.example.mymirror;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class HintActivity extends Activity{
	 private TextView konw;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_hint);
	        konw = (TextView) findViewById(R.id.i_know);
	        konw.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                finish();
	            }
	        });
	    }

}
