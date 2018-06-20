package com.example.mymirror.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.example.mymirror.R;

public class PictureView extends ImageView {

	private int[] bitmap_ID_Array;    //获取图片资源ID数组
	private int bitmap_index;
	private Canvas mCanvas;   //画布
	private int draw_Width;
	private int draw_Height;
	private Bitmap mBitmap;  //镜框
	
	public PictureView(Context context) {
		this(context,null);
	}

	public PictureView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public PictureView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		getTheWindowSize((Activity)context);  //获取屏幕尺度
		init();
	}

	//获取资源中的图片
	private void initBitmaps(){
		bitmap_ID_Array=new int[]{R.mipmap.mag_0001,R.mipmap.mag_0003,R.mipmap.mag_0005,R.mipmap.mag_0006,
				R.mipmap.mag_0007,R.mipmap.mag_0008,R.mipmap.mag_0009,R.mipmap.mag_0011,R.mipmap.mag_0012,
				R.mipmap.mag_0014};
	}
	
	private void init(){
		initBitmaps();
		bitmap_index=0;
		mBitmap=Bitmap.createBitmap(draw_Width, draw_Height, Bitmap.Config.ARGB_8888);//??
		mCanvas=new Canvas(mBitmap);
		mCanvas.drawColor(Color.TRANSPARENT); //transparent是透明色
	}
	
	public void setPhotoFrame(int index){
		bitmap_index=index;
		invalidate();  //窗口无效，需要重绘
		
	}
	
	//获取更换镜框的ID
	public int getPhotoFrame(){
		return bitmap_index;
	}
	
	private void getTheWindowSize(Activity activity){
        DisplayMetrics dm = new DisplayMetrics(); //获取屏幕分辨率的类
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);//获取屏幕显示属性
        draw_Width = dm.widthPixels;//宽度变量
        draw_Height = dm.heightPixels;//高度
        Log.e("1、屏幕宽度：", draw_Width + "\t\t屏幕高度：" + draw_Height);
    }
	
	//更新Bitmap对象，更换相框、
	private Bitmap getNewBitmap(){
		//更具Bitmap——index获取图片
		Bitmap bitmap=BitmapFactory.decodeResource(getResources(),
				bitmap_ID_Array[bitmap_index]).copy(Bitmap.Config.ARGB_8888, true);
		//根据长宽设置图片
		bitmap=Bitmap.createScaledBitmap(bitmap, draw_Width, draw_Height, true);
		return bitmap;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.TRANSPARENT);  //透明
		canvas.drawBitmap(mBitmap, 0, 0, null);
		Rect rect1=new Rect(0,0,this.getWidth(),this.getHeight());
		canvas.drawBitmap(getNewBitmap(), null,rect1,null);
	}
}
