package com.yaoxiaowen.weather;

import com.yaoxiaowen.weather.debug.MyLog;
import com.yaoxiaowen.weather.tool.WeatherStringRes;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper{
	private static final String TAG = "MyDatabaseHelper";
	 
	private static final String COMMA = ","; //代表一个逗号
	private static final String SPACE = " ";  //代表一个空格
	
	private static final String CREATE_TABLE_SQL = 
				  "create table " + WeatherStringRes.DB_TABLE_NAME + "(" + "_id integer primary key autoincrement, "
				+ WeatherStringRes.FIELD_DATE + COMMA + SPACE
				+ WeatherStringRes.FIELD_DAY_PICTURE_URL + COMMA + SPACE
				+ WeatherStringRes.FIELD_NIGHT_PICTURE_URL + COMMA + SPACE
				+ WeatherStringRes.FIELD_WEATHER + COMMA + SPACE
				+ WeatherStringRes.FIELD_WIND + COMMA + SPACE
				+ WeatherStringRes.FIELD_TEMPERATURE
			    + ")";
	
	public MyDatabaseHelper(Context context, String name, int version) {
		// TODO Auto-generated constructor stub
		super(context, name, null, version);
		MyLog.v(TAG, "MyDatabaseHelper()");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		MyLog.v(TAG, "onCreate()");
		db.execSQL(CREATE_TABLE_SQL);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
