package com.yaoxiaowen.weather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import com.yaoxiaowen.weather.debug.DebugTools;
import com.yaoxiaowen.weather.debug.MyLog;
import com.yaoxiaowen.weather.tool.MyApp;
import com.yaoxiaowen.weather.tool.WeatherStringRes;

public class MainReceiver extends BroadcastReceiver {
	private static final String TAG = "MainReceiver";
	private GetWeatherAsyncTask mGetWeatherTask;
	private DebugTools mDebugTools;
	private SharedPreferences sp;
	
	public MainReceiver() {
		mGetWeatherTask = new GetWeatherAsyncTask(MyApp.getContext());
		mDebugTools = new DebugTools(MyApp.getContext());
		sp = PreferenceManager.getDefaultSharedPreferences(MyApp.getContext());
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		MyLog.v(TAG, "onReceive() -> intent.getAction() = " + intent.getAction());
		String city = null;
		
		if (intent.getAction().equals(WeatherStringRes.BC_START_UPDATE_DB)){           //开始更新数据库
			if (isNetAvailable()){
				city = sp.getString(InputCityFragment.KEY_INPUT_CITY, "宁波");
				MyLog.v(TAG, "onReceive() BC_START_UPDATE_DB -> city=" + city);
				
				mGetWeatherTask.execute(city.toString());
			}else{
				mDebugTools.displayToast("网络连接不可用");
			}
			
			
		}else if (intent.getAction().equals(WeatherStringRes.BC_UPDATED_COMPLETE)){  //资料写入数据库完毕，开始更新widget
			Intent intentUpdate = new Intent();
			intentUpdate.setAction(WeatherStringRes.MainWidgetService_action);
			//5.0 以后， service服务必须采用显示方式启动
			intentUpdate.setPackage(MyApp.getContext().getPackageName());
			intentUpdate.addCategory(WeatherStringRes.MainWidgetService_category);
			// YaoWen(43194) modify  at 2017/8/8 21:14
			//Todo 这个地方会报错
			/**
			 *   Caused by: java.lang.IllegalArgumentException: Service Intent must be explicit: Intent { act=com.yaoxiaowen.weather.action.MainWidgetService cat=[com.yaoxiaowen.weather.category.MainWidgetService] (has extras) }
			 at android.app.ContextImpl.validateServiceIntent(ContextImpl.java:1715)
			 at android.app.ContextImpl.startServiceCommon(ContextImpl.java:1744)
			 at android.app.ContextImpl.startService(ContextImpl.java:1728)
			 at android.content.ContextWrapper.startService(ContextWrapper.java:516)
			 at com.yaoxiaowen.weather.MainReceiver.onReceive(MainReceiver.java:52)
			 */
//			intentUpdate.putExtra("test", "驻马店");
			
			MyLog.v(TAG, "onReceive() BC_UPDATED_COMPLETE");
			
			MyApp.getContext().startService(intentUpdate);
			
		}
	}
	
	
	private boolean isNetAvailable(){
		ConnectivityManager cm = (ConnectivityManager)MyApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		
		if (cm == null){
			return false;
		}else {
			if (cm.getActiveNetworkInfo() == null){
				return false;
			}
			
			if (cm.getActiveNetworkInfo().isAvailable()){
				return true;
			}
		}
		

		return false;
	}

}
