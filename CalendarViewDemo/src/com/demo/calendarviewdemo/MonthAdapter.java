package com.demo.calendarviewdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MonthAdapter extends FragmentPagerAdapter {

	public MonthAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return FragmentMonth.newInstance(position);
	}

	@Override
	public int getCount() {
		return UtilCalendar.getMaxMonthPager();
	}

}
