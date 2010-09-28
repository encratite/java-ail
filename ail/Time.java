package ail;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

public class Time {
	private static SimpleDateFormat format;
	
	private static SimpleDateFormat getDateFormat() {
		SimpleTimeZone utc = new SimpleTimeZone(0, "UTC");
		SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateForm.setTimeZone(utc);
		return dateForm;
	}
	
	public static String getDateString(Date input) {
		if(format == null)
			format = getDateFormat();
		StringBuffer buffer = new StringBuffer();
		format.format(input, buffer, new FieldPosition(0));
		return buffer.toString();
	}
	
	public static Date convertTimestamp(long input) {
		Date output = new Date();
		output.setTime(input * 1000);
		return output;
	}
}
