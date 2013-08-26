package com.typeiisoft.lct;

import com.typeiisoft.lct.utils.AppPreferences;
import com.typeiisoft.lct.utils.MoonInfo;
import com.typeiisoft.lct.utils.StrFormat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * This class handles displaying the Moon information via an activity and 
 * view.
 * 
 * @author Michael Reuter
 */
public class MoonInfoFragment extends Fragment {
	/** Logging identifier. */
	private static final String TAG = MoonInfoFragment.class.getName();
	/** The application preferences. */
	private AppPreferences appPrefs;
	/** View for the fragment. */
	private View view;
	
	/**
	 * This function creates the Moon information fragment.
	 * @param inflater : The object that creates the view.
	 * @param container : The layout container for the view.
	 * @param savedInstanceState : Object containing any state information.
	 * @return : The view for the fragment.
	 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	this.view = inflater.inflate(R.layout.mooninfo, container, false);
    	
    	this.appPrefs = new AppPreferences(this.getActivity());
		MoonInfo moonInfo = new MoonInfo(appPrefs.getDateTime());
		Log.i(TAG, "MoonInfo: " + moonInfo.toString());
		
		String[] localDateTime = StrFormat.dateFormatNoSeconds(moonInfo.getObsLocal()).split(" ");
		String localDateStr = "(" + localDateTime[2] + ") " + localDateTime[0] + " " + localDateTime[1];
		this.appendText(R.id.local_date_tv, localDateStr);
		String[] utcDateTime = StrFormat.dateFormatNoSeconds(moonInfo.getObsUtc()).split(" ");
		this.appendText(R.id.utc_date_tv, " " + utcDateTime[0] + " " + utcDateTime[1]);
		this.appendText(R.id.moon_phase_angle_tv, moonInfo.phase());
		String ageStr = StrFormat.formatDouble(moonInfo.age(), 2) + " days";
		this.appendText(R.id.moon_age_tv, ageStr);
		String illumStr = StrFormat.formatDouble(moonInfo.illumation() * 100.0, 1) + "%";
		this.appendText(R.id.moon_illum_tv, illumStr);
		String colongStr = StrFormat.dmsFromDd(moonInfo.colong(), false);
		this.appendText(R.id.moon_colong_tv, colongStr);

		this.appendText(R.id.new_moon_tv, 
				StrFormat.dateFormatNoSeconds(moonInfo.previousNewMoon()));
		this.appendText(R.id.fq_moon_tv, 
				StrFormat.dateFormatNoSeconds(moonInfo.nextFirstQuarterMoon()));
		this.appendText(R.id.full_moon_tv, 
				StrFormat.dateFormatNoSeconds(moonInfo.nextFullMoon()));
		this.appendText(R.id.tq_moon_tv, 
				StrFormat.dateFormatNoSeconds(moonInfo.nextThirdQuarterMoon()));
		
    	return this.view;
    }

	/**
	 * This function handles appending text to the labels that are already 
	 * displayed on the layout.
	 * @param layoutResId : The requested resource ID.
	 * @param more_text : The extra text to add the the labels.
	 */
	private void appendText(int layoutResId, String more_text) {
		TextView tv = (TextView) this.view.findViewById(layoutResId);
		String cur_text = tv.getText().toString();
		StringBuffer buff = new StringBuffer(cur_text).append(" ").append(more_text);
		tv.setText(buff);
	}
}
