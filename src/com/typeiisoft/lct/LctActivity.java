package com.typeiisoft.lct;

import java.io.IOException;
import java.util.Calendar;

import com.mhuss.AstroLib.Astro;
import com.typeiisoft.lct.db.DataBaseHelper;
import com.typeiisoft.lct.utils.AppPreferences;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;

public class LctActivity extends TabActivity {
	/** The application preferences object. */
	private AppPreferences appPrefs;
	private static final String TAG = "LctActivity";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Reusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Set the observation date and time into the shared preferences.
    	this.appPrefs = new AppPreferences(this.getApplicationContext());
    	Calendar now = Calendar.getInstance();
    	int offset = now.getTimeZone().getOffset(now.getTimeInMillis()) / Astro.MILLISECONDS_PER_HOUR;
		// Set time to UTC, offset will allow calc of local time later.
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
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, MoonInfoActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("mooninfo").setIndicator("Moon Info",
                          new StateListDrawable())
                      .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, LunarClubFeaturesActivity.class);
        spec = tabHost.newTabSpec("lunar_club_features").setIndicator("LC Features",
                          new StateListDrawable())
                      .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, LunarTwoFeaturesActivity.class);
        spec = tabHost.newTabSpec("lunar_two_features").setIndicator("LII Features",
                          new StateListDrawable())
                      .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
        //moonDB.close();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.about:
    		startActivity(new Intent(this, About.class));
    		return true;
    	}
    	return false;
    }
}