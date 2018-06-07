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
	private int mCurrentCamIndex,ROTATE,minFocus,maxFocus,ereryFocus,nowFocus;
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
    private Camera openCamers() {
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
}
