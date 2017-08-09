package com.yaoxiaowen.weather;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.yaoxiaowen.weather.debug.MyLog;
import com.yaoxiaowen.weather.tool.MyApp;
import com.yaoxiaowen.weather.tool.WeatherStringRes;

public class SetDataToDB {
	
	private static final String TAG = "SetDataToDB";
	
	private MyDatabaseHelper dbHelper;
	SQLiteDatabase db;
	
	
	public SetDataToDB() {
		// TODO Auto-generated constructor stub
		dbHelper = new MyDatabaseHelper(MyApp.getContext(), WeatherStringRes.DB_NAME, 1);
		db = dbHelper.getReadableDatabase();
	}
	
	private void initDB(SQLiteDatabase db){
		String execSQL = "insert into " + WeatherStringRes.DB_TABLE_NAME + " values(null, ?, ?, ?, ?, ?, ?)";
		
		//第一行
		db.execSQL(execSQL,
				new String[]{
					null, 
					null, 
					null, 
					null, 
					null, 
					null
					});
		
		//第二行
			db.execSQL(execSQL,
					new String[]{
						null, 
						null, 
						null, 
						null, 
						null, 
						null
						});
			
			//第三行
			db.execSQL(execSQL,
					new String[]{
						null, 
						null, 
						null, 
						null, 
						null, 
						null
						});
			
			//第四行
			db.execSQL(execSQL,
					new String[]{
						null, 
						null, 
						null, 
						null, 
						null, 
						null
						});
	}  // end of "initDB()"

	public void updateDateToDB(ContentValues values, int num){
		
		if (num == 0){
			initDB(db);
		}
		
		updateDateToDB(db, values, num);
	}
	
	private void updateDateToDB(SQLiteDatabase db, ContentValues values, int num){
		MyLog.v(TAG, "updateDateToDB() -> num=" + (num + 1)
					+ "\n values=" + values.toString());
		db.update(WeatherStringRes.DB_TABLE_NAME, values, "_id=" + (num + 1), null);
	}
}
