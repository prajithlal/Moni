package com.kz.moni;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.kz.moni.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class MalActivity extends Activity {

	private ViewFlipper mFlipper;
	private TextView mTextView1, mTextView2;
	private int mCurrentLayoutState;
	private int  mCount;
	private GestureDetector mGestureDetector;

	private String[] malAlph = {"A", "B","C", "Cu", "D", "Du", "E", "F", "G", "sF", "H", "Hm", "Hu", "Aw", "A", 
			"I", "J", "K", "L", "M",
			"N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W",
			"X", "Y", "Z", "[", "\\",
			"]", "^", "_", "`", "a",
			"b", "c", "d", "e", "f", "g", "h", "i", "j", "k","l"};
	private final static String TAG = "MalActivity";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mal_activity);


		mCurrentLayoutState = 0;

		mFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
		mTextView1 = (TextView) findViewById(R.id.textView1);
		mTextView2 = (TextView) findViewById(R.id.textView2);

		Typeface typeFace = Typeface.createFromAsset(getAssets(),"ML_TT_Karthika_Normal1.ttf");
		mTextView1.setTypeface(typeFace);
		mTextView2.setTypeface(typeFace);
		Resources res = getResources();
		float fontSize = res.getDimension(R.dimen.mal_font_size);
		mTextView1.setTextSize(fontSize);
		mTextView2.setTextSize(fontSize);
		mTextView1.setText(malAlph[mCount]);

		mGestureDetector = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						if (velocityX < -10.0f) {
							log("On right fling");
							mCurrentLayoutState = mCurrentLayoutState == 0 ? 1: 0;
							switchLayoutStateTo(mCurrentLayoutState);

						} else {
							log("On left fling");
							if (mCount >0 ) {
								mCount--;
							}
							mCurrentLayoutState = mCurrentLayoutState == 0 ? 1	: 0;
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

		if (mCount < malAlph.length) {
			mCount++;
		} else {
			mCount = 0;
		}
		mFlipper.setInAnimation(inFromRightAnimation());
		mFlipper.setOutAnimation(outToLeftAnimation());
		
		if (switchTo == 0) {
			mTextView1.setText(malAlph[mCount]);
		} else {
			mTextView2.setText(malAlph[mCount]);
		}

//		int myLtr = this.getResources().getIdentifier(malAlph[mCount].toLowerCase(), "raw", this.getPackageName());	
//		mSoundID = mSoundPool.load(this,myLtr, 1);
				
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
