package com.demo.calendarviewdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class WeekAdapter extends FragmentPagerAdapter {

	public WeekAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return FragmentWeek.newInstance(position);
	}

	@Override
	public int getCount() {
		return UtilCalendar.getMaxWeekPager();
	}

}
