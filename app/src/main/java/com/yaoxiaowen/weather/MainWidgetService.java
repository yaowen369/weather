package com.yaoxiaowen.weather;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.widget.RemoteViews;
import com.yaoxiaowen.weather.tool.LunarCalendar;
import com.yaoxiaowen.weather.debug.DebugTools;
import com.yaoxiaowen.weather.debug.MyLog;
import com.yaoxiaowen.weather.tool.WeatherStringRes;

public class MainWidgetService extends Service {
	private static final String TAG = "MainWidgetService";
	
	private SharedPreferences sp;
	private MyDatabaseHelper dbHelper;
	private MyReciver myRecevier = new MyReciver();
	private DebugTools mDebugTools = new DebugTools(this);
	
	private static final String[] monthNum = {
		"零",
			    "一",   "二",  "三",  "四", "五",  "六",  "七",  "八",  "九",
		"十",  "十一",  "十二",  "十三",  "十四",  "十五",  "十六",  "十七",  "十八",  "十九",
		"二十",  "廿一",  "廿二",  "廿三",  "廿四",  "廿五",  "廿六",  "廿七",  "廿八",  "廿九",
		"三十",
	}; 
	
	//用于具体的农历日期的称呼
	private static final String[] lunarNum = {
		"零",
			    "初一",   "初二",  "初三",  "初四", "初五",  "初六",  "初七",  "初八",  "初九",
		"初十",  "十一",  "十二",  "十三",  "十四",  "十五",  "十六",  "十七",  "十八",  "十九",
		"二十",  "廿一",  "廿二",  "廿三",  "廿四",  "廿五",  "廿六",  "廿七",  "廿八",  "廿九",
		"三十",
	}; 
	
	RemoteViews mRemoteViews;
	ComponentName mComponentName;
	AppWidgetManager mAppWidgetManager;
	
	public MainWidgetService() {
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		MyLog.v(TAG, "onBind()");
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		MyLog.v(TAG, "onCreate()");
		dbHelper = new MyDatabaseHelper(this, WeatherStringRes.DB_NAME, 1);
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_DATE_CHANGED);
		registerReceiver(myRecevier, intentFilter);
		
		mDebugTools.displayToast("MainWidgetService -> onCreate()", 1500);
		
		 mRemoteViews = new RemoteViews(getApplication().getPackageName(), R.layout.widget_layout);
		 mComponentName = new ComponentName(getApplicationContext(), MainWidgetProvider.class);
		 mAppWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
		 MyLog.v(TAG, "onCreate()->"
				 	+  "\n mRemoteViews=" + mRemoteViews
				 	+ "\n mComponentName=" + mComponentName
				 	+ "\n mAppWidgetManager=" + mAppWidgetManager);
		 
		 setLunarDate();
		 
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(myRecevier);
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		MyLog.v(TAG, "onStartCommand()");
		sp = getSharedPreferences(WeatherStringRes.SP_NAME, MODE_PRIVATE);
		setWeather();
		return super.onStartCommand(intent, flags, startId);
	}
	
	//设置农历的日期
	private void setLunarDate(){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		MyLog.v(TAG, "setLunarDate()->" + "year=" + year + "  month=" + month + "  day=" + day);
		
		int[] lunarDate = LunarCalendar.ywSolarToLunar(year, month, day);
		
		
		String lunarDateStr = monthNum[ lunarDate[1] ]+ "月" + lunarNum [ lunarDate[2] ];
		
		MyLog.v(TAG, "setLunarDate()-> lunarDate = " + lunarDate[0] + "  " + lunarDate[1] + "  " + lunarDate[2]
				+ "\n lunarDateStr = " + lunarDateStr);
		
		mRemoteViews.setTextViewText(R.id.lunarDate, lunarDateStr);
		mAppWidgetManager.updateAppWidget(mComponentName, mRemoteViews);
	} // end of "setLunarDate()"
	
	//设置天气预报区域的Today Weather detail
	private void setWeather(){
		
		String cityName = sp.getString(WeatherStringRes.SP_CURRENT_CITY, null);
		MyLog.v(TAG, "setTodayWeather() -> cityName=" + cityName);
		mRemoteViews.setTextViewText(R.id.cityName, cityName);
		String realTime = sp.getString(WeatherStringRes.SP_REAL_TEMP, null);
		mRemoteViews.setTextViewText(R.id.realtimeTemperature, realTime);
		
		
		//从数据库中得到数据
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "select * from " + WeatherStringRes.DB_TABLE_NAME;
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToNext();
		
		if (cursor.getCount() == 0){
			MyLog.v(TAG, "setWeather() -> cursor.getCount() == 0");
			return ;
		}
		
		//设置第一行的数据
		mRemoteViews.setTextViewText(R.id.temperatureRang, cursor.getString( cursor.getColumnIndexOrThrow(WeatherStringRes.FIELD_TEMPERATURE)  ));
		mRemoteViews.setTextViewText(R.id.windDirection, cursor.getString( cursor.getColumnIndexOrThrow(WeatherStringRes.FIELD_WIND) ));
		
		//设置天气图标
		String weather = cursor.getString( cursor.getColumnIndexOrThrow(WeatherStringRes.FIELD_WEATHER));
		mRemoteViews.setTextViewText(R.id.weatherText, weather);
		mRemoteViews.setImageViewResource(R.id.weatherPic, new GetWeatherPic(weather).getPic());
		
		//下面这几句试图在ImageView上设置从URL上得到的图片，但是没有成功，原因不知道
//		String wetsite = cursor.getString( cursor.getColumnIndexOrThrow(WeatherStringRes.FIELD_NIGHT_PICTURE_URL) );
//		MyLog.v(TAG, "wetsite = " + wetsite
//						+ "\n  Uri=" + Uri.parse(wetsite));
//		mRemoteViews.setImageViewUri(R.id.weatherPic, Uri.parse(wetsite));
		
		setThreeDayWeather(cursor);
		
		mAppWidgetManager.updateAppWidget(mComponentName, mRemoteViews);
		
	}  //end of "setTodayWeather()"
	
	//设置未来三天的天气预报
	private void setThreeDayWeather(Cursor cursor){
		//设置FirstDay数据
		cursor.moveToNext();
		mRemoteViews.setTextViewText(R.id.firstSingleDate, cursor.getString( cursor.getColumnIndexOrThrow(WeatherStringRes.FIELD_DATE)));
		mRemoteViews.setTextViewText(R.id.firstSingleWeatherText, cursor.getString( cursor.getColumnIndexOrThrow(WeatherStringRes.FIELD_WEATHER)));
		mRemoteViews.setTextViewText(R.id.firstSingleWindDirection, cursor.getString( cursor.getColumnIndexOrThrow(WeatherStringRes.FIELD_WIND)));
		mRemoteViews.setTextViewText(R.id.firstSingleTemperatureRang, cursor.getString( cursor.getColumnIndexOrThrow(WeatherStringRes.FIELD_TEMPERATURE)));
		
		//设置SecondDay数据
		cursor.moveToNext();
		mRemoteViews.setTextViewText(R.id.secondSingleDate, cursor.getString( cursor.getColumnIndexOrThrow(WeatherStringRes.FIELD_DATE)));
		mRemoteViews.setTextViewText(R.id.secondSingleWeatherText, cursor.getString( cursor.getColumnIndexOrThrow(WeatherStringRes.FIELD_WEATHER)));
		mRemoteViews.setTextViewText(R.id.secondSingleWindDirection, cursor.getString( cursor.getColumnIndexOrThrow(WeatherStringRes.FIELD_WIND)));
		mRemoteViews.setTextViewText(R.id.secondSingleTemperatureRang, cursor.getString( cursor.getColumnIndexOrThrow(WeatherStringRes.FIELD_TEMPERATURE)));
		
		//设置ThirdDay数据
		cursor.moveToNext();
		mRemoteViews.setTextViewText(R.id.thirdSingleDate, cursor.getString( cursor.getColumnIndexOrThrow(WeatherStringRes.FIELD_DATE)));
		mRemoteViews.setTextViewText(R.id.thirdSingleWeatherText, cursor.getString( cursor.getColumnIndexOrThrow(WeatherStringRes.FIELD_WEATHER)));
		mRemoteViews.setTextViewText(R.id.thirdSingleWindDirection, cursor.getString( cursor.getColumnIndexOrThrow(WeatherStringRes.FIELD_WIND)));
		mRemoteViews.setTextViewText(R.id.thirdSingleTemperatureRang, cursor.getString( cursor.getColumnIndexOrThrow(WeatherStringRes.FIELD_TEMPERATURE)));
	}
	
	
	
	public class MyReciver extends BroadcastReceiver{
		private static final String TAG_1 = "  MyReciver";	
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			MyLog.v(TAG + TAG_1, "onReceive() -> intent.getAction() = " + intent.getAction());
			if (intent.getAction().equals(Intent.ACTION_DATE_CHANGED)){    //如果日期发生改变
				setLunarDate();
			}
		}
		
	}
	
}
