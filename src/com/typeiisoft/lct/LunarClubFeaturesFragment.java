package com.typeiisoft.lct;

import com.typeiisoft.lct.db.DataBaseHelper;
import com.typeiisoft.lct.features.FeatureAdapter;
import com.typeiisoft.lct.features.L2FeatureAdapter;
import com.typeiisoft.lct.features.LcFeatureAdapter;

import android.app.Activity;
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
	/** Holder for the current feature type */
	private String currentType;
	
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
    	return inflater.inflate(R.layout.lcfeatures, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		Log.i(TAG, "Running onViewCreated for " + this.currentType + " tab.");
		super.onViewCreated(view, savedInstanceState);
        DataBaseHelper moonDB = new DataBaseHelper(this.getActivity());
		FeatureAdapter adapter = new FeatureAdapter(this.getActivity().getApplicationContext(), 
				moonDB.getLunarClubFeatures(this.currentType));
		this.setListAdapter(adapter);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, "Running onActivityCreated for " + this.currentType + " tab.");
        DataBaseHelper moonDB = new DataBaseHelper(this.getActivity());
		FeatureAdapter adapter = new FeatureAdapter(this.getActivity().getApplicationContext(), 
				moonDB.getLunarClubFeatures(this.currentType));
		this.setListAdapter(adapter);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.i(TAG, "Running onAttach for " + this.currentType + " tab.");
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String text = l.getItemAtPosition(position).toString();
		Toast.makeText(LunarClubFeaturesFragment.this.getActivity().getApplicationContext(), text, 
				Toast.LENGTH_LONG).show();
	}
	
	
	
}
