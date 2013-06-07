package com.typeiisoft.lct.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.typeiisoft.lct.features.FeatureComparator;
import com.typeiisoft.lct.features.LunarFeature;
import com.typeiisoft.lct.utils.AppPreferences;
import com.typeiisoft.lct.utils.MoonInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class handles the interaction 
 * @author Michael Reuter
 *
 */
public class DataBaseHelper extends SQLiteOpenHelper {
	private static final String TAG = "DataBaseHelper";
	//The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.typeiisoft.lct/databases/";
    private static String DB_NAME = "moon.db";
    private static String DB_TABLE = "Features";
    private SQLiteDatabase myDataBase; 
    private final Context myContext;
    /** Enum that holds the database fields for integer comparison. */
    private enum DbFields {
    	_id, NAME, DIAMETER, LATITUDE, LONGITUDE, DELTA_LAT, DELTA_LONG, TYPE, 
    	QUAD_NAME, QUAD_CODE, LUNAR_CODE, LUNAR_CLUB_TYPE;
    }
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to 
     * the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {
 
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	

    /**
     * Creates a empty database on the system and rewrites it with your own 
     * database.
     * */
    public void createDataBase() throws IOException{
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
    			copyDataBase();
    		} catch (IOException e) {
        		throw new Error("Error copying database");
        	}
        	Log.i(TAG, "Database copied successfully.");
    	}
    }

    /**
     * Check if the database already exist to avoid re-copying the file each 
     * time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
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
     * Copies your database from your local assets-folder to the just created 
     * empty database in the system folder, from where it can be accessed and 
     * handled. This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
    	// Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	// Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	// Transfer bytes from the inputfile to the outputfile
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
 
    public void openDataBase() throws SQLException {
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, 
    			SQLiteDatabase.OPEN_READONLY);
    }
 
    @Override
	public synchronized void close() {
    	    if(myDataBase != null)
    		    myDataBase.close();
    	    super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

    // Add your public helper methods to access and get content from the 
	// database. You could return cursors by doing 
	// "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

	/**
	 * This function gets the Lunar Club feature list as a set of three 
	 * lists based on the target type.
	 * @return : The list of lists of Lunar Club features.
	 */
	public ArrayList<ArrayList<LunarFeature>> getLunarClubFeatures() {
		ArrayList<LunarFeature> tmp = (ArrayList<LunarFeature>)this.getFeatureList("Lunar");
		
		// Setup three lists for the target categories.
		ArrayList<LunarFeature> nakedEye = new ArrayList<LunarFeature>();
		ArrayList<LunarFeature> binocular = new ArrayList<LunarFeature>();
		ArrayList<LunarFeature> telescopic = new ArrayList<LunarFeature>();
		String targetType;
		for (LunarFeature l : tmp) {
			targetType = l.getClubType();
			Log.i(TAG, "Target type = " + targetType);
			if (targetType.equals(new String("Naked Eye"))) {
				nakedEye.add(l);
			}
			if (targetType.equals(new String("Binocular"))) {
				binocular.add(l);
			}
			if (targetType.equals(new String("Telescopic"))) {
				telescopic.add(l);
			}
		}
		
		Log.i(TAG, "Naked Eye = " + nakedEye.size());
		Log.i(TAG, "Binocular = " + binocular.size());
		Log.i(TAG, "Telescopic = " + telescopic.size());
		
		Collections.sort(nakedEye, new FeatureComparator());
		Collections.sort(binocular, new FeatureComparator());
		Collections.sort(telescopic, new FeatureComparator());
		
		ArrayList<ArrayList<LunarFeature>> features = new ArrayList<ArrayList<LunarFeature>>();
		features.add(nakedEye);
		features.add(binocular);
		features.add(telescopic);
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
		AppPreferences appPrefs = new AppPreferences(this.myContext);
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
	 * @return The LunarFeature corresponding to the information in the row.
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
