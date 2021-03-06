package com.typeiisoft.lct;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.text.method.LinkMovementMethod;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * This class handles the program's About dialog.
 * 
 * @author Michael Reuter
 */
public class AboutDialogFragment extends DialogFragment {
	/** Logging identifier */
	private static final String TAG = AboutDialogFragment.class.getName();
	/**
	 * This function is first called when the activity is started.
	 * @param saveInstanceState : Object containing any state information.
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
		
		LayoutInflater inflater = this.getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.about, null);
		builder.setView(view);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle(R.string.about_title);
		builder.setPositiveButton("OK", null);
		
		TextView tv = (TextView) view.findViewById(R.id.about_program_tv);
		String cur_text = tv.getText().toString();
		try {
			PackageManager pm = this.getActivity().getApplicationContext().getPackageManager();
			PackageInfo pi = pm.getPackageInfo(this.getActivity().getApplicationContext().getPackageName(), 0);
			String versionName = pi.versionName;
			tv.setText(String.format(cur_text, versionName));		
		} 
		catch (PackageManager.NameNotFoundException nnfe) {
			Log.e(TAG, "Package name not found!");
		}

		TextView abountContent = (TextView) view.findViewById(R.id.about_content);
		abountContent.setMovementMethod(LinkMovementMethod.getInstance());
		
		return builder.create();
	}
}
