package com.typeiisoft.lct;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class LunarClubPagerAdapter extends FragmentPagerAdapter {
	/** Number of tabs for the display. */
	private final static int NUM_TABS = 3;
	
	/**
	 * Class constructor.
	 * @param fm : handle for FragmentManager
	 */
	public LunarClubPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	/**
	 * Function to retrieve a fragment at a given index.
	 * @param arg0 : index for fragment
	 * @return the fragment at the given index
	 */
	@Override
	public Fragment getItem(int arg0) {
		return null;
	}

	/**
	 * Function to get the number of total number of tabs.
	 * @return the number of tabs in the pager
	 */
	@Override
	public int getCount() {
		return NUM_TABS;
	}

}
