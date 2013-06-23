package com.typeiisoft.lct;

import com.typeiisoft.lct.db.DataBaseHelper;
import com.typeiisoft.lct.features.FeatureAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class LunarClubFeaturesFragment extends ListFragment {
	/** Logging identifier. */
	private static final String TAG = "LunarClubFeaturesFragment";
	/** Holder for the current feature type */
	private String currentType;
	
	/**
	 * Instance constructor.
	 * @param currType : The requested feature category.
	 * @return : A new instance of the object with the requested category.
	 */
	static LunarClubFeaturesFragment newInstance(String currType) {
		LunarClubFeaturesFragment lcf = new LunarClubFeaturesFragment();
		Bundle data = new Bundle();
		data.putString("feature_type", currType);
		lcf.setArguments(data);
		return lcf;
	}
	
	/**
	 * Function that sets initial information into fragment.
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        /** Getting the arguments to the Bundle object */
        Bundle data = getArguments();
        
        this.currentType = data.getString("feature_type");
    	Log.i(TAG, "Running onCreate for " + this.currentType + " tab.");
    }

	/**
	 * This function creates the Lunar Club features view.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		Log.i(TAG, "Creating " + this.currentType + " tab.");
		
		// Inflate the layout for this fragment
    	View view = inflater.inflate(R.layout.features, container, false);
    	
    	// Set the content
    	DataBaseHelper moonDB = new DataBaseHelper(this.getActivity());
		FeatureAdapter adapter = new FeatureAdapter(this.getActivity().getApplicationContext(), 
				moonDB.getLunarClubFeatures(this.currentType));
		this.setListAdapter(adapter);
		
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		Log.i(TAG, "Running onViewCreated for " + this.currentType + " tab.");
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, "Running onActivityCreated for " + this.currentType + " tab.");
		/*
        DataBaseHelper moonDB = new DataBaseHelper(this.getActivity());
		FeatureAdapter adapter = new FeatureAdapter(this.getActivity().getApplicationContext(), 
				moonDB.getLunarClubFeatures(this.currentType));
		this.setListAdapter(adapter);
		*/
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.i(TAG, "Running onAttach for " + this.currentType + " tab.");
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
		Toast.makeText(LunarClubFeaturesFragment.this.getActivity().getApplicationContext(), text, 
				Toast.LENGTH_LONG).show();
	}
}
