package com.yaoxiaowen.weather;

import java.util.Calendar;

public class GetWeatherPic {
	private static final String TAG = "GetWeatherPic";
	private String weatherType = null;
	private String dayWeatherType = null;
	private String nightWeatherType = null;
	
	//返回关于错误的标记
	public static final int STR_INFO_ERROR = -1;
	public static final int NO_DAY_PIC_ERROR = -2;
	public static final int NO_NIGHT_PIC_ERROR = -3;
	
	
	public GetWeatherPic(String weather) {
		weatherType = weather;
	}
	
	public int getPic(){
		int changeType = weatherType.indexOf("转");
		int drawableId = 0;
		if (changeType > 0) {
			dayWeatherType = weatherType.substring(0, changeType);
			nightWeatherType = weatherType.substring(
					changeType + 1, weatherType.length());
		} else {
			dayWeatherType = weatherType;
			nightWeatherType = weatherType;
		}
		
		if (isDaytime()){
			drawableId =  parseIcon(0, dayWeatherType);
		}else{
			drawableId =  parseIcon(1, nightWeatherType);
		}
		
		return drawableId;
	}
	
	//判断是否是白天
	private boolean isDaytime(){
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		
		if ((hour>7) && (hour<18))
			return true;
		else
			return false;
	}
	
	//根据资源来得到图片信息
	private int parseIcon(int time, String strIcon) {
		if (strIcon == null)
			return STR_INFO_ERROR;
		if (time == 0) {
			if ("晴".equals(strIcon))
				return R.drawable.wether_ico00;
			if ("多云".equals(strIcon))
				return R.drawable.wether_ico01;
			if ("阴".equals(strIcon))
				return R.drawable.wether_ico02;
			if ("阵雨".equals(strIcon))
				return R.drawable.wether_ico03;
			if ("雷阵雨".equals(strIcon))
				return R.drawable.wether_ico04;
			if ("雷阵雨伴有冰雹".equals(strIcon))
				return R.drawable.wether_ico05;
			if ("雨夹雪".equals(strIcon))
				return R.drawable.wether_ico06;
			if ("小雨".equals(strIcon))
				return R.drawable.wether_ico07;
			if ("中雨".equals(strIcon))
				return R.drawable.wether_ico08;
			if ("大雨".equals(strIcon))
				return R.drawable.wether_ico09;
			if ("暴雨".equals(strIcon))
				return R.drawable.wether_ico10;
			if ("大暴雨".equals(strIcon))
				return R.drawable.wether_ico11;
			if ("特大暴雨".equals(strIcon))
				return R.drawable.wether_ico12;
			if ("阵雪".equals(strIcon))
				return R.drawable.wether_ico13;
			if ("小雪".equals(strIcon))
				return R.drawable.wether_ico14;
			if ("中雪".equals(strIcon))
				return R.drawable.wether_ico15;
			if ("大雪".equals(strIcon))
				return R.drawable.wether_ico16;
			if ("暴雪".equals(strIcon))
				return R.drawable.wether_ico17;
			if ("雾".equals(strIcon))
				return R.drawable.wether_ico18;
			if ("冻雨".equals(strIcon))
				return R.drawable.wether_ico19;
			if ("沙尘暴".equals(strIcon))
				return R.drawable.wether_ico20;
			if ("小雨转中雨".equals(strIcon))
				return R.drawable.wether_ico21;
			if ("中雨转大雨".equals(strIcon))
				return R.drawable.wether_ico22;
			if ("大雨转暴雨".equals(strIcon))
				return R.drawable.wether_ico23;
			if ("暴雨转大暴雨".equals(strIcon))
				return R.drawable.wether_ico24;
			if ("大暴雨转特大暴雨".equals(strIcon))
				return R.drawable.wether_ico25;
			if ("小雪转中雪".equals(strIcon))
				return R.drawable.wether_ico26;
			if ("中雪转大雪".equals(strIcon))
				return R.drawable.wether_ico27;
			if ("大雪转暴雪".equals(strIcon))
				return R.drawable.wether_ico28;
			if ("浮尘".equals(strIcon))
				return R.drawable.wether_ico29;
			if ("扬沙".equals(strIcon) || "霾".equals(strIcon))
				return R.drawable.wether_ico30;
			if ("强沙尘暴".equals(strIcon))
				return R.drawable.wether_ico31;
			
			return R.drawable.wether_ico00;
		} else {
			if (strIcon.indexOf("晴") > 0)
				return R.drawable.wether_ico32;
			if (strIcon.indexOf("云") > 0)
				return R.drawable.wether_ico33;
			if (strIcon.indexOf("雨") > 0)
				return R.drawable.wether_ico34;
			if (strIcon.indexOf("雪") > 0)
				return R.drawable.wether_ico35;
			if (strIcon.indexOf("雷") > 0)
				return R.drawable.wether_ico36;
			if (strIcon.indexOf("冰") > 0)
				return R.drawable.wether_ico37;
			
			return R.drawable.wether_ico32;
		}
		// return 0;
	}
}
