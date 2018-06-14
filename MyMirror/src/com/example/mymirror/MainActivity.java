package com.example.mymirror;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.mymirror.view.DrawView;
import com.example.mymirror.view.FunctionView;
import com.example.mymirror.view.PictureView;
//important
public class MainActivity extends Activity implements SurfaceHolder.Callback,SeekBar.OnSeekBarChangeListener,
View.OnTouchListener,View.OnClickListener,FunctionView.onFunctionViewItemClickListener {
	
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
	
	//镜框属性
	private int frame_index;    //镜框的类型
	private int[] frame_index_ID;  //图片资源ID数组
	private static int PHOTO=1;    //镜框请求值
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setViews();
        
        //设置默认镜框ID数组
        frame_index=0;
        frame_index_ID=new int[]{R.mipmap.mag_0001,R.mipmap.mag_0003,R.mipmap.mag_0005,R.mipmap.mag_0006,
				R.mipmap.mag_0007,R.mipmap.mag_0008,R.mipmap.mag_0009,R.mipmap.mag_0011,R.mipmap.mag_0012,
				R.mipmap.mag_0014};
    }
    
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.e(TAG, "返回值"+resultCode+"\t\t请求值："+requestCode);
		if (resultCode==RESULT_OK && requestCode==PHOTO) {
			int position =data.getIntExtra("POSITION", 0); //从返回数据中获取POSITION 值
			frame_index=position;
			Log.e(TAG, "返回的镜框类别:"+position);
		}
	}



	// 初始化控件
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
    
    //判断手机是否有摄像头
    private boolean checkCameraHardware() {
    	if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
    		return true;
    	} else {
    		return false;
    	}
    	
	}
   
    //打开前置摄像头并返回摄像头对象, @param cameraCount 相机数量, * @return 摄像头对象
    private Camera openFrontFacingCameraGingerbread() {
    	int cameraCount;
    	Camera mCamera=null;
    	Camera.CameraInfo cameraInfo=new Camera.CameraInfo();
    	//获取相机数量
    	cameraCount=Camera.getNumberOfCameras();
    	Log.d(TAG,"??"+cameraCount);
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
    @Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
		Log.e("surfaceChanged","绘制改变");
		try{
			camera.stopPreview();
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.e("surfaceCreated","绘制开始");
		try{
			setCamera();
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		}catch(IOException e){
			camera.release();
			camera = null;
			e.printStackTrace();
		}
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.e("surfaceDestroyed","绘制结束");
		toRelease();
	}
	private void toRelease() {
		// TODO Auto-generated method stub
		camera.setPreviewCallback(null);
		camera.stopPreview();
		camera.release();
		camera = null;
	}
	//主界面调取摄像头
	private void setViews() {
		holder = surfaceView.getHolder();
		holder.addCallback(this);
		add.setOnTouchListener(this);
        minus.setOnTouchListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        functionView.setOnFunctionViewItemClickListener(this);
	}
	//设置相机焦距方法
	private void setZoomValues(int want){
		//获取相机信息
		Camera.Parameters parameters=camera.getParameters();
		seekBar.setProgress(want);
		parameters.setZoom(want);
		camera.setParameters(parameters);
	}
	//获取焦距
    private int getZoomValues() {
        Camera.Parameters parameters = camera.getParameters();//获取相机参数
        int values = parameters.getZoom();//获取当前焦距
        return values;
    }
    //放大焦距
    private void addZoomValues() {
        if (nowFocus > maxFocus) { //当前焦距 大于 最大焦距
            Log.e(TAG, "大于maxFocus是不可能的！");
        } else if (nowFocus == maxFocus) {
        } else {
            setZoomValues(getZoomValues() + everyFocus);//设焦距
        }
    }
  //缩小焦距
    private void minusZoomValues() {
        if (nowFocus < 0) {
            Log.e(TAG, "小于0是不可能的！");
        } else if (nowFocus == 0) {
        } else {
            setZoomValues(getZoomValues() - everyFocus);//设焦距
        }
    }
  //设置焦距跟随进度条变化
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //0~99  99级
        Camera.Parameters parameters = camera.getParameters();
        nowFocus = progress; 
        parameters.setZoom(progress);
        camera.setParameters(parameters);
    }
    @Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
	//焦距调节的按钮事件
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.add:
                addZoomValues();
                break;
            case R.id.minus:
                minusZoomValues();
                break;
            case R.id.picture://多点触控的操作
                //待添加手势识别事件函数
                break;
            default:
                break;
        }
        return true;
    }
    
    @Override
	public void hint() {
		Intent intent=new Intent(this,HintActivity.class);
		Log.i(TAG, "输出");
		startActivity(intent);
		
	}
	@Override
	public void choose() {
		Intent intent=new Intent(this,PhotoFrameActivity.class);
		startActivityForResult(intent, PHOTO);  //PHOTO 为镜框返回值，其值为1
		Toast.makeText(this, "选择", Toast.LENGTH_SHORT).show();
		
	}
	@Override
	public void down() {
		
	}
	@Override
	public void up() {
		
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		
	}
    
	
	

}
