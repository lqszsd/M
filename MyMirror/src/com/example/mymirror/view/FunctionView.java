package com.example.mymirror.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mymirror.R;

public class FunctionView extends LinearLayout implements View.OnClickListener{

	private LayoutInflater mInflater;
	private ImageView light_up;
	private ImageView hint,choose,down,up;
  	public static final int HINT_ID = R.id.hint;
  	public static final int CHOOSE_ID = R.id.choose;
  	public static final int DOWN_ID = R.id.light_down;
  	public static final int UP_ID = R.id.light_up;
	
	private onFunctionViewItemClickListener listener;
	public interface onFunctionViewItemClickListener {
		void hint();
		void choose();
		void down();
		void up();
		
	}
	
	public FunctionView(Context context) {
		this(context,null);
		
	}
	
	public FunctionView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public FunctionView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mInflater=LayoutInflater.from(context);
		init();
	}
	
	@Override
	  public void onClick(View v) {
	    if (listener!= null){
	      switch (v.getId()){
	        case HINT_ID: 
	          listener.hint();
	          break;
	        case CHOOSE_ID:
	          listener.choose();
	          break;
	        case DOWN_ID:
	          listener.down();
	          break;
	        case UP_ID:
	          listener.up();
	          break;
	        default:
	          break;
	      }
	    }
	 }
	
	
	private void init(){
		View view=mInflater.inflate(R.layout.view_function,this );
		light_up=(ImageView)findViewById(R.id.light_up);
		hint = (ImageView) view.findViewById(HINT_ID);
		choose = (ImageView) view.findViewById(CHOOSE_ID);
		down = (ImageView) view.findViewById(DOWN_ID);
		up = (ImageView) view.findViewById(UP_ID);
		setView();
		
	}

	private void setView(){
		    hint.setOnClickListener(this);
		    choose.setOnClickListener(this);
		    down.setOnClickListener(this);
		    up.setOnClickListener(this);
	}
	
	public void setOnFunctionViewItemClickListener(onFunctionViewItemClickListener monFunctionViewItemClickListener) {
		this.listener = monFunctionViewItemClickListener;
		//sdf
	}
	
}
