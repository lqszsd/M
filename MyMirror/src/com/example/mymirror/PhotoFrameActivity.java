package com.example.mymirror;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoFrameActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{

	private GridView gridView;
	private TextView textView;   //���ؼ�
	private int[] photo_styles;
	private String[] photo_name;
	private Bitmap[] bitmaps;
	
	
	//�������
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			//ȫ����ʾ
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_photo_frame); //��ȡ����
			
			//��ʼ���ؼ�
			textView=(TextView)findViewById(R.id.back_to_main);
			gridView=(GridView)findViewById(R.id.photo_frame_list);
			
			initDatas();//��ʼ������
			
			textView.setOnClickListener(this);
			
			PhotoFrameAdapter adapter=new PhotoFrameAdapter();  //����������
			gridView.setAdapter(adapter);        //��������
			gridView.setOnItemClickListener(this);      //ִ������Ŀ���������¼�
			
		}
	
	private void initDatas(){
		//photo styles
		photo_styles=new int[]{R.mipmap.mag_0001,R.mipmap.mag_0003,R.mipmap.mag_0005,R.mipmap.mag_0006,
				R.mipmap.mag_0007,R.mipmap.mag_0008,R.mipmap.mag_0009,R.mipmap.mag_0011,R.mipmap.mag_0012,R.mipmap.mag_0014};
		//photo name
		photo_name=new String[]{"Beautiful","Special","Wishes","Forever","Journey","Love","River","Wonder","Birthday","Nice"};
		bitmaps=new Bitmap[photo_styles.length];
		for(int i=0;i<photo_styles.length;i++){
			Bitmap bitmap=BitmapFactory.decodeResource(getResources(),photo_styles[i]);
			bitmaps[i]=bitmap;
		}
	}
	
	class PhotoFrameAdapter extends BaseAdapter{

		@Override//��ȡitem����
		public int getCount() {
			return photo_name.length;
		}

		@Override//��ȡitem
		public Object getItem(int position) {
			return photo_name[position];
		}

		@Override//��ȡͼƬID
		public long getItemId(int position) {
			return position;
		}

		@Override//��ȡitem����
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView==null) {
				holder=new ViewHolder();    //�½�holder����
				convertView=getLayoutInflater().inflate(R.layout.item_gridview, null);
				holder.image=(ImageView)convertView.findViewById(R.id.item_pic);
				holder.txt=(TextView)convertView.findViewById(R.id.item_txt);
				convertView.setTag(holder);
			}else {
				holder=(ViewHolder)convertView.getTag();
			}
			setData(holder,position);
			return convertView;
		}
		
	}
	//��������
	private void setData(ViewHolder holder, int position){
		holder.image.setImageBitmap(bitmaps[position]);
		holder.txt.setText(photo_name[position]);
	}
	
	class ViewHolder{
		ImageView image;    //����ͼƬ�ؼ�
		TextView txt;
	}
	
	//item �ĵ����¼�
	@Override
	public void onItemClick(AdapterView<?> parent,View view, int position, long id){
		Intent intent=new Intent();
		intent.putExtra("position", position);    //����ͼƬ��λ�ý��д���
		setResult(RESULT_OK, intent);            //�����Խ�������ص�������
		finish();
	}
	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		case R.id.back_to_main:
			finish();
			break;
		default:
			break;
		}
		
	}
}
