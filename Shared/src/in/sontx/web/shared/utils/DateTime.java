package in.sontx.web.shared.utils;

import java.util.Calendar;

public class DateTime {
	private int hour;
	private int minute;
	private int second;
	private int day;
	private int month;
	private int year;

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.YEAR, year);
		return (int) (calendar.getTimeInMillis() / 1000L);
	}

	public String toDateString() {
		return String.format("%02d/%02d/%04d", day, month, year);
	}

	@Override
	public String toString() {
		return String.format("%02d:%02d:%02d - %02d/%02d/%04d", hour, minute, second, day, month, year);
	}

	public static DateTime now() {
		return parse(0);
	}

	public static DateTime parse(int time) {
		Calendar calendar = Calendar.getInstance();
		if (time > 0)
			calendar.setTimeInMillis(time * 1000L);
		DateTime dt = new DateTime();
		dt.day = calendar.get(Calendar.DAY_OF_MONTH);
		dt.month = calendar.get(Calendar.MONTH) + 1;
		dt.year = calendar.get(Calendar.YEAR);
		dt.hour = calendar.get(Calendar.HOUR_OF_DAY);
		dt.minute = calendar.get(Calendar.MINUTE);
		dt.second = calendar.get(Calendar.SECOND);
		return dt;
	}
}
