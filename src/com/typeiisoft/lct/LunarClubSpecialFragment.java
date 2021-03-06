package com.typeiisoft.lct;

import java.util.Calendar;

import com.mhuss.AstroLib.Astro;
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
 * This class handles the naked eye observations for the Lunar Club that 
 * are not directly related to features on the Moon.
 * 
 * @author Michael Reuter
 */
public class LunarClubSpecialFragment extends Fragment {
	/** Logging identifier. */
	private static final String TAG = LunarClubSpecialFragment.class.getName();
	/** The maximum number of hours to/from new Moon for observations. */
	private final double TIME_CUTOFF = 72.0;
	/** The maximum number of hours for the age of the waxing crescent. */
	private final double TIME_CRESCENT_WAXING = 40.0;
	/** The maximum number of hours for the age of the waning crescent. */
	private final double TIME_CRESCENT_WANING = 48.0;
	/** Time range in days for the Cow Jumping over the Moon observation. */
	private final double[] TIME_COW_JUMPING = new double[]{2.0, 3.0};
	/** Illuminated fraction for Full Moon. */
	private final double FULL_MOON_FRACTION = 0.987;
	/** The application preferences. */
	private AppPreferences appPrefs;
	/** View for the fragment. */
	private View view;

	/**
	 * This function is the instance constructor.
	 * @return : A new instance of the object.
	 */
	static LunarClubSpecialFragment newInstance() {
		return new LunarClubSpecialFragment();
	}

	/**
	 * This function creates the Lunar Club special view.
	 * @param inflater : The object that creates the view.
	 * @param container : The layout container for the view.
	 * @param savedInstanceState : Object containing any state information.
	 * @return : The view for the fragment.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "Creating Special tab.");
		// Inflate the layout for this fragment
		this.view = inflater.inflate(R.layout.lc_special, container, false);
    	return this.view;
	}

	/**
	 * This function sets the information and status of the items in the 
	 * view.
	 * @param saveInstanceState : The object containing instance saved state.
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
    	this.appPrefs = new AppPreferences(this.getActivity());
		MoonInfo moonInfo = new MoonInfo(appPrefs.getDateTime());
		Log.i(TAG, "MoonInfo: " + moonInfo.toString());
		
		// Time from new Moon calculations
		Calendar nmCal = moonInfo.previousNewMoon();
		Log.i(TAG, "Previous New Moon: " + StrFormat.dateFormat(nmCal));
		Calendar locCal = moonInfo.getObsLocal();
		double diffTime = (double)(locCal.getTimeInMillis() - 
				nmCal.getTimeInMillis());
		double hrsFromNm = diffTime / Astro.MILLISECONDS_PER_HOUR;
		Log.i(TAG, "Time from New Moon = " + hrsFromNm);
		if (hrsFromNm <= this.TIME_CUTOFF) {
			String hrsFromNmStr = StrFormat.formatDouble(hrsFromNm, 1) + " hours";
			this.appendText(R.id.time_from_new_moon, hrsFromNmStr);
			
			if (hrsFromNm > this.TIME_CRESCENT_WAXING && hrsFromNm <= this.TIME_CUTOFF) {
				this.toggleStar(R.id.om_in_nm_arms_iv);
			}
			if (hrsFromNm <= this.TIME_CRESCENT_WAXING) {
				this.toggleStar(R.id.cresent_waxing_iv);
			}
		}
		else {
			this.appendText(R.id.time_from_new_moon, 
					this.getString(R.string.lcsp_out_of_limit));
		}
		
		// Time to new Moon calculations
		nmCal = moonInfo.nextNewMoon();
		Log.i(TAG, "Next New Moon: " + StrFormat.dateFormat(nmCal));
		diffTime = (double)(nmCal.getTimeInMillis() - 
				locCal.getTimeInMillis());
		double hrsToNm = diffTime / Astro.MILLISECONDS_PER_HOUR;
		Log.i(TAG, "Time to New Moon = " + hrsToNm);
		if (hrsToNm <= this.TIME_CUTOFF) {
			String hrsToNmStr = StrFormat.formatDouble(hrsToNm, 1) + " hours";
			this.appendText(R.id.time_to_new_moon, hrsToNmStr);
			
			if (hrsToNm > this.TIME_CRESCENT_WANING && hrsToNm <= this.TIME_CUTOFF) {
				this.toggleStar(R.id.nm_in_om_arms_iv);
			}
			if (hrsToNm <= this.TIME_CRESCENT_WANING) {
				this.toggleStar(R.id.cresent_waning_iv);
			}
		}
		else {
			this.appendText(R.id.time_to_new_moon, 
					this.getString(R.string.lcsp_out_of_limit));
		}
		
		// Time to Full Moon calculation
		// Only needed for Cow Jumping over the Moon
		Calendar fmCal = moonInfo.nextFullMoon();
		Log.i(TAG, "Next Full Moon: " + StrFormat.dateFormat(fmCal));
		diffTime = (double)(fmCal.getTimeInMillis() - 
				locCal.getTimeInMillis());
		double daysToFm = diffTime / (Astro.MILLISECONDS_PER_HOUR * Astro.HOURS_PER_DAY);
		Log.i(TAG, "Time to Full Moon = " + daysToFm);
		
		if (daysToFm >= this.TIME_COW_JUMPING[0] && 
				daysToFm <= this.TIME_COW_JUMPING[1]) {
			this.toggleStar(R.id.cjotm_iv);			
		}
		
		// All other <Blank> in the Moon will use the illuminated fraction
		double illum = moonInfo.illumation();
		if (illum >= this.FULL_MOON_FRACTION) {
			this.toggleStar(R.id.mitm_iv);
			this.toggleStar(R.id.witm_iv);
			this.toggleStar(R.id.ritm_iv);
		}
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
	 * This function is responsible for replacing the default star with the 
	 * "on" star.
	 * @param layoutResId : The ImageView resource ID.
	 */
	private void toggleStar(int layoutResId) {
		ImageView iv = (ImageView)this.view.findViewById(layoutResId);
		iv.setImageResource(android.R.drawable.btn_star_big_on);
	}
}
