package com.example.mymirror;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.example.mymirror.view.DrawView;
import com.example.mymirror.view.FunctionView;
import com.example.mymirror.view.PictureView;

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
	private int mCurrentCamIndex,ROTATE,minFocus,maxFocus,everyFocus,nowFocus;
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
    private Camera openFrontFacingCameraGingerbread() {
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
    //设置摄像头方向
    private void setCameraDisplayOrientatiion(Activity activity,int cameraId,Camera camera){
    	Camera.CameraInfo info = new  Camera.CameraInfo();
    	Camera.getCameraInfo(cameraId, info);
    	//获得旋转角度
    	int rotation =  activity.getWindowManager().getDefaultDisplay().getRotation();
    	int degrees = 0;
    	switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}
    	int result = 0;
    	if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
    		//前置摄像头旋转算法
    		result = (info.orientation + degrees)%360;
    		result = (360-result)%360;
    	}else {
			//后置摄像头旋转算法
    		result = (info.orientation - degrees + 360)%360;
		}
    	ROTATE = result + 180;
    	camera.setDisplayOrientation(result);
    }
    
    //设置摄像头
    private void setCamera() {
    	if(checkCameraHardware()){
    		camera = openFrontFacingCameraGingerbread();
    		setCameraDisplayOrientatiion(this, mCurrentCamIndex, camera);
    		Camera.Parameters parameters = camera.getParameters();
    		parameters.setPictureFormat(ImageFormat.JPEG);
    		List<String> list = parameters.getSupportedFocusModes();
    		for (String str : list){
    			Log.e(TAG,"支持的对焦的模式：" + str);
    		}
    		List<Camera.Size> pictureList = parameters.getSupportedPictureSizes();
    		List<Camera.Size> previewList = parameters.getSupportedPreviewSizes();
    		parameters.setPictureSize(pictureList.get(0).width, pictureList.get(0).height);
    		parameters.setPreviewSize(previewList.get(0).width, previewList.get(0).height);
    		minFocus = parameters.getZoom();
    		maxFocus = parameters.getMaxZoom();
    		everyFocus = 1;
    		nowFocus = minFocus;
    		seekBar.setMax(maxFocus);
    		Log.e(TAG, "当前镜头距离：" + minFocus + "\t\t获取最大距离：" + maxFocus);
    		camera.setParameters(parameters);
    	}
		
	}
    
    
    
    
    
    
}
