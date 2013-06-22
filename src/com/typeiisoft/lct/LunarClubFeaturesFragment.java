package com.typeiisoft.lct;

import java.util.ArrayList;

import com.typeiisoft.lct.db.DataBaseHelper;
import com.typeiisoft.lct.features.L2FeatureAdapter;
import com.typeiisoft.lct.features.LcFeatureAdapter;
import com.typeiisoft.lct.features.LunarFeature;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class LunarClubFeaturesFragment extends ListFragment {
	/** Logging identifier. */
	private static final String TAG = "LunarClubFeaturesFragment";
	/** View for the fragment. */
	private View view;
	/** Holder for the current feature type */
	private String currentType;
	
	/**
	 * Function that sets initial information into fragment.
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        /** Getting the arguments to the Bundle object */
        Bundle data = getArguments();
        
        this.currentType = data.getString("feature_type");
    }

	/**
	 * This function creates the Lunar Club features view.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		Log.i(TAG, "Creating " + this.currentType + " tab.");
		
		// Inflate the layout for this fragment
    	this.view = inflater.inflate(R.layout.lcfeatures, container, false);
    	
    	// Find the listview
    	ListView lv = (ListView) view.findViewById(android.R.id.list);
    	
        DataBaseHelper moonDB = new DataBaseHelper(this.getActivity());
        
        L2FeatureAdapter adapter = new L2FeatureAdapter(this.getActivity().getApplicationContext(), 
        		moonDB.getLunarClubFeatures(this.currentType));
    	this.setListAdapter(adapter);
    	
        // Setup the click listener to display more information
        OnItemClickListener listener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, 
					int position, long id) {
				String text = parent.getItemAtPosition(position).toString();
				Toast.makeText(LunarClubFeaturesFragment.this.getActivity().getApplicationContext(), text, 
						Toast.LENGTH_LONG).show();
			}
		};
		lv.setOnItemClickListener(listener);

		return this.view;
	}
}
