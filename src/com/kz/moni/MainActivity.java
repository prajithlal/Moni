package com.kz.moni;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final static String TAG = "Main";

	private ImageView imgDora;
	
	final Context cntxt = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		try {

			log("Loading dora");
			imgDora = (ImageView) findViewById(R.id.imgDora);
//			imgDora.getLayoutParams().height=450;
//			imgDora.getLayoutParams().width=300;
			setImageOnDisplay();
			Drawable dr = Drawable.createFromStream(getAssets().open("engImg/dora.png"), null);
			log("Drawable ok");
           imgDora.setImageDrawable(dr);
           log("Dora loaded");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Button btnEng = (Button) findViewById(R.id.btnEngActivity);
		btnEng.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				Toast.makeText(getApplicationContext(), "English alphabets", Toast.LENGTH_SHORT).show();
	            Intent intent = new Intent(cntxt, EngActicity.class);
	            startActivity(intent);
				
			}
		});
		
		Button btnMal = (Button) findViewById(R.id.btnMalActivity);
		btnMal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Toast.makeText(getApplicationContext(), "Mal alphabets", Toast.LENGTH_SHORT).show();
	            Intent intent = new Intent(cntxt, MalActivity.class);
	            startActivity(intent);
			}
		});

		Button btnNumber = (Button) findViewById(R.id.btnNumber);
		btnNumber.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Toast.makeText(getApplicationContext(), "Mal alphabets", Toast.LENGTH_SHORT).show();
	            Intent intent = new Intent(cntxt, NumberActivity.class);
	            startActivity(intent);
			}
		});
		

        
		final ImageView button01 = (ImageView) findViewById(R.id.imgSound);
		button01.setOnClickListener(new OnClickListener() {
	        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(cntxt);
	        SharedPreferences.Editor editor = pref.edit();
		    int button01pos = 0;
		    public void onClick(View v) {
		        if (button01pos == 0) {
		            button01.setImageResource(R.drawable.sound);
		            button01pos = 1;
		            editor.putBoolean("isMute", true);
		        } else if (button01pos == 1) {
		            button01.setImageResource(R.drawable.sound_on);
		            button01pos = 0;
		            editor.putBoolean("isMute", false);
		        }
		        

		        editor.commit();
		        
//		        String txt = pref.getString("acty","");
//		        log("My activity is" + txt);
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
		imgDora.setLayoutParams(parms);
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
