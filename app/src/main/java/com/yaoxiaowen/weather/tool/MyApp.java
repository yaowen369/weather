package com.yaoxiaowen.weather.tool;

import android.app.Application;

public class MyApp extends Application {
	private static MyApp instance;

	public static MyApp getContext(){
		return instance;
	}
	
	public MyApp() {
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
	}
	
	
}
