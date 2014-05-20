package com.kz.moni;

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
	
	public class MoniConstants {
			public final static String MUTE_FLAG = "isMute";
			
	}
	
}
