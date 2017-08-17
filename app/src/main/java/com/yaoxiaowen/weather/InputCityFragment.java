package com.yaoxiaowen.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yaoxiaowen.weather.debug.MyLog;
import com.yaoxiaowen.weather.tool.WeatherStringRes;

public class InputCityFragment extends PreferenceFragment implements OnPreferenceClickListener,
OnSharedPreferenceChangeListener{
	private static final String TAG = "InputCityFragment";
	
	public static final String KEY_UPDATE_FREQUENCY = "update_frequency";
	public static final String KEY_INPUT_CITY = "input_city";

	ListPreference update_frequency;
	EditTextPreference input_city;
	SharedPreferences sp;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		
		
		sp.registerOnSharedPreferenceChangeListener(this);
		addPreferencesFromResource(R.xml.input_city);
		
		update_frequency = (ListPreference)findPreference(KEY_UPDATE_FREQUENCY);
		update_frequency.setOnPreferenceClickListener(this);
		
		input_city = (EditTextPreference)findPreference(KEY_INPUT_CITY);
		input_city.setOnPreferenceClickListener(this);
		
		MyLog.v(TAG, "onCreate()  -> update_frequency = " + update_frequency
							+ "\n  input_city = " + input_city);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MyLog.v(TAG, "onCreateView()");
		
//		View inputCity = inflater.inflate(R.xml.input_city, container, false);
//		
//		return inputCity;
		return super.onCreateView(inflater, container, savedInstanceState);
	}


	@Override
	public boolean onPreferenceClick(Preference pf) {
		MyLog.v(TAG, "onPreferenceClick()");
//		if (pf.getKey().equals(KEY_INPUT_CITY)){
//			String city = sp.getString(KEY_INPUT_CITY, null);
//			input_city.setSummary(city);
//			
//			MyLog.v(TAG, "onPreferenceClick() -> KEY_INPUT_CITY"
//					 + "\n input_city.getText() = " + input_city.getText()
//					 + "\n input_city.getKey() = " + input_city.getKey() 
//					 + "\n city = " + city);
//			
//		}else if (pf.getKey().equals(KEY_UPDATE_FREQUENCY)){
//			String frequency = sp.getString(KEY_UPDATE_FREQUENCY, null);
//			String[] item = getResources().getStringArray(R.array.update_list_name);
//			
//			update_frequency.setSummary(item[ Integer.parseInt(frequency) ]);
//			
//			MyLog.v(TAG, "onPreferenceClick() -> KEY_UPDATE_FREQUENCY" 
//						+ "\n getKey() = " + update_frequency.getKey()
//						+ "\n getValue() = " + update_frequency.getValue()
//						+ "\n getOrder() = " + update_frequency.getOrder()
//						+ "\n getEntryValues() = " + update_frequency.getEntryValues()
//						+ "\n getSummary() = " + update_frequency.getSummary()
//						+ "\n  ===" + item[ Integer.parseInt(frequency)]);
//		}
		
		return true;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
		// TODO Auto-generated method stub
		MyLog.v(TAG, "onSharedPreferenceChanged() -> key = " + key);
		
		if (key.equals(KEY_INPUT_CITY)){
			String city = sp.getString(KEY_INPUT_CITY, null);
			if (city != null){
				input_city.setSummary(city);
			}
			
			sendBC(WeatherStringRes.BC_START_UPDATE_DB);
			
			MyLog.v(TAG, "onSharedPreferenceChanged() -> KEY_INPUT_CITY"
					 + "\n input_city.getText() = " + input_city.getText()
					 + "\n input_city.getKey() = " + input_city.getKey() 
					 + "\n city = " + city);
			
		}else if (key.equals(KEY_UPDATE_FREQUENCY)){
			String frequency = sp.getString(KEY_UPDATE_FREQUENCY, null);
			String[] item = getResources().getStringArray(R.array.update_list_name);
		
			if (frequency != null){
				update_frequency.setSummary(item[ Integer.parseInt(frequency) ]);
			}		
			
			MyLog.v(TAG, "onSharedPreferenceChanged() -> KEY_UPDATE_FREQUENCY" 
						+ "\n getKey() = " + update_frequency.getKey()
						+ "\n getValue() = " + update_frequency.getValue()
						+ "\n getOrder() = " + update_frequency.getOrder()
						+ "\n getEntryValues() = " + update_frequency.getEntryValues()
						+ "\n getSummary() = " + update_frequency.getSummary()
						+ "\n  ===" + item[ Integer.parseInt(frequency)]);
		}
		
	}

	private void sendBC(String action){
		Intent intent = new Intent();
		intent.setAction(action);
		getActivity().sendBroadcast(intent);
	}
}
