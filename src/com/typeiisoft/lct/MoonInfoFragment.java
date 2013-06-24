package com.typeiisoft.lct;

import com.typeiisoft.lct.utils.AppPreferences;
import com.typeiisoft.lct.utils.MoonInfo;

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
		Log.w(TAG, "MoonInfo: " + moonInfo.toString());
		
		String[] dateTime = moonInfo.obsLocalTime();
		this.appendText(R.id.obsdate_label, dateTime[0]);
		this.appendText(R.id.obstime_label, dateTime[1]);
		this.appendText(R.id.moon_phase_label, moonInfo.phase());
		this.appendText(R.id.moon_age_label, moonInfo.age());
		this.appendText(R.id.moon_illum_label, moonInfo.illumation());
		this.appendText(R.id.moon_colong_label, moonInfo.colong());

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
