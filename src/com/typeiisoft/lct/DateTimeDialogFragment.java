package com.typeiisoft.lct;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

/**
 * This class handles the date/time setting dialog.
 * 
 * @author Michael Reuter
 */
public class DateTimeDialogFragment extends DialogFragment {
	/** Logging identifier */
	private static final String TAG = DateTimeDialogFragment.class.getName();

	/**
	 * This function creates the date/time setting dialog.
	 * @param saveInstanceState : Object containing saved state.
	 * @return : The created dialog.
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());

		LayoutInflater inflater = this.getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.datetime, null);
		
		// Set Time format
		TimePicker tp = (TimePicker) view.findViewById(R.id.timePicker1);
		tp.setIs24HourView(true);
		
		builder.setView(view);
		//builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle(R.string.datetime_title);
		builder.setPositiveButton("Set", null);
		builder.setNegativeButton("Cancel", null);
		builder.setNeutralButton("Now!", null);

		return builder.create();
	}
}
