package com.demo.calendarviewdemo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.view.calendar.CalendarView;
import org.view.calendar.CalendarView.CalendarAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentMonth extends Fragment {

	public static FragmentMonth newInstance(int position) {
		FragmentMonth f = new FragmentMonth();
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
		Calendar calendar = UtilCalendar.getCalendarMonth(position);
		mCalendarView.setCalendar(calendar);

		mCalendarView.setAdapter(new CalendarAdapter() {

			List<Boolean> points;

			@Override
			public void bindCalendar(Calendar calendar) {
				ArrayList<Calendar> calendars = new ArrayList<Calendar>();
				for (int i = 0; i < 4; i++) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_MONTH, i);
					calendars.add(cal);
				}
				points = mCalendarView.equalPoints(calendars);

			}

			@Override
			public boolean isPoint(int i) {
				return points.get(i);
			}
		});
	}

}
