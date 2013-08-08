package com.typeiisoft.lct;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * This class handles the naked eye observations for the Lunar Club that 
 * are not directly related to features on the Moon.
 * 
 * @author Michael Reuter
 */
public class LunarClubSpecialFragment extends Fragment {
	/** Logging identifier. */
	private static final String TAG = LunarClubSpecialFragment.class.getName();

	/**
	 * This function is the instance constructor.
	 * @return : A new instance of the object.
	 */
	static LunarClubSpecialFragment newInstance() {
		return new LunarClubSpecialFragment();
	}

	/**
	 * This function creates the Lunar Club features view.
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
    	return inflater.inflate(R.layout.lc_special, container, false);
	}
}
