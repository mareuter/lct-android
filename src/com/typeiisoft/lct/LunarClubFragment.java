package com.typeiisoft.lct;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LunarClubFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.lc_tab, container, false);
        
        /** Getting a reference to the ViewPager defined the layout file */
        ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
 
        /** Getting fragment manager */
        FragmentManager fm = this.getActivity().getSupportFragmentManager();
 
        /** Instantiating FragmentPagerAdapter */
        LunarClubPagerAdapter pagerAdapter = new LunarClubPagerAdapter(fm);
 
        /** Setting the pagerAdapter to the pager object */
        pager.setAdapter(pagerAdapter);

        return view;
    }
}
