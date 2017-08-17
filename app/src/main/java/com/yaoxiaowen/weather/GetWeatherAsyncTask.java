package com.yaoxiaowen.weather;

import com.yaoxiaowen.weather.debug.MyLog;
import com.yaoxiaowen.weather.tool.MyApp;
import com.yaoxiaowen.weather.tool.WeatherStringRes;
import android.app.backup.BackupAgent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

public class GetWeatherAsyncTask extends AsyncTask<String, Void, String> {
	private static final String TAG = "GetWeatherAsyncTask";
	
	private TextView text;
	private BaiduWeather mBaibuWeather;
	private Context context;
	
	public GetWeatherAsyncTask(TextView text, Context context) {
		this.text = text;
		this.context = context;
	}
	
	public GetWeatherAsyncTask(TextView text){
		this.text = text;
	}
	
	public GetWeatherAsyncTask(Context context) {
		this.context = context;
	}

	
	@Override
	protected void onPreExecute() {
		MyLog.v(TAG, "onPreExecute()");
		mBaibuWeather = new BaiduWeather();
	}



	@Override
	protected String doInBackground(String... arg0) {
		MyLog.v(TAG, "doInBackground()");
		return mBaibuWeather.getWeather(arg0[0]);
	}
	
	@Override
	protected void onPostExecute(String result) {
		MyLog.v(TAG, "onPostExecute()");
//		text.setText(result);
		if (result.equals(BaiduWeather.SUCCESS)){
			MyLog.v(TAG, "onPostExecute() -> SUCCESS");
			Intent intent = new Intent();
			intent.setAction(WeatherStringRes.BC_UPDATED_COMPLETE);
			MyApp.getContext().sendBroadcast(intent);
			
		}
		
	}

}
