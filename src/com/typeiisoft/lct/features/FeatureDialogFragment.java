package com.typeiisoft.lct.features;

import com.typeiisoft.lct.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * This class handles creating a dialog for the requested Lunar feature.
 * 
 * @author Michael Reuter
 */
public class FeatureDialogFragment extends DialogFragment {
	/** Holder for the view */
	private View view;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());

		LayoutInflater inflater = this.getActivity().getLayoutInflater();
		this.view = inflater.inflate(R.layout.featureinfo, null);
		builder.setView(this.view);
		builder.setPositiveButton("Dismiss", null);
		
		Bundle args = this.getArguments();
		this.appendText(R.id.feature_name, args.getString("name"));
		this.appendText(R.id.feature_type, args.getString("type"));
		this.appendText(R.id.feature_lat, args.getString("latitude"));
		this.appendText(R.id.feature_lon, args.getString("longitude"));
		this.appendText(R.id.feature_diameter, args.getString("diameter"));
		this.appendText(R.id.feature_quadname, args.getString("quad_name"));
		this.appendText(R.id.feature_quadcode, args.getString("quad_code"));
		
		return builder.create();
	}
	
	/**
	 * This function handles appending text to the labels that are already 
	 * displayed on the layout.
	 * @param layoutResId : The requested resource ID.
	 * @param more_text : The extra text to add the the labels.
	 */
	private void appendText(int layoutResId, String more_text) {
		TextView tv = (TextView) this.view.findViewById(layoutResId);
		String cur_text = tv.getText().toString();
		StringBuffer buff = new StringBuffer(cur_text).append(more_text);
		tv.setText(buff);
	}
}
