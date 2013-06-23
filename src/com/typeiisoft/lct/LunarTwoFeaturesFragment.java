package com.typeiisoft.lct;

import java.util.ArrayList;

import com.typeiisoft.lct.db.DataBaseHelper;
import com.typeiisoft.lct.features.FeatureAdapter;
import com.typeiisoft.lct.features.LunarFeature;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

/**
 * This class handles creating the ListView for the LunarII club features. 
 * The item view is just the name of the feature. More information can be 
 * had by performing a long click on an item.
 * 
 * @author Michael Reuter
 *
 */
public class LunarTwoFeaturesFragment extends ListFragment {
	/** Logging identifier. */
	private final static String TAG = "LunarTwoFeaturesFragment";
	
	/**
	 * This function does the actual view creation. It also sets up a long 
	 * click listener to show more information about the feature.
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
	 * Function to show detailed information on the feature when a list item 
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
