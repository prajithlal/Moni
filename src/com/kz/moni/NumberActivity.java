package com.kz.moni;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.kz.moni.MoniPreferences.MoniConstants;
import com.kz.moni.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class NumberActivity extends Activity {

	private ViewFlipper mFlipper;
	private TextView mTextView1, mTextView2;
	private int mCurrentLayoutState;
	private int mCount=1;
	private boolean isRight = false;
	private GestureDetector mGestureDetector;
	
	private final static String TAG = "NumActivity";
	
	private AudioManager mAudioMgr;
	private SoundPool mSoundPool;
	private int mSoundID;
	// Audio volume
	private float mStreamVolume;
	private boolean isMute = false;
	
	Context mContext;
	private MoniPreferences mPref;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.number_activity);

		mPref = new MoniPreferences();
		isMute = mPref.getMoniPreference(mContext, MoniConstants.MUTE_FLAG);
		
		mCurrentLayoutState = 0;

		mFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
		mTextView1 = (TextView) findViewById(R.id.textView1);
		mTextView2 = (TextView) findViewById(R.id.textView2);

		mTextView1.setText(String.valueOf(mCount));

		mGestureDetector = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						log("On Num fling method");
						if (velocityX < -10.0f) {
							mCurrentLayoutState = mCurrentLayoutState == 0 ? 1: 0;
							switchLayoutStateTo(mCurrentLayoutState);
						}
						if (velocityX > +10.0f) {
							isRight = true;
							if (mCount >1 ) {
								mCount--;
							}
							mCurrentLayoutState = mCurrentLayoutState == 0 ? 1: 0;
							switchLayoutStateTo(mCurrentLayoutState);
						}
						return true;
					}
				});
	}

	//Load soundpool on resume activity
	@Override
	protected void onResume() {
		super.onResume();



	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		mAudioMgr = (AudioManager) getSystemService(AUDIO_SERVICE);

		mStreamVolume = (float) mAudioMgr.getStreamVolume(AudioManager.STREAM_MUSIC)
				/ mAudioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

		mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

		mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
			
			@Override
			public void onLoadComplete(SoundPool soundPool, int sid, int status) {
				
				if (!isMute) {
					mSoundPool.play(mSoundID,mStreamVolume,mStreamVolume,1,0,1f);
				}
			}
		});
		
		int myLtr = this.getResources().getIdentifier("a"+mCount, "raw", this.getPackageName());
		mSoundID = mSoundPool.load(this,myLtr, 1);

		
//		Bitmap bm;
//		try {
//			bm = getBitmapFromAsset("apple.png");
//			txtLtr.setCompoundDrawablesWithIntrinsicBounds(bm, 0,0, 0);
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
@Override
public boolean onTouchEvent(MotionEvent event) {
	return mGestureDetector.onTouchEvent(event);
}

public void switchLayoutStateTo(int switchTo) {
	mCurrentLayoutState = switchTo;
	
	if (!isRight){
		if (mCount<10){
			mCount++;
		}else {
			mCount=1;
		}
	}
	
		if (isRight) {

			mFlipper.setInAnimation(inFromLefttAnimation());
			mFlipper.setOutAnimation(outToRighttAnimation());
			isRight=false;
		} else {
			mFlipper.setInAnimation(inFromRightAnimation());
			mFlipper.setOutAnimation(outToLeftAnimation());
		}



	if(mCount==10){
		float fontSize = getResources().getDimension(R.dimen.numL_font_size);
		mTextView1.setTextSize(fontSize);
		mTextView2.setTextSize(fontSize);
	} else {
		float fontSize = getResources().getDimension(R.dimen.numS_font_size);
		mTextView1.setTextSize(fontSize);
		mTextView2.setTextSize(fontSize);
	}
	if (switchTo == 0) {
		mTextView1.setText(String.valueOf(mCount));
	} else {
		mTextView2.setText(String.valueOf(mCount));
	}

	int myLtr = this.getResources().getIdentifier("a"+mCount, "raw", this.getPackageName());	
	mSoundID = mSoundPool.load(this,myLtr, 1);

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

private Animation inFromLefttAnimation() {
	Animation inFromLeft = new TranslateAnimation(
			Animation.RELATIVE_TO_PARENT, -1.0f,
			Animation.RELATIVE_TO_PARENT, 0.0f,
			Animation.RELATIVE_TO_PARENT, 0.0f,
			Animation.RELATIVE_TO_PARENT, 0.0f);
	inFromLeft.setDuration(500);
	inFromLeft.setInterpolator(new LinearInterpolator());
	return inFromLeft;
}

private Animation outToRighttAnimation() {
	Animation outtoRight = new TranslateAnimation(
			Animation.RELATIVE_TO_PARENT, 0.0f,
			Animation.RELATIVE_TO_PARENT, +1.0f,
			Animation.RELATIVE_TO_PARENT, 0.0f,
			Animation.RELATIVE_TO_PARENT, 0.0f);
	outtoRight.setDuration(500);
	outtoRight.setInterpolator(new LinearInterpolator());
	return outtoRight;
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
