package com.kz.moni;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
public class EngActicity extends Activity {

	private ViewFlipper mFlipper;
	private TextView mTextView1, mTextView2;
	private int mCurrentLayoutState;
	private int  mCount;
	private GestureDetector mGestureDetector;
	private String[] engAlph = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	private final static String TAG = "EngActivity";
	
	private AudioManager mAudioMgr;
	private SoundPool mSoundPool;
	private int mSoundID;
	// Audio volume
	private float mStreamVolume;
	Context mContext = this;
	
//	private ImageView imgLtr;
	private boolean isRight = false;
	private boolean isMute = false;
	private TextView txtLtr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.eng_acticity);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
       
        isMute = pref.getBoolean("isMute", false);
        log("Sound mute status is " + isMute);
        
		mCurrentLayoutState = 0;

		mFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
		mTextView1 = (TextView) findViewById(R.id.textView1);
		mTextView2 = (TextView) findViewById(R.id.textView2);

		mTextView1.setText(engAlph[mCount]);

		mGestureDetector = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						if (velocityX < -10.0f) {
							mCurrentLayoutState = mCurrentLayoutState == 0 ? 1: 0;
							switchLayoutStateTo(mCurrentLayoutState);

						} 
						
						if (velocityX > +10.0f) {
							isRight = true;
							if (mCount >0 ) {
								mCount--;
							}
							mCurrentLayoutState = mCurrentLayoutState == 0 ? 1: 0;
							switchLayoutStateTo(mCurrentLayoutState);
						}
						
						return true;
					}
				});
	}

	@Override
	protected void onStart() {

		super.onStart();
		mAudioMgr = (AudioManager) getSystemService(AUDIO_SERVICE);

		mStreamVolume = (float) mAudioMgr.getStreamVolume(AudioManager.STREAM_MUSIC);
				/// mAudioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

		mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

		mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
			
			@Override
			public void onLoadComplete(SoundPool soundPool, int sid, int status) {
				Log.i(getClass().getSimpleName(), "Sound is now loaded");
				if (!isMute)
					mSoundPool.play(mSoundID,mStreamVolume,mStreamVolume,1,0,1f);
				
			}
		});

		int myLtr = this.getResources().getIdentifier(engAlph[mCount].toLowerCase(), "raw", this.getPackageName());
		mSoundID = mSoundPool.load(this,myLtr, 1);

		int myImg = this.getResources().getIdentifier("apple", "assets", this.getPackageName());
		log("Imageview checking" + myImg);
//		imgLtr= (ImageView)findViewById(R.id.imgLtr);  
//		imgLtr.setImageResource(myImg);
		txtLtr = (TextView) findViewById(R.id.txtLtr);
		txtLtr.setCompoundDrawablesWithIntrinsicBounds(myImg,0,0,0);
		log("Image loaded");
		
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

		if (!isRight) {
			if (mCount < 25) {
				mCount++;
			} else {
				mCount = 0;
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


		if (switchTo == 0) {
			mTextView1.setText(engAlph[mCount]);
		} else {
			mTextView2.setText(engAlph[mCount]);
		}

		int myLtr = this.getResources().getIdentifier(engAlph[mCount].toLowerCase(), "raw", this.getPackageName());	
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
	private Bitmap getBitmapFromAsset(String strName) throws IOException
	{
	    AssetManager assetManager = getAssets();
	    InputStream istr = assetManager.open(strName);
	    Bitmap bitmap = BitmapFactory.decodeStream(istr);
	    return bitmap;
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
