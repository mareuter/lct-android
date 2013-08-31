package com.typeiisoft.lct;

import java.util.Calendar;
import java.util.Map;

import com.mhuss.AstroLib.Lunar;
import com.typeiisoft.lct.utils.AppPreferences;
import com.typeiisoft.lct.utils.MoonInfo;
import com.typeiisoft.lct.utils.StrFormat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
		this.appendText(R.id.local_date_label_tv, "(" + localDateTime[2] + ")");
		String localDateStr =  localDateTime[0] + " " + localDateTime[1];
		this.appendText(R.id.local_date_tv, localDateStr);
		String[] utcDateTime = StrFormat.dateFormatNoSeconds(moonInfo.getObsUtc()).split(" ");
		this.appendText(R.id.utc_date_tv, utcDateTime[0] + " " + utcDateTime[1]);
		this.appendText(R.id.moon_phase_tv, moonInfo.phase());
		String ageStr = StrFormat.formatDouble(moonInfo.age(), 2) + " days";
		this.appendText(R.id.moon_age_tv, ageStr);
		String illumStr = StrFormat.formatDouble(moonInfo.illumation() * 100.0, 1) + "%";
		this.appendText(R.id.moon_illum_tv, illumStr);
		String colongStr = StrFormat.dmsFromDd(moonInfo.colong(), false);
		this.appendText(R.id.moon_colong_tv, colongStr);

		// Find the dates for next four lunar phases.
		Map<Calendar, Integer> phases = moonInfo.findNextFourPhases();
		
		int[] phaseTextViews = {R.id.first_phase_tv, R.id.second_phase_tv,
				R.id.third_phase_tv, R.id.fourth_phase_tv};

		int[] phaseImageViews = {R.id.first_phase_iv, R.id.second_phase_iv,
				R.id.third_phase_iv, R.id.fourth_phase_iv};
		
		// Calendar keys are sorted in most recent first. Need to reverse it.
		int counter = 3;
		for (Map.Entry<Calendar, Integer> entry : phases.entrySet()) {
			Calendar cal = entry.getKey();
			Integer phase = entry.getValue();
			this.setPhaseIcon(phaseImageViews[counter], this.getPhaseIcon(phase));
			this.appendText(phaseTextViews[counter], StrFormat.dateFormatNoSeconds(cal));
			counter -= 1;
		}
		
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
	
	/**
	 * This function gets the phase icon drawable to the corresponding value.
	 * @param i : The phase icon value.
	 * @return : The corresponding drawable.
	 */
	private int getPhaseIcon(Integer i) {
		int phase = i.intValue();
		int drawable;
		switch (phase) {
		case Lunar.NEW:
			drawable = R.drawable.ic_new_moon;
			break;
		case Lunar.Q1:
			drawable = R.drawable.ic_fq_moon;
			break;
		case Lunar.FULL:
			drawable = R.drawable.ic_full_moon;
			break;
		case Lunar.Q3:
			drawable = R.drawable.ic_tq_moon;
			break;
		default:
			// Bad stuff happened to get here.
			drawable = -1;
			break;
		}
		return drawable;
	}
	
	/**
	 * This function sets the drawable to the given ImageView.
	 * @param layoutResId : The ImageView to set the drawable to.
	 * @param drawable : The drawable to use.
	 */
	private void setPhaseIcon(int layoutResId, int drawable) {
		ImageView iv = (ImageView)this.view.findViewById(layoutResId);
		iv.setImageResource(drawable);
	}
}
