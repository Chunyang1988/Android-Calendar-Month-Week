# Android-Calendar-Month-Week
https://github.com/Chunyang1988/Android-Calendar-Month-Week/wiki

# 日历控件

支持月显示，周显示

### 效果图如下

![月显示](https://github.com/Chunyang1988/Android-Calendar-Month-Week/blob/master/Pic/device-2016-06-16-114104.png)

![周显示](https://github.com/Chunyang1988/Android-Calendar-Month-Week/blob/master/Pic/device-2016-06-16-114143.png)

### 说明

1.控制默认当天会突出显示如上图蓝色部分，表示当天显示

2.控件可以在日期下面显示小点。（预览图暂时没有，后续补充）

### 使用

    <org.view.calendar.CalendarView
        android:id="@+id/view_calendar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:type="month" />



公开方法


		setAdapter(CalendarAdapter adapter)
		
		setOnCalendarItemClick(OnCalendarItemClick click) 
		
		setTextColor(int color)
		
		setUnMonthTextColor(int color)
		
		setTodayTextColor(int color)
		
		setTodayBackgroundColor(int color) 
		
		setPointColor(int color)
		
		setSelectBackgroudColor(int color)
		
		setTextSize(int size)
		
		setCalendar(Calendar calendar)
		
		setShowType(Type type)
		
		setBackgroupColor(int color)


Adapter

	public static abstract class CalendarAdapter {

		public abstract void bindCalendar(Calendar calendar);

		public boolean isPoint(Calendar calendar) {
			return false;
		}
	}


主要作用是用于计算小点，自己可以优化更改



### 支持属性


	color:

	defaultTextColor:默认字体颜色

	todayTextColor:当天字体颜色

	todayBackground：当天日期背景颜色

	unMonthTextColor：非本月字体颜色

	pointColor：小点颜色

	selectColor：点击选中的背景颜色


	enum：
	typ:周显示，月显示
	mont,week
