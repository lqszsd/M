package com.example.mymirror.view;

import com.example.mymirror.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FunctionView extends LinearLayout implements View.OnClickListener{

	private LayoutInflater mInflater;
	private ImageView light_up;
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
	
	public void onClick(View view){
		
	}
	
	
	private void init(){
		View view=mInflater.inflate(R.layout.view_function,this );
		light_up=(ImageView)findViewById(R.id.light_up);
		
		
	}

	
	

	
}
