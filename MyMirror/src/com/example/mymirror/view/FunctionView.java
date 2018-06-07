package com.example.mymirror.view;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class FunctionView extends LinearLayout implements OnClickListener{

	private LayoutInflater mInflater;
	public FunctionView(Context context) {
		this(context,null);
		//66
	}
	
	public FunctionView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public FunctionView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mInflater=LayoutInflater.from(context);
		init();
	}
	
	private void init(){
		View view= mInflater.inflate(R.layout., root)
	}

	
	

	
}
