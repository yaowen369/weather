package com.yaoxiaowen.weather;

import com.yaoxiaowen.weather.debug.MyLog;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.widget.RemoteViews;
import android.widget.RemoteViews.RemoteView;

public class MainWidgetProvider extends AppWidgetProvider {

	private static final String TAG = "MainWidgetProvider";
	public MainWidgetProvider() {
		// TODO Auto-generated constructor stub
		MyLog.v(TAG, "MainWidgetProvider()");
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
		MyLog.v(TAG, "onDeleted()");
	}

	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		MyLog.v(TAG, "onDisabled()");
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		MyLog.v(TAG, "onEnabled()");
		super.onEnabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		MyLog.v(TAG, "onReceive()");
		
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
     	super.onUpdate(context, appWidgetManager, appWidgetIds);
		MyLog.v(TAG, "onUpdate()");
		Intent intent = new Intent(context, MainWidgetService.class);
		context.startService(intent);
		
	}
	
	
}
