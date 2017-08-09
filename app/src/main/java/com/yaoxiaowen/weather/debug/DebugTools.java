package com.yaoxiaowen.weather.debug;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

public class DebugTools {
	
	private Context mContext = null;

	public DebugTools(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
	}
	
	
	public  void displayToast(String str){
		if(DebugState.DEBUG){
			Toast.makeText(mContext, str, Toast.LENGTH_LONG)
			      .show();
		}
	}
	
	public  void displayToast(boolean state, String str){
		if(DebugState.DEBUG && state){
			Toast.makeText(mContext, str, Toast.LENGTH_LONG)
			      .show();
		}
	}
	
	public  void displayToast(String str, int duration){
		if (DebugState.DEBUG){
			Toast.makeText(mContext, str, duration)
			      .show();
		}
	}
	
	public  void displayToast(boolean state, String str, int duration){
		if (DebugState.DEBUG && state){
			Toast.makeText(mContext, str, duration)
			      .show();
		}
	}
	
	public void displayAlertDialog(String message){
		if (DebugState.DEBUG){
			
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
			.setMessage(message)
			.setPositiveButton("确定", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			});
			builder.create()
					.show();
		}
		
	}
	
	public void displayAlertDialog(boolean state, String message){
		if (DebugState.DEBUG && state){
			
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
			.setMessage(message)
			.setPositiveButton("确定", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			});
			builder.create()
					.show();
		}
		
	}
	
	public void displayAlertDialog(String title, String message){
		if (DebugState.DEBUG){
		
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
			.setTitle(title)
			.setMessage(message)
			.setPositiveButton("确定", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			});
			builder.create()
					.show();
		}
	}

	public void displayAlertDialog(boolean state, String title, String message){
		if (DebugState.DEBUG && state){
		
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
			.setTitle(title)
			.setMessage(message)
			.setPositiveButton("确定", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			});
			builder.create()
					.show();
		}
	}
}















