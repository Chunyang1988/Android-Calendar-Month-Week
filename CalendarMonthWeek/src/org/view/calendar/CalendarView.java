package org.view.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class CalendarView extends View {

	private boolean isLoger = false;

	private int type;
	private int defaultTextColor;
	private int todayTextColor;
	private int unMonthTextColor;
	private int pointColor;
	private int selectColor;
	private int todayBackground;
	private float defaultTextSize;

	private int backgroundColor;

	private Calendar nowTime;
	private Calendar showTime;
	private Paint mPaint;

	private float itemWidth;
	private float itemHeight;
	private float itemPadding = 0;
	private int columns = 7;
	private int rows = 6;

	private boolean isDrawSelect;
	private int selectColumn;
	private int selectRow;

	private List<MonthDay> monthDays = new ArrayList<MonthDay>();

	public enum Type {
		Month, Week
	}

	public static interface OnCalendarItemClick {

		void onItemClick(Calendar calendar);
	}

	public static abstract class CalendarAdapter {

		/**
		 * 
		 * @param calendar
		 *            当前显示年月
		 */
		public abstract void bindCalendar(Calendar calendar);

		public boolean isPoint(int i) {
			return false;

		}

	}

	private OnCalendarItemClick mClickListener;

	private CalendarAdapter mAdapter;

	public CalendarView(Context context) {
		this(context, null);
	}

	public CalendarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.CalendarView, defStyleAttr, defStyleAttr);

		type = a.getInteger(R.styleable.CalendarView_type, 0);
		defaultTextColor = a.getColor(
				R.styleable.CalendarView_defaultTextColor, 0xff000000);
		todayTextColor = a.getColor(R.styleable.CalendarView_todayTextColor,
				0xFFFFFFFF);
		unMonthTextColor = a.getColor(
				R.styleable.CalendarView_unMonthTextColor, 0xFF838B83);
		pointColor = a
				.getColor(R.styleable.CalendarView_pointColor, 0xFF4876FF);
		selectColor = a.getColor(R.styleable.CalendarView_selectColor,
				0xFFE3E3E3);
		todayBackground = a.getColor(R.styleable.CalendarView_todayBackground,
				0xFFCD2626);

		a.recycle();

		defaultTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				16, getResources().getDisplayMetrics());

		backgroundColor = 0xFFFFFFFF;
		mPaint = new Paint();
		mPaint.setTextSize(defaultTextSize);

		nowTime = Calendar.getInstance();
		makeCalendar(nowTime);

	}

	public List<Boolean> equalPoints(List<Calendar> calendars) {
		List<Boolean> pps = new ArrayList<Boolean>();
		for (MonthDay day : monthDays) {
			boolean b = equals(calendars, day);
			pps.add(b);
		}
		return pps;
	}

	// private boolean equals(List<Calendar> calendars) {
	// if (calendars != null && !calendars.isEmpty())
	// for (Calendar cal : calendars) {
	// for (MonthDay day : monthDays) {
	// if (day.equals(cal))
	// return true;
	// }
	// }
	// return false;
	// }

	private boolean equals(List<Calendar> calendars, MonthDay day) {
		for (Calendar cal : calendars) {
			if (day.equals(cal))
				return true;
		}
		return false;
	}

	public void setAdapter(CalendarAdapter adapter) {
		mAdapter = adapter;
		if (mAdapter != null)
			mAdapter.bindCalendar(showTime);
	}

	public void setOnCalendarItemClick(OnCalendarItemClick click) {
		mClickListener = click;
	}

	public void setTextColor(int color) {
		if (defaultTextColor != color) {
			defaultTextColor = color;
			invalidate();
		}
	}

	public void setUnMonthTextColor(int color) {
		if (unMonthTextColor != color) {
			unMonthTextColor = color;
			invalidate();
		}
	}

	public void setTodayTextColor(int color) {
		if (todayTextColor != color) {
			todayTextColor = color;
			invalidate();
		}
	}

	public void setTodayBackgroundColor(int color) {
		if (todayBackground != color) {
			todayBackground = color;
			invalidate();
		}
	}

	public void setPointColor(int color) {
		if (pointColor != color) {
			pointColor = color;
			invalidate();
		}
	}

	public void setSelectBackgroudColor(int color) {
		if (selectColor != color) {
			selectColor = color;
			invalidate();
		}
	}

	public void setTextSize(int size) {
		if (defaultTextSize != size) {
			defaultTextSize = size;
			invalidate();
		}
	}

	public void setCalendar(Calendar calendar) {
		makeCalendar(calendar);
		invalidate();
	}

	public void setShowType(Type type) {
		int show = type == Type.Month ? 0 : 1;
		if (show != this.type) {
			this.type = show;
			invalidate();
		}
	}

	public void setBackgroupColor(int color) {
		if (backgroundColor != color) {
			backgroundColor = color;
			invalidate();
		}
	}

	private void makeCalendar(Calendar calendar) {
		showTime = Calendar.getInstance();
		showTime.setTime(calendar.getTime());
		monthDays.clear();
		if (type == 0) {
			makeMonthDay();
		} else {
			makeWeekDay();
		}
	}

	// private int getFirstDayOfWeek(Calendar cal) {
	// Calendar calendar = Calendar.getInstance();
	// calendar.setFirstDayOfWeek(Calendar.SUNDAY);
	// calendar.setTime(cal.getTime());
	// calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
	// return calendar.get(Calendar.DAY_OF_MONTH);
	// }

	private Calendar getCalendarFirstDayOfWeek(Calendar cal) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(cal.getTime());
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		return calendar;
	}

	private void makeWeekDay() {
		monthDays.clear();
		Calendar now = Calendar.getInstance();
		now.setTime(showTime.getTime());
		Calendar fdw = getCalendarFirstDayOfWeek(now);
		for (int i = 0; i < 7; i++) {
			MonthDay date = new MonthDay();
			Calendar cal = Calendar.getInstance();
			cal.setTime(fdw.getTime());
			date.setCanlendar(cal);
			monthDays.add(date);
			fdw.add(Calendar.DAY_OF_MONTH, 1);
		}
	}

	private void makeMonthDay() {
		Calendar now = Calendar.getInstance();
		now.setTime(showTime.getTime());
		int month = now.get(Calendar.MONTH);
		now.set(Calendar.DAY_OF_MONTH, 1);
		Calendar fdw = getCalendarFirstDayOfWeek(now);
		for (int i = 0; i < 42; i++) {
			MonthDay daydate = new MonthDay();
			Calendar cal = Calendar.getInstance();
			cal.setTime(fdw.getTime());
			daydate.setCanlendar(cal);
			int m = daydate.getMonth();
			daydate.setNoMonthDay(month != m);
			// daydate.setToday(daydate.equals(showTime));
			monthDays.add(daydate);

			fdw.add(Calendar.DAY_OF_MONTH, 1);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();

		itemWidth = (float) ((widthSize * 1.0 - paddingLeft - paddingRight - itemPadding
				* (columns - 1)) / columns);
		itemHeight = itemWidth;

		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		if (heightMode == MeasureSpec.UNSPECIFIED
				|| heightMode == MeasureSpec.AT_MOST) {

			if (type == 0) {
				float itemHeightSize = getPaddingBottom() + getPaddingTop()
						+ itemPadding * (rows - 1) + ((rows - 1) * itemHeight);
				heightSize = (int) Math.min(heightSize, itemHeightSize);
			} else {
				float itemHeightSize = getPaddingBottom() + getPaddingTop()
						+ itemHeight;
				heightSize = (int) Math.min(heightSize, itemHeightSize);
			}

		}
		setMeasuredDimension(widthSize, heightSize);
		if (isLoger)
			Log.e(VIEW_LOG_TAG, "heightSize->" + heightSize + " widthSize->"
					+ widthSize);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(backgroundColor);

		FontMetrics metrics = mPaint.getFontMetrics();
		float mt = metrics.top;
		float mb = metrics.bottom;
		float textHeight = Math.abs(mt) + Math.abs(mb);

		if (isDrawSelect) {// 画选择的圆圈

			int column = selectColumn;// 行
			int row = selectRow;// 列

			int index = row * columns + column;
			MonthDay date = monthDays.get(index);
			if (!date.isNoMonthDay) {

				if (mClickListener != null) {
					mClickListener.onItemClick(date.canlendar);
				}

				float cx = column * itemWidth + itemWidth / 2;
				float cy = row * itemHeight + itemHeight / 2;
				float radius = textHeight - mb;
				mPaint.setColor(selectColor);// 0xFFE3E3E3
				canvas.drawCircle(cx, cy, radius, mPaint);
				isDrawSelect = false;
			}
		}

		int size = type == 0 ? columns * rows : columns;
		for (int i = 0; i < size; i++) {
			MonthDay date = monthDays.get(i);
			int day = date.getDay();

			int column = i % columns;// 行
			int row = i / columns;// 列

			float textWidth = getTextWidth(mPaint, String.valueOf(day));

			float x = column * itemWidth + (itemWidth / 2 - textWidth / 2);
			float y = row * itemHeight + (itemHeight / 2 + textHeight / 2 - mb);

			float cx = column * itemWidth + itemWidth / 2;
			float cy = row * itemHeight + itemHeight / 2;
			float radius = textHeight - mb;
			if (mAdapter != null && mAdapter.isPoint(i)) {
				mPaint.setColor(pointColor);// 0xFF4876FF
				canvas.drawCircle(cx, cy + radius + 5, 4, mPaint);
			}
			if (date.isNoMonthDay) {
				mPaint.setColor(unMonthTextColor);// 0xFF838B83
			} else {
				if (date.equals(nowTime)) {
					mPaint.setColor(todayBackground);// 0xFFCD2626
					canvas.drawCircle(cx, cy, radius, mPaint);
					mPaint.setColor(todayTextColor);// 0xFFFFFFFF
				} else {
					mPaint.setColor(defaultTextColor);// 0xFF1F1F1F
				}
			}
			canvas.drawText(String.valueOf(day), x, y, mPaint);
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();

		float dx = 0;
		float dy = 0;
		if (action == MotionEvent.ACTION_DOWN) {
			float x = event.getX();
			float y = event.getY();
			dx = x;
			dy = y;
			selectColumn = (int) (x / itemWidth);
			selectRow = (int) (y / itemHeight);
			isDrawSelect = true;
			if (isLoger)
				Log.e(VIEW_LOG_TAG,
						"dispatchTouchEvent action->ACTION_DOWN column:-"
								+ selectColumn + " row:-" + selectRow);
			return true;
		}

		if (action == MotionEvent.ACTION_MOVE) {
			float x = event.getX();
			float y = event.getY();
			float my = Math.abs(dy - y);
			float mx = Math.abs(dx - x);
			if (isLoger)
				Log.e(VIEW_LOG_TAG,
						"dispatchTouchEvent action->ACTION_MOVE my->" + my
								+ " mx->" + mx);
			if (my > itemHeight || mx > itemWidth) {
				return false;
			} else
				return true;

		}
		if (action == MotionEvent.ACTION_UP) {
			if (isLoger)
				Log.e(VIEW_LOG_TAG, "dispatchTouchEvent action->ACTION_UP");
			if (isDrawSelect) {
				invalidate();
				return false;
			}

		}

		return false;
	}

	private int getTextWidth(Paint paint, String str) {
		int iRet = 0;
		if (str != null && str.length() > 0) {
			int len = str.length();
			float[] widths = new float[len];
			paint.getTextWidths(str, widths);
			for (int j = 0; j < len; j++) {
				iRet += (int) Math.ceil(widths[j]);
			}
		}
		return iRet;
	}

	static class MonthDay {
		Calendar canlendar;
		boolean isNoMonthDay;
		// boolean isToday;

		int year;
		int month;
		int day;

		public void setCanlendar(Calendar canlendar) {
			this.canlendar = canlendar;
		}

		public int getMonth() {
			return canlendar.get(Calendar.MONTH);
		}

		public int getDay() {
			return canlendar.get(Calendar.DAY_OF_MONTH);
		}

		public void setNoMonthDay(boolean isNoMonthDay) {
			this.isNoMonthDay = isNoMonthDay;
		}

		// public void setToday(boolean isToday) {
		// this.isToday = isToday;
		// }

		public boolean equals(Calendar cal) {
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);

			int y = canlendar.get(Calendar.YEAR);
			int m = canlendar.get(Calendar.MONTH);
			int d = canlendar.get(Calendar.DAY_OF_MONTH);
			return (year == y && month == m && day == d);
		}

	}
}
