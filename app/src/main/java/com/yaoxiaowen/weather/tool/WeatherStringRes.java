package com.yaoxiaowen.weather.tool;

public class WeatherStringRes {
	
	//xml文件中的标签
	public static final String STATUS = "status"; 
	public static final String  DATE = "date";
	public static final String RESULTS = "results";
	public static final String CURRENT_CITY = "currentCity";
	public static final String WEATHER_DATA = "weather_data";
	public static final String DAY_PICTURE_URL = "dayPictureUrl";
	public static final String NIGHT_PICTURE_URL = "nightPictureUrl";
	public static final String WEATHER = "weather";
	public static final String WIND = "wind";
	public static final String TEMPERATURE = "temperature";

	//SharePreferences中用到的字符
	public static final String SP_NAME = "weather_sp";
	public static final String SP_DATE = "date";
	public static final String SP_CURRENT_CITY = "currentCity";
	public static final String SP_REAL_TEMP = "realTemperature";
	
	
	//在数据库中需要用到的列名等资料
	public static final String DB_NAME = "yw_weather_db";
	public static final String DB_TABLE_NAME = "weather_date_info"; 
	public static final String FIELD_DATE = "date";
	public static final String FIELD_DAY_PICTURE_URL = "dayPictureUrl";
	public static final String FIELD_NIGHT_PICTURE_URL = "nightPictureUrl";
	public static final String FIELD_WEATHER = "weather";
	public static final String FIELD_WIND = "wind";
	public static final String FIELD_TEMPERATURE = "temperature";
	
	public static final String test = "";
	public static final String FIRST_INFO = "first_info";
	public static final String SECOND_INFO = "second_info";
	public static final String THIRD_INFO = "third_info";

	//关于一些特殊的符号
	public static final char celsiusUnit = '℃';
	public static final String virgule = "/";
	
	//Activit与Server的action与category
	public static final String MainWidgetService_action = "com.yaoxiaowen.weather.action.MainWidgetService";
	public static final String MainWidgetService_category = "com.yaoxiaowen.weather.category.MainWidgetService";
	
	//广播用到的信息
	public static final String BC_START_UPDATE_DB = "com.yaoxiaowen.weather.bc.start_update_db";
	public static final String BC_UPDATED_COMPLETE = "com.yaoxiaowen.weather.bc.db_updated_complete";
	
	public WeatherStringRes() {
		// TODO Auto-generated constructor stub
	}

}
