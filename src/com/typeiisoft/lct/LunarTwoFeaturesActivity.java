package com.typeiisoft.lct;

import java.util.ArrayList;

import com.typeiisoft.lct.db.DataBaseHelper;
import com.typeiisoft.lct.features.L2FeatureAdapter;
import com.typeiisoft.lct.features.LunarFeature;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

/**
 * This class handles creating the ListView for the LunarII club features. 
 * The item view is just the name of the feature. More information can be 
 * had by performing a long click on an item.
 * 
 * @author Michael Reuter
 *
 */
public class LunarTwoFeaturesActivity extends ListActivity {
	/** Logging identifier. */
	private final static String TAG = "LunarTwoFeaturesActivity";
	
	/**
	 * This function does the actual view creation. It also sets up a long 
	 * click listener to show more information about the feature.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "Creating tab.");
		super.onCreate(savedInstanceState);
        setContentView(R.layout.l2features);
        DataBaseHelper moonDB = new DataBaseHelper(this);
        L2FeatureAdapter adapter = new L2FeatureAdapter(this, 
        		(ArrayList<LunarFeature>) moonDB.getLunarTwoFeatures());
        setListAdapter(adapter);
        
        // Setup the click listener to display more information
        OnItemClickListener listener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, 
					int position, long id) {
				String text = parent.getItemAtPosition(position).toString();
				Toast.makeText(LunarTwoFeaturesActivity.this, text, 
						Toast.LENGTH_LONG).show();
			}
		};
		this.getListView().setOnItemClickListener(listener);
	}
}
