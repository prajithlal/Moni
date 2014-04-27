package com.kz.moni;

import java.io.IOException;

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

public class MainActivity extends Activity {

	private final static String TAG = "Main";

	private ImageView imgDra;
	
	final Context cntxt = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		try {

			log("Loading dora");
			imgDra = (ImageView) findViewById(R.id.imgDra);
			imgDra.getLayoutParams().height=450;
			imgDra.getLayoutParams().width=300;
//			setImageOnDisplay();
			Drawable dr = Drawable.createFromStream(getAssets().open("engImg/dora.png"), null);
			log("Drawable ok");
			imgDra.setImageDrawable(dr);
           log("Dora loaded");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		Button btnEngNew = (Button) findViewById(R.id.btnEngActy);
		btnEngNew.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				Toast.makeText(getApplicationContext(), "English alphabets", Toast.LENGTH_SHORT).show();
	            Intent intent = new Intent(cntxt, EngActicity.class);
	            startActivity(intent);
				
			}
		});
		
		Button btnMalNew = (Button) findViewById(R.id.btnMalActy);
		btnMalNew.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Toast.makeText(getApplicationContext(), "Mal alphabets", Toast.LENGTH_SHORT).show();
	            Intent intent = new Intent(cntxt, MalActivity.class);
	            startActivity(intent);
			}
		});

		Button btnNumNew = (Button) findViewById(R.id.btnNum);
		btnNumNew.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Toast.makeText(getApplicationContext(), "Mal alphabets", Toast.LENGTH_SHORT).show();
				log("test");
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


	private void setImageOnDisplay(){
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int width = metrics.widthPixels/2+50;  
		int height = metrics.heightPixels/2+100;
		log("Height " + height + "width " + width);
		LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
		imgDra.setLayoutParams(parms);
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
