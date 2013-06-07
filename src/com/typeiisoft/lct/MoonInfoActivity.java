package com.typeiisoft.lct;

import com.typeiisoft.lct.utils.AppPreferences;
import com.typeiisoft.lct.utils.MoonInfo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * This class handles displaying the Moon information via an activity and 
 * view.
 * 
 * @author Michael Reuter
 *
 */
public class MoonInfoActivity extends Activity {
	/** Logging tag. */
	private static final String TAG = "MoonInfoActivity";
	/** The application preferences. */
	private AppPreferences appPrefs;
	
	/**
	 * This function creates the MoonInfo activity.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mooninfo);

		this.appPrefs = new AppPreferences(this.getApplicationContext());
		
		MoonInfo moonInfo = new MoonInfo(appPrefs.getDateTime());
		Log.w(TAG, "MoonInfo: " + moonInfo.toString());
		
		String[] dateTime = moonInfo.obsLocalTime();
		this.appendText(R.id.obsdate_label, dateTime[0]);
		this.appendText(R.id.obstime_label, dateTime[1]);
		this.appendText(R.id.moon_phase_label, moonInfo.phase());
		this.appendText(R.id.moon_age_label, moonInfo.age());
		this.appendText(R.id.moon_illum_label, moonInfo.illumation());
		this.appendText(R.id.moon_colong_label, moonInfo.colong());
	}
	
	/**
	 * This function handles appending text to the labels that are already 
	 * displayed on the layout.
	 * @param layoutResId
	 * @param more_text
	 */
	private void appendText(int layoutResId, String more_text) {
		TextView tv = (TextView) this.findViewById(layoutResId);
		String cur_text = tv.getText().toString();
		StringBuffer buff = new StringBuffer(cur_text).append(" ").append(more_text);
		tv.setText(buff);
	}
}
