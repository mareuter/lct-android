package com.typeiisoft.lct.features;

import java.util.Comparator;

/**
 * This class is responsible for the sorting criteria for all feature lists.
 * 
 * @author Michael Reuter
 */
public class FeatureComparator implements Comparator<LunarFeature> {
	/**
	 * This function provides comparison capability between two LunarFeature 
	 * objects. It first checks the names to see if the two features are the 
	 * same. Next, it checks the latitude of the feature. Objects are sorted 
	 * by highest northern latitude (most positive) to highest southern 
	 * latitude (most negative). 
	 * @param arg0 : The first feature.
	 * @param arg1 : The second feature.
	 * @return : 0 if equal, -1 if greater, 1 if less
	 */
	@Override
	public int compare(LunarFeature arg0, LunarFeature arg1) {
		int value = arg0.getFeatureType().compareTo(arg1.getFeatureType());
		if (0 != value) {
			return value;
		}
		else {
			return -1 * Double.compare(arg0.getLatitude(), arg1.getLatitude());
		}
	}
}
