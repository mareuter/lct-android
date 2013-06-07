package com.typeiisoft.lct.features;

import com.typeiisoft.lct.utils.StrFormat;

/**
 * This class is responsible for containing the relevant information for 
 * a given lunar feature.
 * 
 * @author Michael Reuter
 *
 */
public class LunarFeature {
	/** Clean name of the lunar feature (no dicritical marks). */
	private String name;
	/** Diameter or longest axis of lunar feature. */
	private double diameter;
	/** Latitude of the lunar feature in decimal degrees. North is positive. */
	private double latitude;
	/** Longitude of the lunar feature in decimal degrees. East is positive. */
	private double longitude;
	/** Short description of feature type. */
	private String featureType;
	/** Width of the lunar feature in latitude in decimal degrees. */
	private double deltaLatitude;
	/** Width of the lunar feature in longitude in decimal degrees. */
	private double deltaLongitude;
	/** Name of lunar quadrant containing feature's center point as determined 
	 * by the International Astronomical Union (IAU) Working Group for 
	 * Planetary System Nomenclature (WGPSN) */
	private String quadName;
	/** Specific lunar quadrant containing feature's center point as determined 
	 * by the IAU WGPSN. 
	 */
	private String quadCode;
	/** Observing club lunar feature belongs to. */	
	private String codeName;
	/** Target type name for Lunar Club */
	private String clubType;
	
	/**
	 * Parametered class constructor.
	 * @param name : clean name of feature
	 * @param latitude : latitude of feature
	 * @param longitude : longitude of feature
	 * @param featureType : description of feature
	 * @param deltaLatitude : latitude width of feature
	 * @param deltaLongitude : longitude width of feature
	 * @param quadName : lunar quadrant name
	 * @param quadCode : lunar quadrant code
	 * @param codeName : Club list for feature
	 * @param clubType : Lunar Club target type
	 */
	public LunarFeature(String name, double diameter, double latitude, double longitude,
			String featureType, double deltaLatitude, double deltaLongitude,
			String quadName, String quadCode, String codeName, 
			String clubType) {
		this.name = name;
		this.diameter = diameter;
		this.latitude = latitude;
		this.longitude = longitude;
		this.featureType = featureType;
		this.deltaLatitude = deltaLatitude;
		this.deltaLongitude = deltaLongitude;
		this.quadName = quadName;
		this.quadCode = quadCode;
		this.codeName = codeName;
		this.clubType = clubType;
	}
	
	/**
	 * Getter for feature name
	 * @return : the clean lunar feature name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Getter for feature diameter
	 * @return : the feature diameter
	 */
	public double getDiameter() {
		return this.diameter;
	}
	/**
	 * Getter for feature latitude
	 * @return : the feature latitude
	 */
	public double getLatitude() {
		return this.latitude;
	}
	
	/**
	 * Getter for feature longitude
	 * @return : the feature longitude
	 */
	public double getLongitude() {
		return this.longitude;
	}
	
	/**
	 * Getter for the feature type name
	 * @return : the feature type name
	 */
	public String getFeatureType() {
		return this.featureType;
	}
	
	/**
	 * Getter for the feature latitude width
	 * @return : the feature latitude width
	 */
	public double getDeltaLatitude() {
		return this.deltaLatitude;
	}
	
	/**
	 * Getter for the feature longitude width
	 * @return : the feature longitude width
	 */
	public double getDeltaLongitude() {
		return this.deltaLongitude;
	}
	
	/**
	 * Getter for the lunar feature quadrant name
	 * @return : the feature's quadrant name
	 */
	public String getQuadName() {
		return this.quadName;
	}
	
	/**
	 * Getter for the lunar feautre quadrant code 
	 * @return : the feature's quadrant code
	 */
	public String getQuadCode() {
		return this.quadCode;
	}
	
	/**
	 * Getter for the observing club list name. Can be Lunar, LunarII or Both
	 * @return : the observing cub list name
	 */
	public String getCodeName() {
		return this.codeName;
	}

	/**
	 * Getter for the Lunar Club target type name. Can be None.
	 * @return : the Lunar Club target type name
	 */
	public String getClubType() {
		return this.clubType;
	}
	
	/**
	 * This function creates a string representation of the feature object.
	 * @return : The feature's string representation.
	 */
	public String toString() {
		String lsp = System.getProperty("line.separator");
		StringBuilder stb = new StringBuilder();
		stb.append("Name: ").append(this.name).append(lsp)
		.append("Type: ").append(this.featureType).append(lsp)
		.append("Latitude: ").append(StrFormat.coordFormat("lat", 
				this.latitude)).append(lsp)
		.append("Longitude: ").append(StrFormat.coordFormat("lon", 
				this.longitude)).append(lsp)
		.append("Diameter: ").append(StrFormat.formatDouble(this.diameter, 2))
		.append(" km").append(lsp)
		.append("Quad Name: ").append(this.quadName).append(lsp)
		.append("Quad Code: ").append(this.quadCode).append(lsp);
		return stb.toString();
	}
}
