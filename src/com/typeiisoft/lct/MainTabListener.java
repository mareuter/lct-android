package com.typeiisoft.lct;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public final class MainTabListener<T extends Fragment> implements TabListener {
    private Fragment mFragment;
    private final FragmentActivity mActivity;
    private final String mTag;
    private final Class<T> mClass;
    
    /** Constructor used each time a new tab is created.
     * @param activity  The host Activity, used to instantiate the fragment
     * @param tag  The identifier tag for the fragment
     * @param clz  The fragment's Class, used to instantiate the fragment
     */
   public MainTabListener(FragmentActivity activity, String tag, Class<T> clz) {
       mActivity = activity;
       mTag = tag;
       mClass = clz;
   }

   /* The following are each of the ActionBar.TabListener callbacks */

   @Override
   public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
	   // User selected the already selected tab. Usually do nothing.
   }

   @Override
   public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
	   android.support.v4.app.FragmentTransaction fft = mActivity.getSupportFragmentManager().beginTransaction();
	   fft.commit();
	   // Check if the fragment is already initialized
	   if (mFragment == null) {
		   // If not, instantiate and add it to the activity
		   mFragment = Fragment.instantiate(mActivity, mClass.getName());
		   fft.add(android.R.id.content, mFragment, mTag);
	   } else {
		   // If it exists, simply attach it in order to show it
		   fft.attach(mFragment);
	   }
   }

   @Override
   public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
	   android.support.v4.app.FragmentTransaction fft = mActivity.getSupportFragmentManager().beginTransaction();
	   fft.commit();
	   if (mFragment != null) {
		   // Detach the fragment, because another one is being attached
		   fft.detach(mFragment);
	   }
   }
}
