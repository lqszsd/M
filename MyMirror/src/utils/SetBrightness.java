package utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.Settings;
import android.view.WindowManager;

import java.util.*;

public class SetBrightness {
	//开启亮度自动调节
	public static boolean isAutoBrightness(ContentResolver aContentResolver) {
        boolean automicBrightness = false;   
        try {
           
            automicBrightness = Settings.System.getInt(aContentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE) ==        Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return automicBrightness;
    }
	//获取屏幕亮度
	 public static int getScreenBrightness(Activity activity) {
	        int nowBrightnessValue = 0;//亮度值
	        ContentResolver resolver = activity.getContentResolver();
	        try {
	            nowBrightnessValue = android.provider.Settings.System.getInt(
	                    resolver, Settings.System.SCREEN_BRIGHTNESS);//获取亮度值
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return nowBrightnessValue;
	    }
	 //设置屏幕亮度
	 public static void setBrightness(Activity activity, int brightness) {
	        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
	        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
	        activity
	        .getWindow().setAttributes(lp);
	    }
	 //停止亮度自动调节
	 public static void stopAutoBrightness(Activity activity) {
	        Settings.System.putInt(activity.getContentResolver(),
	                Settings.System.SCREEN_BRIGHTNESS,
	                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
	    }
	 //开启亮度自动调节
	 public static void startAutoBrightness(Activity activity) {
	        Settings.System.putInt(activity.getContentResolver(),
	                Settings.System.SCREEN_BRIGHTNESS_MODE,
	                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
	    }
	 //保存亮度设置
	 public static void saveBrightness(ContentResolver resolver, int brightness) {
	        Uri uri = android.provider.Settings.System
	                .getUriFor("screen_brightness");
	        android.provider.Settings.System.putInt(resolver, "screen_brightness",
	                brightness);
	        resolver.notifyChange(uri, null);//保存状态
	    }
	 //获取内容解析
	 public static ContentResolver getResolver(Activity activity){
	        ContentResolver cr = activity.getContentResolver();
	        return cr;
	    }
	 

}
