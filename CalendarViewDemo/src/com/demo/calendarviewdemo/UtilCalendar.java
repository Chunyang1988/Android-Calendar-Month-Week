package com.demo.calendarviewdemo;

import java.util.Calendar;

public class UtilCalendar {

	public static final int MIN_YEAR = 2016;
	public static final int MAX_YEAR = 2099;

	public static Calendar getCalendarWeek(int position) {
		Calendar cal = Calendar.getInstance();
		int week = position;// 53
		for (int i = MIN_YEAR; i <= MAX_YEAR; i++) {
			cal.set(Calendar.YEAR, i);
			int yearMaxWeek = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);// 52
			if (yearMaxWeek - week < 0) {
				week = Math.abs(yearMaxWeek - week);
				continue;
			}
			for (int j = 0; j < 12; j++) {
				cal.set(Calendar.MONTH, j);
				int maxDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				for (int k = 1; k <= maxDayOfMonth; k++) {
					cal.set(Calendar.DAY_OF_MONTH, k);
					int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
					if (weekOfYear == week) {
						return cal;
					}
				}
			}
		}
		return cal;
	}

	public static Calendar getCalendarMonth(int position) {
		Calendar cal = Calendar.getInstance();
		int year = MIN_YEAR + (position / 12);
		int month = position % 12;
		cal.set(year, month, 1);
		return cal;
	}

	/**
	 * 获取制定时间周的第一天
	 * 
	 * @param cal
	 * @return
	 */
	public static int getFirstDayOfWeek(Calendar cal) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(cal.getTime());
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static Calendar getCalendarFirstDayOfWeek(Calendar cal) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(cal.getTime());
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		return calendar;
	}

	public static Calendar[] getCalendarDayOfWeek(Calendar cal) {
		Calendar[] days = new Calendar[7];
		Calendar f = getCalendarFirstDayOfWeek(cal);
		int d = f.get(Calendar.DAY_OF_MONTH);
		int md = f.getActualMaximum(Calendar.DAY_OF_MONTH);
		int n = 1;
		for (int i = 0; i < days.length; i++) {
			Calendar c = Calendar.getInstance();
			c.setTime(f.getTime());
			int day = d + i;
			if (day <= md) {
				c.set(Calendar.DAY_OF_MONTH, day);
				days[i] = c;
			} else {
				c.add(Calendar.MONDAY, 1);
				c.set(Calendar.DAY_OF_MONTH, n++);
				days[i] = c;
			}
		}
		return days;
	}

	public static Calendar[] getCalendarDayOfMonth(Calendar cal) {
		Calendar[] days = new Calendar[42];
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Calendar f = getCalendarFirstDayOfWeek(cal);
		int d = f.get(Calendar.DAY_OF_MONTH);
		int fmd = f.getActualMaximum(Calendar.DAY_OF_MONTH);
		int n = 1;
		for (int i = 0; i < days.length; i++) {
			Calendar c = Calendar.getInstance();
			c.setTime(f.getTime());
			int day = d + i;
			if (day <= fmd) {
				c.set(Calendar.DAY_OF_MONTH, day);
				days[i] = c;
			} else {
				c.add(Calendar.MONDAY, 1);
				c.set(Calendar.DAY_OF_MONTH, n++);
				days[i] = c;
			}
		}
		return days;
	}

	public static int[] getDayOfWeek(Calendar cal) {
		int[] days = new int[7];
		int f = getFirstDayOfWeek(cal);
		int n = 1;
		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 0; i < days.length; i++) {
			int day = f + i;
			if (day <= maxDay)
				days[i] = day;
			else
				days[i] = n++;
		}
		return days;
	}

	public static int[] getDayOfMonth(Calendar cal) {
		int[] days = new int[42];
		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		cal.add(Calendar.MONTH, -1);
		int sm = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(year, month, 1);
		int f = getFirstDayOfWeek(cal);
		int d = 1;
		for (int i = 0; i < days.length; i++) {
			int day = f + i;
			if (day <= sm)
				days[i] = day;
			else {
				if (d > maxDay) {
					d = 1;
				}
				days[i] = d++;
			}
		}
		return days;
	}

	/**
	 * 取得当前日期所在周的最后一天
	 * 
	 * @param cal
	 * @return
	 */
	public static int getLastDayOfWeek(Calendar cal) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(cal.getTime());
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6); // Saturday
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static int getCurrentWeek(Calendar cal) {
		int year = cal.get(Calendar.YEAR);
		Calendar c = Calendar.getInstance();
		int current = 0;
		int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
		int month = cal.get(Calendar.MONTH);
		if (year == MIN_YEAR && month == 11 && weekOfYear == 1) {
			current = c.getActualMaximum(Calendar.WEEK_OF_YEAR) + weekOfYear;
		} else {
			current = weekOfYear;
		}
		for (int i = MIN_YEAR; i < year; i++) {
			c.set(Calendar.YEAR, i);
			current += c.getActualMaximum(Calendar.WEEK_OF_YEAR);
		}
		return current - 1;
	}

	public static int getCurrentMonth(Calendar cal) {
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int y = year - MIN_YEAR;
		return y >= 0 ? (y * 12) + month : 0;
	}

	public static int getMaxWeekPager() {
		int pager = 0;
		Calendar cal = Calendar.getInstance();
		for (int i = MIN_YEAR; i <= MAX_YEAR; i++) {
			cal.set(Calendar.YEAR, i);
			pager += cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
		}
		return pager;
	}

	public static int getMaxMonthPager() {
		return (MAX_YEAR - MIN_YEAR) * 12 + 12;
	}

}
