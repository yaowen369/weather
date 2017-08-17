package com.yaoxiaowen.weather;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import com.yaoxiaowen.weather.debug.MyLog;
import com.yaoxiaowen.weather.tool.MyApp;


public class MyAlarm extends Activity{
	private static final String TAG = "MyAlarm";
	
	public MyAlarm() {
		MyLog.v(TAG, "MyAlarm()");
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLunarAlarm();
	}



	public void setLunarAlarm(){
		Time time = new Time();
		time.setToNow();
		
		long currentTime_ms = (time.hour * 60 * 60 * 1000) + (time.minute * 60 * 1000) + (time.second * 1000);
		//设定在每晚的凌晨之后一分钟把农历进行更新
		long targetTime_ms = (0 * 60 * 60 * 1000) + (1 * 60 * 1000) + (0 * 1000);;
		
		long intervalTime_ms = targetTime_ms - currentTime_ms;
		long startTime_ms;
		
		if (intervalTime_ms > 0){
			startTime_ms = intervalTime_ms;
		}else{
			startTime_ms = (24*60*60*1000) + intervalTime_ms;
		}
		
		AlarmManager mAlarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(this, MainWidgetService.class);

		
		PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
		MyLog.v(TAG, "setLunarAlarm()");
		long ondDay_ms = 24*60*60*1000;
		
		startTime_ms = 1 * 1000;
		ondDay_ms = 3 * 1000;
		
		mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, startTime_ms, ondDay_ms, pendingIntent);
	}

}
