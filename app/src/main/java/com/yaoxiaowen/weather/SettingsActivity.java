package com.yaoxiaowen.weather;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.yaoxiaowen.weather.debug.DebugTools;
import com.yaoxiaowen.weather.debug.MyLog;
import com.yaoxiaowen.weather.tool.WeatherStringRes;

public class SettingsActivity extends Activity {
	
	private static final String TAG = "SettingsActivity";
	private DebugTools mDebugTools = new DebugTools(this);
	
	private Button btn;
	private Button updateBtn;
	private TextView text;
	private String city = "宁波";
	private BaiduWeather baiduWeather;
	private GetWeatherAsyncTask mGetWeatherTask;

	private MyDatabaseHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
		
		btn = (Button)findViewById(R.id.btn);
		text = (TextView)findViewById(R.id.text);
		updateBtn = (Button)findViewById(R.id.updateBtn);
		MyLog.v(TAG, "btn=" + btn + "\t text=" + text + "\t updateBtn=" + updateBtn);
		
		
		mGetWeatherTask = new GetWeatherAsyncTask(text, this);
		
		dbHelper = new MyDatabaseHelper(this, WeatherStringRes.DB_NAME, 1);
//		insertDateToDB(dbHelper.getReadableDatabase());
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				mGetWeatherTask.execute("宁波".toString());
				
				if (isNetAvailable()){
					mDebugTools.displayToast("有可用的网络");
				}else {
					mDebugTools.displayToast("网络未连接");
				}
				
//				getDateFromDB();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	public void updateDB(View v){
		getDateFromDB();
		Intent intent = new Intent();
		intent.setAction(WeatherStringRes.MainWidgetService_action);
		intent.addCategory(WeatherStringRes.MainWidgetService_category);
		intent.putExtra("test", "驻马店");
		startService(intent);
	}
	
	//向数据库中插入数据
	private void insertDateToDB(SQLiteDatabase db){
		String execSQL = "insert into " + WeatherStringRes.DB_TABLE_NAME + " values(null, ?, ?, ?, ?, ?, ?)";
		
		ContentValues values_1 = new ContentValues();
		values_1.put(WeatherStringRes.FIELD_DATE, "10yw月19号");
		values_1.put(WeatherStringRes.FIELD_DAY_PICTURE_URL, "ywhttp://baidu.com/");
		values_1.put(WeatherStringRes.FIELD_NIGHT_PICTURE_URL, "ywhttp://taobbao.com");
		values_1.put(WeatherStringRes.FIELD_WEATHER, "yw阴天");
		values_1.put(WeatherStringRes.FIELD_WIND, "yw大风");
		values_1.put(WeatherStringRes.FIELD_TEMPERATURE, "yw17-23");
		
		db.insert(WeatherStringRes.DB_TABLE_NAME, null, values_1);
//		db.execSQL(execSQL,
//				new String[]{
//					"周二 10月14日 (实时：16℃)", 
//					"http://api.map.baidu.com/images/weather/day/qing.png", 
//					"http://api.map.baidu.com/images/weather/night/qing.png", 
//					"晴", 
//					"微风", 
//					"10℃"
//					});
		
		db.execSQL(execSQL,
				new String[]{
					"周二", 
					"http//.png", 
					"httpqing.png", 
					"阴", 
					"大风", 
					"14℃"
					});
		

		db.execSQL(execSQL,
				new String[]{
					"周二", 
					"http//.png", 
					"httpqing.png", 
					null, 
					"3", 
					"4"
					});
	}

	public void getDateFromDB(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
//		String sql = "select " + WeatherStringRes.FIRST_INFO + " from " + WeatherStringRes.DB_TABLE_NAME;
		String sql = "select * from " + WeatherStringRes.DB_TABLE_NAME;
		Cursor cursor = db.rawQuery(sql, null);
		
		
		MyLog.v(TAG, "getDateFromDB() ->  cursor=" +   cursor.toString());
		//第一行记录
		cursor.moveToFirst();
		text.setText("数据库测试结果1"
					+ "\n 0=" + cursor.getString(0)
					+ "\n 1=" + cursor.getString(1)
					+ "\n 2=" + cursor.getString(2)
					+ "\n 3=" + cursor.getString(3)
					+ "\n 4=" + cursor.getString(4)
					+ "\n 5=" + cursor.getString(5)
					+ "\n 6=" + cursor.getString(6)
					);
		
		//第二行记录
		cursor.moveToNext();
		text.append("\n数据库测试结果2"
				+ "\n 0=" + cursor.getString(0)
				+ "\n 1=" + cursor.getString(1)
				+ "\n 2=" + cursor.getString(2)
				+ "\n 3=" + cursor.getString(3)
				+ "\n 4=" + cursor.getString(4)
				+ "\n 5=" + cursor.getString(5)
				+ "\n 6=" + cursor.getString(6)
				);
		
		//第三行记录
		cursor.moveToNext();
		int count = cursor.getColumnIndexOrThrow(WeatherStringRes.FIELD_WEATHER);
		String str = cursor.getString(count);
		mDebugTools.displayAlertDialog("count=" + count + "\t str= " + str);
		
		
		text.append("\n数据库测试结果3"
				+ "\n 0=" + cursor.getString(0)
				+ "\n 1=" + cursor.getString(1)
				+ "\n 2=" + cursor.getString(2)
				+ "\n 3=" + cursor.getString(3)
				+ "\n 4=" + cursor.getString(4)
				+ "\n 5=" + cursor.getString(5)
				+ "\n 6=" + cursor.getString(6)
				);
		
		//第三行记录
		cursor.moveToNext();
		
		text.append("\n数据库测试结果4"
				+ "\n 0=" + cursor.getString(0)
				+ "\n 1=" + cursor.getString(1)
				+ "\n 2=" + cursor.getString(2)
				+ "\n 3=" + cursor.getString(3)
				+ "\n 4=" + cursor.getString(4)
				+ "\n 5=" + cursor.getString(5)
				+ "\n 6=" + cursor.getString(6)
				);
		
		
	}
	
	public boolean isNetAvailable(){
		ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
		
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
