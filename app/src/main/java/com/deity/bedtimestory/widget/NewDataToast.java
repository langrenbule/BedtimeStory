package com.deity.bedtimestory.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.deity.bedtimestory.R;


/**
 * NewDataToast.java
 * 工程：keytopTITS2.3.3
 * 功能：统一的Toast工具类
 *
 * author    		date          	time      
 * ─────────────────────────────────────────────
 * fengwh          2014-11-4   	  下午上午10:22:05
 *
 * Copyright (c) 2014, KEYTOP All Rights Reserved.
 */
public class NewDataToast extends Toast{
	private static NewDataToast newDataToast;
	
	public NewDataToast(Context context) {
		super(context);
	}

	public static NewDataToast makeText(Context context, CharSequence text){
		return makeText(context, text, Toast.LENGTH_SHORT);
	}
	
	public static NewDataToast makeText(Context context, CharSequence text,int duration){
		if (null==newDataToast) {
			newDataToast = new NewDataToast(context);
		}
		if(TextUtils.isEmpty(text)){
			return null;	
		}
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view=inflater.inflate(R.layout.new_data_toast, null);
		TextView tv=(TextView) view.findViewById(R.id.new_data_toast_message);
		tv.setText(text);
		
		newDataToast.setView(view);
		newDataToast.setDuration(duration);
		return newDataToast;
	}
}
