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
	private TextView textView;   //返回键
	private int[] photo_styles;
	private String[] photo_name;
	private Bitmap[] bitmaps;
	
	
	//类的主体
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			//全屏显示
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_photo_frame); //获取布局
			
			//初始化控件
			textView=(TextView)findViewById(R.id.back_to_main);
			gridView=(GridView)findViewById(R.id.photo_frame_list);
			
			initDatas();//初始化数据
			
			textView.setOnClickListener(this);
			
			PhotoFrameAdapter adapter=new PhotoFrameAdapter();  //创建适配器
			gridView.setAdapter(adapter);        //绑定适配器
			gridView.setOnItemClickListener(this);      //执行子项目单击监听事件
			
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

		@Override//获取item数量
		public int getCount() {
			return photo_name.length;
		}

		@Override//获取item
		public Object getItem(int position) {
			return photo_name[position];
		}

		@Override//获取图片ID
		public long getItemId(int position) {
			return position;
		}

		@Override//获取item对象
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView==null) {
				holder=new ViewHolder();    //新建holder对象
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
	//设置数据
	private void setData(ViewHolder holder, int position){
		holder.image.setImageBitmap(bitmaps[position]);
		holder.txt.setText(photo_name[position]);
	}
	
	class ViewHolder{
		ImageView image;    //声明图片控件
		TextView txt;
	}
	
	//item 的单击事件
	@Override
	public void onItemClick(AdapterView<?> parent,View view, int position, long id){
		Intent intent=new Intent();
		intent.putExtra("position", position);    //设置图片的位置进行传输
		setResult(RESULT_OK, intent);            //还可以将结果返回到主窗体
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
