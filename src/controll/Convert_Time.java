package controll;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class Convert_Time {
	
	public static final int FETCH_DAYS = 28;
	public static final String[] WEEKDAYS = {
		"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
	};
	
	/**
	 * Convert the date format.
	 * @param date The date whose format is "YYYY/MM/DD".
	 * @return The integer format.
	 */
	public static int getDateInt(String date) {
		date = date.replace("/", "");
		return Integer.parseInt(date);
		
	}
	
	/**
	 * Convert the date to the weekday.
	 * @param date The date whose format is "YYYY/MM/DD".
	 * @return The week day.
	 */
	public static int getWeekDay(int date) {
		SimpleDateFormat format1=new SimpleDateFormat("yyyyMMdd");
		Date dat = null;
		try {
			dat = format1.parse(Integer.toString(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(dat);
		return (c.get(Calendar.DAY_OF_WEEK) + 5) % 7;
	}
	
	/**
	 * Convert the date to the weekday.
	 * @param date The date whose format is integer.
	 * @return The week day.
	 */
	public static int getWeekDay(String date) {
		SimpleDateFormat format1=new SimpleDateFormat("yyyyMMdd");
		Date dat = null;
		try {
			dat = format1.parse(date.replace("/", ""));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(dat);
		return (c.get(Calendar.DAY_OF_WEEK) + 5) % 7;
	}
	
	/**
	 * Get the duration by the given two time.
	 * @param t1 Start time.
	 * @param t2 End time.
	 * @return Duration.
	 */
	public static int getDur(int t1, int t2) {
		int h1 = t1 / 100;
		int m1 = t1 % 100;
		int h2 = t2 / 100;
		int m2 = t2 % 100;
		
		int time = (h2 * 60 + m2) - (h1 * 60 + m1);
		int hh = time / 60;
		int mm = time % 60;
		return hh * 100 + mm;
	}
}
