package com.yaoxiaowen.weather.debug;

import android.util.Log;


/**
 * YaoWen(43194) create at tongcheng work pc,
 * time:  2017/8/17 19:22  qq:2669932513
 *
 * 之所以要将 v,d 级别的都修改为i级别的,因为很多手机 v.d级别的log默认不输出
 */
public class MyLog {

	public MyLog() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void v(String tag, String msg){
		if (DebugState.DEBUG){
			Log.i("yaowenlog," + tag, msg);
		}
	}
	
	public static void v(boolean state, String tag, String msg){
		if ( DebugState.DEBUG && state){
			Log.i("yaowenlog," + tag, msg);
		}
	}
	
	public static void d(String tag, String msg){
		if (DebugState.DEBUG){
			Log.i("yaowenlog," + tag, msg);
		}
	}

	
	public static void d(boolean state, String tag, String msg){
		if (DebugState.DEBUG && state){
			Log.i("yaowenlog," + tag, msg);
		}
	}
	
	public static void i(String tag, String msg){
		if (DebugState.DEBUG){
			Log.i("yaowenlog," + tag, msg);
		}
	}
	

	public static void i(boolean state, String tag, String msg){
		if (DebugState.DEBUG && state){
			Log.i("yaowenlog," + tag, msg);
		}
	}
	
	public static void w(String tag, String msg){
		if (DebugState.DEBUG){
			Log.w("yaowenlog," + tag, msg);
		}
	}
	
	public static void w(boolean state, String tag, String msg){
		if (DebugState.DEBUG && state){
			Log.w("yaowenlog," + tag, msg);
		}
	}
	
	
	public static void e(String tag, String msg){
		if (DebugState.DEBUG){
			Log.e("yaowenlog," + tag, msg);
		}
	}
	

	public static void e(boolean state, String tag, String msg){
		if (DebugState.DEBUG && state){
			Log.e("yaowenlog," + tag, msg);
		}
	}
}
