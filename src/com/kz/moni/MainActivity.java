package com.kz.moni;

import java.io.IOException;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	private final static String TAG = "Main";

	private ImageView imgDra;
	
	final Context cntxt = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		try {

			imgDra = (ImageView) findViewById(R.id.imgDra);
			int[] d = getScreenSize();
			
			imgDra.getLayoutParams().height=d[1];
			imgDra.getLayoutParams().width=d[0];
//			log("Height " + d[1] + "width " + d[0]);
			Drawable dr = Drawable.createFromStream(getAssets().open("engImg/dora.png"), null);
			log("Drawable ok");
			imgDra.setImageDrawable(dr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ImageView imgAbc = (ImageView) findViewById(R.id.imgAbc);
		imgAbc.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {

	            Intent intent = new Intent(cntxt, EngActicity.class);
	            startActivity(intent);
		    }
		});
				
		ImageView imgMal = (ImageView) findViewById(R.id.imgMal);
		imgMal.setOnClickListener(new OnClickListener() {

		    public void onClick(View v) {
	            Intent intent = new Intent(cntxt, MalActivity.class);
	            startActivity(intent);
		    }
		});
		
		ImageView imgNum = (ImageView) findViewById(R.id.imgNum);
		imgNum.setOnClickListener(new OnClickListener() {

		    public void onClick(View v) {
	            Intent intent = new Intent(cntxt, NumberActivity.class);
	            startActivity(intent);
		    }
		});

		final ImageView imgSnd = (ImageView) findViewById(R.id.imgSnd);
		imgSnd.setOnClickListener(new OnClickListener() {
	        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(cntxt);
	        SharedPreferences.Editor editor = pref.edit();
		    int mute = 0;

		    public void onClick(View v) {
		        log("img sound2 click");
		        if (mute == 0) {
		        	imgSnd.setImageResource(R.drawable.sound);
		            mute = 1;
		            editor.putBoolean("isMute", true);
		        } else if (mute == 1) {
		        	imgSnd.setImageResource(R.drawable.sound_on);
		            mute = 0;
		            editor.putBoolean("isMute", false);
		        }
		        editor.commit();

		    }
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	private int[] getScreenSize(){
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
//		log("Height " + metrics.heightPixels + "width " + metrics.widthPixels);
		
		int [] d= new int[2];
		d[0] = metrics.widthPixels/2; 
		d[1] = metrics.heightPixels;

//		LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
//		imgDra.setLayoutParams(parms);
		return(d);
	}
	
	
	private void log(String msg) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Log.i(TAG, msg);
	}
}
