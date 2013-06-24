package com.typeiisoft.lct;

import android.app.Activity;
import android.os.Bundle;

/**
 * This class handles the program's About dialog.
 * 
 * @author Michael Reuter
 */
public class About extends Activity {
	/**
	 * This function is first called when the activity is started.
	 * @param saveInstanceState : Object containing any state information.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.about);
	}
}
