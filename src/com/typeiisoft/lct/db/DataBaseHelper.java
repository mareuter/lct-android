package com.typeiisoft.lct.db;

import com.typeiisoft.lct.features.FeatureComparator;
import com.typeiisoft.lct.features.LunarFeature;
import com.typeiisoft.lct.utils.AppPreferences;
import com.typeiisoft.lct.utils.MoonInfo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class handles the interaction for the Moon information database.
 * 
 * @author Michael Reuter
 */
public class DataBaseHelper extends SQLiteOpenHelper {
	/** Logging identifier. */
	private static final String TAG = DataBaseHelper.class.getName();
	/** The Android's default system path of your application database. */
    private static String DB_PATH = "/data/data/com.typeiisoft.lct/databases/";
    /** Name of the Lunar feature database. */
    private static String DB_NAME = "moon.db";
    /** Main table name in the Lunar feature database. */
    private static String DB_TABLE = "Features";
    /** Holder for the SQL database. */
    private SQLiteDatabase myDataBase; 
    /** Holder for an activity. */
    private final Activity myActivity;
    /** Enum that holds the database fields for integer comparison. */
    private enum DbFields {
    	_id, NAME, DIAMETER, LATITUDE, LONGITUDE, DELTA_LAT, DELTA_LONG, TYPE, 
    	QUAD_NAME, QUAD_CODE, LUNAR_CODE, LUNAR_CLUB_TYPE;
    }
 
    /**
     * This function is the class constructor. It takes and keeps a reference 
     * of the passed context in order to access to the application assets 
     * and resources.
     * @param activity : The top-level activity object.
     */
    public DataBaseHelper(Activity activity) {
    	super(activity, DB_NAME, null, 1);
        this.myActivity = activity;
    }	

    /**
     * This function creates an empty database on the system and rewrites 
     * it with your own database.
     * @throws IOException
     */
    public void createDataBase() throws IOException {
    	boolean dbExist = checkDataBase();
    	if (dbExist) {
    		// Do nothing - database already exist
    		Log.i(TAG, "Database already exists.");
    	} else {
    		// By calling this method and empty database will be created into 
    		// the default system path of your application so we are going to 
    		// be able to overwrite that database with our database.
        	this.getReadableDatabase();
        	try {
    			this.copyDataBase();
    		} catch (IOException e) {
        		throw new Error("Error copying database");
        	}
        	Log.i(TAG, "Database copied successfully.");
    	}
    }

    /**
     * This function checks if the database already exists to avoid re-copying 
     * the file each time you open the application.
     * @return : True if the database exists, false if it doesn't
     */
    private boolean checkDataBase() {
    	SQLiteDatabase checkDB = null;
    	try {
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, 
    				SQLiteDatabase.OPEN_READONLY);
    	} catch(SQLiteException e) {
    		// database does't exist yet.
    	}
 
    	if (checkDB != null) {
    		checkDB.close();
    	}
 
    	return checkDB != null ? true : false;
    }

    /**
     * This function copies your database from your local assets-folder to the 
     * just created empty database in the system folder, from where it can be 
     * accessed and handled. This is done by transferring a byte stream.
     * @throws IOException
     */
    private void copyDataBase() throws IOException {
    	// Open your local db as the input stream
    	InputStream myInput = myActivity.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	// Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	// Transfer bytes from the input file to the output file
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0) {
    		myOutput.write(buffer, 0, length);
    	}
 
    	// Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }
 
    /**
     * This function opens a handle to the database.
     * @throws SQLException
     */
    public void openDataBase() throws SQLException {
    	// Open the database
        String myPath = DB_PATH + DB_NAME;
    	this.myDataBase = SQLiteDatabase.openDatabase(myPath, null, 
    			SQLiteDatabase.OPEN_READONLY);
    }
 
    /**
     * This function closes the handle to the database.
     */
    @Override
	public synchronized void close() {
    	    if(this.myDataBase != null)
    		    this.myDataBase.close();
    	    super.close();
	}

    /**
     * This function is for creating a database. Since the program uses an existing 
     * one, this does nothing.
     */
	@Override
	public void onCreate(SQLiteDatabase arg0) {
	}

	/**
	 * This function is for updating a database. Since the program uses an existing 
	 * one that is independent of the program, this does nothing.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

    // Add your public helper methods to access and get content from the 
	// database. You could return cursors by doing 
	// "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

	/**
	 * This function gets the Lunar Club feature list based on the target type.
	 * @param : targetType : The requested type for the Lunar Club features.
	 * @return : The lists of Lunar Club features.
	 */
	public ArrayList<LunarFeature> getLunarClubFeatures(String targetType) {
		List<LunarFeature> tmp = this.getFeatureList("Lunar");
		
		// Setup list for the target category.
		ArrayList<LunarFeature> features = new ArrayList<LunarFeature>();

		String lTargetType;
		for (LunarFeature l : tmp) {
			lTargetType = l.getClubType();
			Log.d(TAG, "Target type = " + lTargetType);
			if (lTargetType.equals(targetType)) {
				features.add(l);
			}
		}
		
		Log.i(TAG, targetType + " = " + features.size());
		Collections.sort(features, new FeatureComparator());
		return features;
	}
	
	/**
	 * This function gets the Lunar II Club feature list.
	 * @return : The list of Lunar II Club features.
	 */
	public List<LunarFeature> getLunarTwoFeatures() {
		List<LunarFeature> tmp = this.getFeatureList("LunarII");
		Collections.sort(tmp, new FeatureComparator());
		return tmp;
	}
	
	/**
	 * This function handles querying the database, creating the list of 
	 * lunar features and returning that list.
	 * @param clubName : The observing club to get the feature list for.
	 * @return : The given feature list.
	 */
	private List<LunarFeature> getFeatureList(String clubName) {
		AppPreferences appPrefs = new AppPreferences(this.myActivity);
		MoonInfo moonInfo = new MoonInfo(appPrefs.getDateTime());
		Log.i(TAG, "MoonInfo: " + moonInfo.toString());
		
		List<LunarFeature> features = new ArrayList<LunarFeature>();

		if (this.checkDataBase()) {
			this.openDataBase();
			Cursor cursor = this.myDataBase.query(DB_TABLE, null, 
					"Lunar_Code=? or Lunar_Code=?", 
					new String[]{clubName, "Both"}, 
					null, null, "Latitude DESC");

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				LunarFeature feature = this.cursorToLunarFeature(cursor);
				if (moonInfo.isVisible(feature)) {
					Log.d(TAG, feature.toString());
					features.add(feature);
				}
				cursor.moveToNext();
			}
			cursor.close();
			this.myDataBase.close();
		}
		else {
			Log.e(TAG, "Database has not been initialized!");
		}

		Log.i(TAG, "Number of " + clubName + " Club features = " + features.size());
		return features;
	}
	
	/**
	 * This function takes the current DB row and creates a LunarFeature 
	 * object from the information.
	 * @param cur : The current DB row.
	 * @return : The LunarFeature corresponding to the information in the row.
	 */
	private LunarFeature cursorToLunarFeature(Cursor cur) {
		return new LunarFeature(cur.getString(DbFields.NAME.ordinal()), 
				cur.getDouble(DbFields.DIAMETER.ordinal()),
				cur.getDouble(DbFields.LATITUDE.ordinal()), 
				cur.getDouble(DbFields.LONGITUDE.ordinal()), 
				cur.getString(DbFields.TYPE.ordinal()), 
				cur.getDouble(DbFields.DELTA_LAT.ordinal()), 
				cur.getDouble(DbFields.DELTA_LONG.ordinal()),
				cur.getString(DbFields.QUAD_NAME.ordinal()),
				cur.getString(DbFields.QUAD_CODE.ordinal()),
				cur.getString(DbFields.LUNAR_CODE.ordinal()),
				cur.getString(DbFields.LUNAR_CLUB_TYPE.ordinal()));
	}
}
