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
	//������ļ�д����
	private static final String TAG=MainActivity.class.getSimpleName();
	//����surface�ռ���ʾ������
	private SurfaceHolder holder;
	//��ʾ������������
	private SurfaceView surfaceView;
	//Ч������
	private PictureView pictureView;
	//����������
	private FunctionView functionView;
	private SeekBar seekBar;
	private ImageView add,minus;
	private LinearLayout bottomLayout;
	private ImageView save;
	private ProgressDialog dialog;
	private DrawView drawView;
	//�ж��Ƿ������
	private boolean havaCamera;
	//�����ָ�� ��תֵ ��Ĭ�Ͻ��� ��󽹾� �������� ��ǰ����ֵ
	private int mCurrentCamIndex,ROTATE,minFocus,maxFocus,everyFocus,nowFocus;
	//������������
	private Camera camera;
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        
        
    }
    /**
     * ��ʼ���ؼ�
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
     * �ж��ֻ��Ƿ�������ͷ
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
     * ��ǰ������ͷ����������ͷ����
     * @param cameraCount �������
     * @return ����ͷ����
     */
    private Camera openFrontFacingCameraGingerbread() {
    	int cameraCount;
    	Camera mCamera=null;
    	Camera.CameraInfo cameraInfo=new Camera.CameraInfo();
    	//��ȡ�������
    	cameraCount=Camera.getNumberOfCameras();
    	for(int camIdx=0;camIdx<cameraCount;camIdx++){
    		Camera.getCameraInfo(camIdx, cameraInfo);
    		if(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
    			try{
    				mCamera=Camera.open(camIdx);
    				mCurrentCamIndex=camIdx;
    			} catch(RuntimeException e){
    				Log.e(TAG, "�����ʧ��"+e.getMessage());
    			}
    			
    		}
    	}
    	return mCamera;
		
	}
    //��������ͷ����
    private void setCameraDisplayOrientatiion(Activity activity,int cameraId,Camera camera){
    	Camera.CameraInfo info = new  Camera.CameraInfo();
    	Camera.getCameraInfo(cameraId, info);
    	//�����ת�Ƕ�
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
    		//ǰ������ͷ��ת�㷨
    		result = (info.orientation + degrees)%360;
    		result = (360-result)%360;
    	}else {
			//��������ͷ��ת�㷨
    		result = (info.orientation - degrees + 360)%360;
		}
    	ROTATE = result + 180;
    	camera.setDisplayOrientation(result);
    }
    
    //��������ͷ
    private void setCamera() {
    	if(checkCameraHardware()){
    		camera = openFrontFacingCameraGingerbread();
    		setCameraDisplayOrientatiion(this, mCurrentCamIndex, camera);
    		Camera.Parameters parameters = camera.getParameters();
    		parameters.setPictureFormat(ImageFormat.JPEG);
    		List<String> list = parameters.getSupportedFocusModes();
    		for (String str : list){
    			Log.e(TAG,"֧�ֵĶԽ���ģʽ��" + str);
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
    		Log.e(TAG, "��ǰ��ͷ���룺" + minFocus + "\t\t��ȡ�����룺" + maxFocus);
    		camera.setParameters(parameters);
    	}
		
	}
    
    
    
    
    
    
}
