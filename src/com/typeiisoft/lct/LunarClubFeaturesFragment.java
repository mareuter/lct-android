package com.typeiisoft.lct;

import java.util.ArrayList;

import com.typeiisoft.lct.db.DataBaseHelper;
import com.typeiisoft.lct.features.LcFeatureAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;

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

		// Inflate the layout for this fragment
    	this.view = inflater.inflate(R.layout.lcfeatures, container, false);
        DataBaseHelper moonDB = new DataBaseHelper(this.getActivity());
        
        /*
        ArrayList<String> categories = new ArrayList<String>();
        categories.add(new String("Naked Eye"));
        categories.add(new String("Binocular"));
        categories.add(new String("Telescopic"));
        
        LcFeatureAdapter adapter = new LcFeatureAdapter(this, categories,
        		moonDB.getLunarClubFeatures());
        setListAdapter(adapter);
        
        // Create a click listener to display feature information.
        OnChildClickListener clickListener = new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Object obj = parent.getExpandableListAdapter().getChild(groupPosition, childPosition);
				if (null == obj) {
					Log.e(TAG, "Can't get object");
				}
				String text = obj.toString();
				Toast.makeText(LunarClubFeaturesFragment.this, text, 
						Toast.LENGTH_LONG).show();
				return true;
			}
		};
		this.getExpandableListView().setOnChildClickListener(clickListener);
		*/
		return this.view;
	}
}
