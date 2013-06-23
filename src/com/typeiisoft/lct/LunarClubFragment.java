package com.typeiisoft.lct;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LunarClubFragment extends Fragment {
	private final static String TAG = "LunarClubFragment";
	ViewPager pager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	Log.i(TAG, "Running onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.lc_tab, container, false);
        
        /** Getting a reference to the ViewPager defined the layout file */
        this.pager = (ViewPager) view.findViewById(R.id.pager);
        this.pager.setSaveEnabled(false);
 
        /** Getting fragment manager */
        FragmentManager fm = this.getActivity().getSupportFragmentManager();
 
        /** Instantiating FragmentPagerAdapter */
        LunarClubPagerAdapter pagerAdapter = new LunarClubPagerAdapter(fm);
 
        /** Setting the pagerAdapter to the pager object */
        this.pager.setAdapter(pagerAdapter);

        return view;
    }
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
    	Log.i(TAG, "Running onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}
    
    
}
