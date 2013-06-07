package com.typeiisoft.lct;

import java.util.ArrayList;

import com.typeiisoft.lct.db.DataBaseHelper;
import com.typeiisoft.lct.features.LcFeatureAdapter;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;

public class LunarClubFeaturesActivity extends ExpandableListActivity {
	/** Logging identifier. */
	private static final String TAG = "LunarClubFeaturesActivity";
	
	/**
	 * This function creates the Lunar Club features view.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.lcfeatures);
        DataBaseHelper moonDB = new DataBaseHelper(this);
        
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
				Toast.makeText(LunarClubFeaturesActivity.this, text, 
						Toast.LENGTH_LONG).show();
				return true;
			}
		};
		this.getExpandableListView().setOnChildClickListener(clickListener);
	}
}
