package com.kz.moni;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private final static String TAG = "Main";

	final Context cntxt = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		

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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}
