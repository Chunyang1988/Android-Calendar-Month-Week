package com.demo.calendarviewdemo;

import java.util.Calendar;

import org.view.calendar.CalendarView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentWeek extends Fragment {

	public static FragmentWeek newInstance(int position) {
		FragmentWeek f = new FragmentWeek();
		Bundle bundle = new Bundle();
		bundle.putInt("p", position);
		f.setArguments(bundle);
		return f;
	}

	CalendarView mCalendarView;

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.framgent_month, container, false);
		mCalendarView = (CalendarView) view.findViewById(R.id.view_calendar);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Bundle bundle = getArguments();
		int position = bundle.getInt("p");
		Calendar calendar = UtilCalendar.getCalendarWeek(position);
		mCalendarView.setCalendar(calendar);
	}

}
