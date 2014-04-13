package com.kz.moni;

import com.kz.moni.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class EngActicity extends Activity {

	private ViewFlipper mFlipper;
	private TextView mTextView1, mTextView2;
	private int mCurrentLayoutState;
	private int  mCount;
	private GestureDetector mGestureDetector;
	private String[] engAlph = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	private final static String TAG = "Main";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.eng_acticity);

		mCurrentLayoutState = 0;

		mFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
		mTextView1 = (TextView) findViewById(R.id.textView1);
		mTextView2 = (TextView) findViewById(R.id.textView2);

//		mTextView1.setText(String.valueOf(mCount));
		mTextView1.setText(engAlph[mCount]);

		mGestureDetector = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						if (velocityX < -10.0f) {
							log("On right fling");
							mCurrentLayoutState = mCurrentLayoutState == 0 ? 1
									: 0;
							switchLayoutStateTo(mCurrentLayoutState);
							if (mCount < 25) {
								mCount++;
							}
						} else {
							log("On left fling");
							if (mCount >0 ) {
								mCount--;
							}
							mCurrentLayoutState = mCurrentLayoutState == 0 ? 1
									: 0;
							switchLayoutStateTo(mCurrentLayoutState);
						}
						return true;
					}
				});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

	
	public void switchLayoutStateTo(int switchTo) {
		mCurrentLayoutState = switchTo;

		mFlipper.setInAnimation(inFromRightAnimation());
		mFlipper.setOutAnimation(outToLeftAnimation());



		if (switchTo == 0) {
			mTextView1.setText(engAlph[mCount]);
		} else {
			mTextView2.setText(engAlph[mCount]);
		}

		mFlipper.showPrevious();
	}

	private Animation inFromRightAnimation() {
		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(500);
		inFromRight.setInterpolator(new LinearInterpolator());
		return inFromRight;
	}

	private Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoLeft.setDuration(500);
		outtoLeft.setInterpolator(new LinearInterpolator());
		return outtoLeft;
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
