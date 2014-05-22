package com.kz.moni;

import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class MoniPreferences {

	private SharedPreferences pref;
	
	public void setMoniPreference(Context mContext, String prefName,boolean val){
		pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(prefName, val);
        editor.commit();
	}
	
	public boolean getMoniPreference(Context mContext,String prefName) {
		boolean val = false;

		pref = PreferenceManager.getDefaultSharedPreferences(mContext);
		val = pref.getBoolean(prefName, false);
		
		return val;
	}
	
	public int getColor(Context mContext) {
		int clrId=1;
		Random r = new Random();
		int clr = r.nextInt(8 - 1) + 1;

		switch(clr){
		
			case 1:
				clrId = mContext.getResources().getColor(R.color.bg1);
				break;
			case 2:
				clrId = mContext.getResources().getColor(R.color.bg2);
				break;
			case 3:
				clrId = mContext.getResources().getColor(R.color.bg3);
				break;
			case 4:
				clrId = mContext.getResources().getColor(R.color.bg4);
				break;
			case 5:
				clrId = mContext.getResources().getColor(R.color.bg5);
				break;
			case 6:
				clrId = mContext.getResources().getColor(R.color.bg6);
				break;
			case 7:
				clrId = mContext.getResources().getColor(R.color.bg7);
				break;

		}

		return clrId;
	}
	public class MoniConstants {
			public final static String MUTE_FLAG = "isMute";
			
	}
	
}
