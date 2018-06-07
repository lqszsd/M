package com.example.mymirror.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class PictureView extends ImageView {

	public PictureView(Context context) {
		this(context,null);
	}

	public PictureView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public PictureView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	
}
