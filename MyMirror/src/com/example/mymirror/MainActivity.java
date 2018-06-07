package com.example.mymirror;

import com.example.mymirror.view.DrawView;
import com.example.mymirror.view.FunctionView;
import com.example.mymirror.view.PictureView;

import android.R.drawable;
import android.R.id;
import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends Activity {
	//定义类的简写名称
	private static final String TAG=MainActivity.class.getSimpleName();
	//控制surface空间显示的内容
	private SurfaceHolder holder;
	//显示相机拍摄的内容
	private SurfaceView surfaceView;
	//效果定义
	private PictureView pictureView;
	//标题栏声明
	private FunctionView functionView;
	private SeekBar seekBar;
	private ImageView add,minus;
	private LinearLayout bottomLayout;
	private ImageView save;
	private ProgressDialog dialog;
	private DrawView drawView;
	//判断是否有相机
	private boolean havaCamera;
	//相机的指数 旋转值 ，默认焦距 最大焦距 调整焦距 当前焦距值
	private int mCurrentCamIndex,ROTATE,minFocus,maxFocus,ereryFocus,nowFocus;
	//定义相机类对象
	private Camera camera;
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        
        
    }
    /**
     * 初始化控件
     */
    public void init(){
    	surfaceView=(SurfaceView)findViewById(R.id.surface);
    	pictureView=(PictureView)findViewById(R.id.picture);
    	functionView=(FunctionView)findViewById(R.id.function);
    	seekBar=(SeekBar)findViewById(R.id.seebar);
    	add=(ImageView)findViewById(R.id.add);
    	minus=(ImageView)findViewById(R.id.minus);
    	bottomLayout=(LinearLayout)findViewById(R.id.bottom_bar);
    	drawView=(DrawView)findViewById(R.id.draw_glasses);  	
    }
    /**
     * 判断手机是否有摄像头
     * @return true or false
     */
    private boolean checkCameraHardware() {
    	if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
    		return true;
    	} else {
    		return false;
    	}
    	
	}
    /**
     * 打开前置摄像头并返回摄像头对象
     * @param cameraCount 相机数量
     * @return 摄像头对象
     */
    private Camera openCamers() {
    	int cameraCount;
    	Camera mCamera=null;
    	Camera.CameraInfo cameraInfo=new Camera.CameraInfo();
    	//获取相机数量
    	cameraCount=Camera.getNumberOfCameras();
    	for(int camIdx=0;camIdx<cameraCount;camIdx++){
    		Camera.getCameraInfo(camIdx, cameraInfo);
    		if(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
    			try{
    				mCamera=Camera.open(camIdx);
    				mCurrentCamIndex=camIdx;
    			} catch(RuntimeException e){
    				Log.e(TAG, "相机打开失败"+e.getMessage());
    			}
    			
    		}
    	}
    	return mCamera;
		
	}
}
