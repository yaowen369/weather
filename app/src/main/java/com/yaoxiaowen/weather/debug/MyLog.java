package com.yaoxiaowen.weather.debug;

import android.util.Log;

public class MyLog {

	public MyLog() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void v(String tag, String msg){
		if (DebugState.DEBUG){
			Log.v("yaowenlog," + tag, msg);
		}
	}
	
	public static void v(boolean state, String tag, String msg){
		if ( DebugState.DEBUG && state){
			Log.v("yaowenlog," + tag, msg);
		}
	}
	
	public static void d(String tag, String msg){
		if (DebugState.DEBUG){
			Log.d("yaowenlog," + tag, msg);
		}
	}

	
	public static void d(boolean state, String tag, String msg){
		if (DebugState.DEBUG && state){
			Log.d("yaowenlog," + tag, msg);
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
