package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
	private static DateProvider instance = null;

	public static DateProvider getInstance() {
		if (instance == null)
			synchronized (DateProvider.class) {
				if (instance == null)
					instance = new DateProvider();
			}
		return instance;
	}

	// avoid serialization break
	public Object readResolve() {
		return getInstance();
	}

	public boolean getLeap() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int year = cal.get(Calendar.YEAR);
		if (year % 400 == 0)
			return true;
		if ((year % 4 == 0) && (year % 100 != 0))
			return true;
		return false;
	}

	public int dateDiff(Date a, Date b) {
		return (int) (a.getTime() - b.getTime()) / (1000 * 60 * 60 * 24);
	}

	public Date now() {
		return Calendar.getInstance().getTime();
	}
}
