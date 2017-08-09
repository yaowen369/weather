package com.yaoxiaowen.weather;

import com.yaoxiaowen.weather.debug.MyLog;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.text.method.TransformationMethod;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	// YaoWen(43194) modify  at 2017/8/8 21:05
	//Todo 之前为什么会写没用到的tv..导致运行的时候报错
// 	private TextView tv= new TextView(this);

	public MainActivity() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mian);


		//Todo 这个地方需要改写
		//之前的写法会报错
		// YaoWen(43194) modify  at 2017/8/8 20:58
		//Error:(29, 30) 错误: 不兼容的类型: boolean无法转换为TransformationMethod
//		tv.setTransformationMethod(false);
		
		Fragment cityFragment = new InputCityFragment();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.city_container, cityFragment);
		transaction.commit();
		
//		Intent intent = new Intent(this, MyAlarm.class);
//		startActivity(intent);
	}


	
}
