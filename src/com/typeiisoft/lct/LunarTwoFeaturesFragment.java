package com.typeiisoft.lct;

import com.typeiisoft.lct.db.DataBaseHelper;
import com.typeiisoft.lct.features.FeatureAdapter;
import com.typeiisoft.lct.features.LunarFeature;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

/**
 * This class handles creating the ListView for the Lunar II club features. 
 * 
 * @author Michael Reuter
 */
public class LunarTwoFeaturesFragment extends ListFragment {
	/** Logging identifier. */
	private final static String TAG = LunarTwoFeaturesFragment.class.getName();
	
	/**
	 * This function creates the view for the Lunar II Club main tab.
	 * @param inflater : The object that creates the view.
	 * @param container : The layout container for the view.
	 * @param savedInstanceState : Object containing any state information.
	 * @return : The view for the fragment.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		Log.i(TAG, "Creating tab.");

		// Inflate the layout for this fragment
    	View view = inflater.inflate(R.layout.features, container, false);

       	// Set the content
		DataBaseHelper moonDB = new DataBaseHelper(this.getActivity());
        FeatureAdapter adapter = new FeatureAdapter(this.getActivity().getApplicationContext(), 
        		(ArrayList<LunarFeature>) moonDB.getLunarTwoFeatures());
    	this.setListAdapter(adapter);
     	
    	return view;
	}

	/**
	 * This function shows detailed information on the feature when a list item 
	 * is clicked.
	 * @param l : The current ListView
	 * @param v : The current View
	 * @param position : The index of the item being clicked
	 * @param id : The item ID
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String text = l.getItemAtPosition(position).toString();
		Toast.makeText(LunarTwoFeaturesFragment.this.getActivity().getApplicationContext(), text, 
				Toast.LENGTH_LONG).show();
	}
}
