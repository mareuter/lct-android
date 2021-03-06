package com.typeiisoft.lct.features;

import com.typeiisoft.lct.R;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * This class handles creating the item views for any of the Lunar feature 
 * lists.
 * 
 * @author Michael Reuter
 */
public class FeatureAdapter extends ArrayAdapter<LunarFeature> {
	/** Logging identifier. */
	private static final String TAG = FeatureAdapter.class.getName();
	/** Associated activity context. */
	private final Context context;
	/** Holder for the list of features. */
	private final ArrayList<LunarFeature> features;

	/**
	 * This function is the class constructor.
	 * @param context : The current activity context.
	 * @param values : The list of currently visible features.
	 */
	public FeatureAdapter(Context context, ArrayList<LunarFeature> values) {
		super(context, R.layout.featureitem, values);
		this.context = context;
		this.features = values;
	}
	
	/**
	 * This function creates the item view for the list view.
	 * @param position : The location within the list view.
	 * @param convertView : Other view to convert to?
	 * @param parent : The containing list view.
	 * @return : The populated item view.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d(TAG, "RowView for " + this.features.get(position).getName() + " starting.");

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.featureitem, parent, false);
		
		TextView featureNameView = (TextView) rowView.findViewById(R.id.feature_name);
		featureNameView.setText(this.features.get(position).getName());

		Log.d(TAG, "RowView for " + this.features.get(position).getName() + " done.");
		return rowView;
	}
}
