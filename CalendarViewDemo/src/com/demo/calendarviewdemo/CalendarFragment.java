package com.demo.calendarviewdemo;

import java.util.Calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class CalendarFragment extends Fragment {

	// public static final int MIN_YEAR = 2016;
	// public static final int MAX_YEAR = 2099;

	// public static int getMaxWeekPager() {
	// int pager = 0;
	// Calendar cal = Calendar.getInstance();
	// for (int i = MIN_YEAR; i <= MAX_YEAR; i++) {
	// cal.set(Calendar.YEAR, i);
	// pager += cal.getActualMaximum(Calendar.WEEK_OF_YEAR);
	// }
	// return pager;
	// }
	//
	// public static int getMaxMonthPager() {
	// return (MAX_YEAR - MIN_YEAR) * 12 + 12;
	// }
	//
	// public static Calendar getCalendarWeek(int position) {
	// Calendar cal = Calendar.getInstance();
	// int week = position;// 53
	// for (int i = MIN_YEAR; i <= MAX_YEAR; i++) {
	// cal.set(Calendar.YEAR, i);
	// int yearMaxWeek = cal.getActualMaximum(Calendar.WEEK_OF_YEAR);// 52
	// if (yearMaxWeek - week < 0) {
	// week = Math.abs(yearMaxWeek - week);
	// continue;
	// }
	// for (int j = 0; j < 12; j++) {
	// cal.set(Calendar.MONTH, j);
	// int maxDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	// for (int k = 1; k <= maxDayOfMonth; k++) {
	// cal.set(Calendar.DAY_OF_MONTH, k);
	// int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
	// if (weekOfYear == week) {
	// return cal;
	// }
	// }
	// }
	// }
	// return cal;
	// }
	// public static Calendar getCalendarMonth(int position) {
	// Calendar cal = Calendar.getInstance();
	// int year = MIN_YEAR + (position / 12);
	// int month = position % 12;
	// cal.set(year, month, 1);
	// return cal;
	// }

	private ViewPager mViewPagerMonth;
	private ViewPager mViewPagerWeek;

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_calendar, container,
				false);
		mViewPagerMonth = (ViewPager) view.findViewById(R.id.view_pager_month);
		mViewPagerWeek = (ViewPager) view.findViewById(R.id.view_pager_week);
		mViewPagerWeek.setVisibility(View.GONE);
		view.findViewById(R.id.btn_change).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (mViewPagerMonth.getVisibility() == View.VISIBLE) {
							mViewPagerWeek.setVisibility(View.VISIBLE);
							mViewPagerMonth.setVisibility(View.GONE);
						} else {
							mViewPagerWeek.setVisibility(View.GONE);
							mViewPagerMonth.setVisibility(View.VISIBLE);
						}
					}
				});
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		MonthAdapter monthAdapter = new MonthAdapter(getFragmentManager());
		mViewPagerMonth.setAdapter(monthAdapter);

		WeekAdapter weekAdapter = new WeekAdapter(getFragmentManager());
		mViewPagerWeek.setAdapter(weekAdapter);

		Calendar now = Calendar.getInstance();
		mViewPagerMonth
				.setCurrentItem(UtilCalendar.getCurrentMonth(now), false);

	}

}
