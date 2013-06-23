package com.typeiisoft.lct;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class LunarClubPagerAdapter extends FragmentStatePagerAdapter {
	private final static String TAG = "LunarClubPagerAdapter";
	/** Number of tabs for the display. */
	private final static int NUM_TABS = 3;
	/** The set of titles for the Lunar Club tabs */
	private String[] tabTitles = {"Naked Eye", "Binocular", "Telescope"};
	
	/**
	 * Class constructor.
	 * @param fm : handle for FragmentManager
	 */
	public LunarClubPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	/**
	 * Function to retrieve a fragment at a given index.
	 * @param position : index for fragment
	 * @return the fragment at the given index
	 */
	@Override
	public Fragment getItem(int position) {
		Log.i(TAG, "Creating tab at position " + String.valueOf(position));
		return LunarClubFeaturesFragment.newInstance(this.tabTitles[position]);
	}

	/**
	 * Function to get the number of total number of tabs.
	 * @return the number of tabs in the pager
	 */
	@Override
	public int getCount() {
		return NUM_TABS;
	}

	/**
	 * Function to put a title on the current page.
	 * @param position : The index for the title.
	 * @return The title for the page.
	 */
    @Override
    public CharSequence getPageTitle(int position) {
        return this.tabTitles[position];
    }
}
