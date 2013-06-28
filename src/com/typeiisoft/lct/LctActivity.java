package com.typeiisoft.lct;

import com.typeiisoft.lct.db.DataBaseHelper;
import com.typeiisoft.lct.utils.AppPreferences;

import com.mhuss.AstroLib.Astro;

import java.io.IOException;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * This is the main activity class for the program. It handles creation of 
 * the tabs and setup of the database. 
 * 
 * @author Michael Reuter
 */
public class LctActivity extends FragmentActivity {
	/** Logging identifier. */
	private static final String TAG = LctActivity.class.getName();
	/** The application preferences object. */
	private AppPreferences appPrefs;
	
    /** 
     * This function is called when the activity is first created. 
     * @param savedInstanceState : Object containing state for the program.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.i(TAG, "Running onCreate");
        super.onCreate(savedInstanceState);
        
        // Set up the ActionBar
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set the observation date and time into the shared preferences.
    	this.appPrefs = new AppPreferences(this);
    	Calendar now = Calendar.getInstance();
    	int offset = now.getTimeZone().getOffset(now.getTimeInMillis()) / Astro.MILLISECONDS_PER_HOUR;
		// Set time to UTC, offset will allow calculation of local time later.
		now.add(Calendar.HOUR_OF_DAY, -offset);
		
		this.appPrefs.setDateTime(now.get(Calendar.DATE), 
				now.get(Calendar.MONTH)+1, now.get(Calendar.YEAR), 
				now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), 
				now.get(Calendar.SECOND), offset);

        // Copy the Moon information database
        DataBaseHelper moonDB = new DataBaseHelper(this);
        try {
        	moonDB.createDataBase();
        }
        catch (IOException ioe) {
        	throw new Error("Unable to open Moon info database.");
        }
        /**
        // Open the handle to the Moon information
        try {
        	moonDB.openDataBase();
        }
        catch (SQLException sqle) {
        	throw sqle;
        }
        // DB should now be open and ready
        */
        // Create a tab to set a Fragment (to be reused)
        // Setup the Moon information tab
        Tab tab = actionBar.newTab()
        		.setText(R.string.moon_info_tab)
        		.setTabListener(new MainTabListener<MoonInfoFragment>(
        				this, "mooninfo", MoonInfoFragment.class));
        actionBar.addTab(tab);
        		
        // Setup the Lunar Club tab
        tab = actionBar.newTab()
        		.setText(R.string.lunar_club_tab)
        		.setTabListener(new MainTabListener<LunarClubFragment>(
        				this, "lunarclub", LunarClubFragment.class));
        actionBar.addTab(tab);
        
        // Setup the Lunar II Club tab
        tab = actionBar.newTab()
        		.setText(R.string.lunar2_club_tab)
        		.setTabListener(new MainTabListener<LunarTwoFeaturesFragment>(
        				this, "lunar2club", LunarTwoFeaturesFragment.class));
        actionBar.addTab(tab);
        
        //moonDB.close();
        if (null != savedInstanceState) {
        	actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
    }
    
    /**
     * This function saves the navigation state to the current ActionBar tab.
     * @param outState : The saved instance object.
     */
    @Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", this.getActionBar().getSelectedNavigationIndex());
	}

	/**
     * This function created the options menu for the program.
     * @param menu : The object to attach a layout to.
     * @return : Whether or not the menu was created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
    /**
     * This function is responsible for handling the actions when a menu 
     * item is clicked.
     * @param item : The item that has been clicked.
     * @return : True if the action was successfully handled.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.about:
    		DialogFragment newFragment = new AboutDialogFragment();
    		newFragment.show(this.getSupportFragmentManager(), "about");
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    	
    }
}