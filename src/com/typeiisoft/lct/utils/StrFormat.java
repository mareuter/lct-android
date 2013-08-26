package com.typeiisoft.lct.utils;

import com.mhuss.AstroLib.Astro;
import com.mhuss.AstroLib.TimeOps;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * This class is designed to provide various string formatting methods. It 
 * should only be used via the static methods as instantiation is not allowed.
 * 
 * @author Michael Reuter
 */
public final class StrFormat {
	/** Constant for the unicode degree symbol. */
	public static final String DEGREE_SYMBOL = "\u00b0";

	/**
	 * This function formats a latitude or longitude value into one with 
	 * a direction label and two numbers after the decimal.
	 * @param coordType : The coordinate type: lat/lon.
	 * @param coord : The value of the latitude or longitude.
	 * @return : A formatted string.
	 */
	@SuppressLint("DefaultLocale")
	public static String coordFormat(String coordType, double coord) {
		String dir = "";
		if ("lat" == coordType.toLowerCase()) {
			if (coord < 0.0) {
				dir = "S";
			}
			else {
				dir = "N";
			}
		}
		if ("lon" == coordType.toLowerCase()) {
			if (coord < 0.0) {
				dir = "W";
			}
			else {
				dir = "E";
			}
		}
		String dbl_fmt = StrFormat.formatDouble(Math.abs(coord), 2);
		StringBuffer buf = new StringBuffer(dbl_fmt);
		// This is the degree symbol in unicode.
		buf.append(StrFormat.DEGREE_SYMBOL + " ");
		buf.append(dir);
		return buf.toString();
	}
	
	/**
	 * This function formats a double with a given precision. The resulting 
	 * string will have that many numbers after the decimal.
	 * @param value : The value to format.
	 * @param precision : The number of digits after the decimal to keep.
	 * @return : A formatted string.
	 */
	public static String formatDouble(double value, int precision) {
		StringBuffer buf = new StringBuffer("0.");
		for (int i = 0; i < precision; i++) {
			buf.append("#");
		}
		DecimalFormat format = new DecimalFormat(buf.toString());
		return format.format(value);
	}
	
	/**
	 * This function takes a many word string and converts it to a multi-line 
	 * string.
	 * @param value : The string to format.
	 * @return : The formatted string.
	 */
	public static String multiLine(String value) {
		String[] results = value.split("\\s+");
		StringBuffer buf = new StringBuffer();
		for (String result : results) {
			buf.append(result).append(System.getProperty("line.separator"));
		}
		return buf.toString().trim();
	}
	
	/**
	 * This function converts decimal degrees into degrees, minutes, seconds 
	 * (DMS) and returns the corresponding string decorated with degree marks.
	 * @param decdeg : The value to convert.
	 * @param useFloatSec : If true, use decimal seconds.
	 * @return : The DMS formatted string.
	 */
	public static String dmsFromDd(double decdeg, boolean useFloatSec) {
		String zeroPad = "%02d";
		int degrees = (int)decdeg;
		decdeg -= degrees;
		decdeg *= Astro.MINUTES_PER_HOUR;
		int minutes = (int)(decdeg);
		decdeg -= minutes;
		decdeg *= Astro.SECONDS_PER_MINUTE;
		double seconds = decdeg;
		String secStr = "";
		if (useFloatSec) {
			secStr = StrFormat.formatDouble(seconds, 1);
		}
		else {
			secStr = String.format(zeroPad, (int)seconds);
		}
		
		StringBuffer buf = new StringBuffer();
		buf.append(Integer.toString(degrees));
		buf.append(StrFormat.DEGREE_SYMBOL + " ");
		buf.append(String.format(zeroPad, minutes)).append("' ");
		buf.append(secStr).append("\"");
		return buf.toString();
	}
	
	/**
	 * This function creates a string with a specified number of spaces.
	 * @param size : The number of spaces requested.
	 * @return : A string containing the requested number of spaces.
	 */
	public static String blankBuffer(int size) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < size; i++) {
			buf.append(" ");
		}
		return buf.toString();
	}
	
	/**
	 * This function makes a date formatted string for the given Calendar.
	 * @param cal : The object holding the time.
	 * @return : The formatted date string.
	 */
	public static String dateFormat(Calendar cal) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
		StringBuffer buf = new StringBuffer(format.format(cal.getTime()));
		String tz = cal.getTimeZone().getDisplayName(TimeOps.dstOffset(cal) != 0, 
				TimeZone.SHORT);
		buf.append(" ").append(tz);
		return buf.toString();
	}
	
	/**
	 * This function makes a date formatted string without seconds for the 
	 * given Calendar.
	 * @param cal : The object holding the time.
	 * @return : The formatted date string.
	 */
	public static String dateFormatNoSeconds(Calendar cal) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
		StringBuffer buf = new StringBuffer(format.format(cal.getTime()));
		String tz = cal.getTimeZone().getDisplayName(TimeOps.dstOffset(cal) != 0, 
				TimeZone.SHORT);
		buf.append(" ").append(tz);
		return buf.toString();
	}
	
	/**
	 * Do not allow class instantiation.
	 */
	private StrFormat() {
	}
}
