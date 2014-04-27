package com.kz.moni;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Typeface;
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
public class MalActivity extends Activity {

	private ViewFlipper mFlipper;
	private TextView mTextView1, mTextView2;
	private int mCurrentLayoutState;
	private int  mCount;
	private GestureDetector mGestureDetector;

	private AudioManager mAudioMgr;
	private SoundPool mSoundPool;
	private int mSoundID;
	// Audio volume
	private float mStreamVolume;
	Context mContext = this;
	
	private String[] malAlph = {"A", "B","C", "Cu", "D", "Du", "E", "F", "G", "sF", "H", "Hm", "Hu", "Aw", "Ax", 
			"I", "J", "K", "L", "M",
			"N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W",
			"X", "Y", "Z", "[", "\\",
			"]", "^", "_", "`", "a",
			"b", "c",  "e","h", "i","j", "k","l","d", "f", "g" };
	
	private String[] malSound = {"a","aa","i","ee","u","oo","rr","e","ae","ai","o","oa","ow","um","ah",
			"k","kh","g","gh","ng",
			"ch","chh","j","zh","nj",
			"t","tdh","d","dhv","tn",
			"tv","th","dv","dh","n",
			"p","ph","b","bh","m",
			"y","r","l","w","sh","hsh","s","h","xr","mhl","zh"};
	private final static String TAG = "MalActivity";

	private boolean isRight = false;
	private boolean isMute = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mal_activity);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        
        isMute = pref.getBoolean("isMute", false);
        log("Sound mute status is " + isMute);
        log ("Letter size " + malAlph.length + "Sound len " + malSound.length);
        
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
							if (mCount < malAlph.length) {
								mCount++;
							} else {
								mCount = 0;
							}
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
		// TODO Auto-generated method stub
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

		int myLtr = this.getResources().getIdentifier("m"+malSound[mCount], "raw", this.getPackageName());
		mSoundID = mSoundPool.load(this,myLtr, 1);

	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

	
	public void switchLayoutStateTo(int switchTo) {
		mCurrentLayoutState = switchTo;

		if (isRight) {

			mFlipper.setInAnimation(inFromLefttAnimation());
			mFlipper.setOutAnimation(outToRighttAnimation());
			isRight=false;
		} else {
			mFlipper.setInAnimation(inFromRightAnimation());
			mFlipper.setOutAnimation(outToLeftAnimation());
		}

		
		if (switchTo == 0) {
			mTextView1.setText(malAlph[mCount]);
		} else {
			mTextView2.setText(malAlph[mCount]);
		}

		int myLtr = this.getResources().getIdentifier("m"+malSound[mCount], "raw", this.getPackageName());	
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
