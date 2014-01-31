package com.example.counter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CounterStatsModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Date> dateList;
	private Date dateCreated;

	private enum Months {Jan, Feb, Mar, Apr, May, June, July, Aug, Sept, Nov, Oct, Dec;};

	private enum Hours {
		hour1("12:00AM"),hour2("1:00AM"),hour3("2:00AM"),hour4("3:00AM"),hour5("4:00AM"),hour6("5:00AM"),
		hour7("6:00AM"),hour8("7:00AM"),hour9("8:00AM"),hour10("9:00AM"),hour11("10:00AM"),hour12("11:00AM"),
		hour13("12:00PM"),hour14("1:00PAM"),hour15("2:00PM"),hour16("3:00PM"),hour17("4:00PM"),hour18("5:00PM"),
		hour19("6:00PM"),hour20("7:00PM"),hour21("8:00PM"),hour22("9:00PM"),hour23("10:00PM"),hour24("11:00PM");

		private final String text;

		private Hours(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}	
	};

	public CounterStatsModel() {
		dateList = new ArrayList<Date>();
		dateCreated = new Date();
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void addCounterDate(Date newDate) {
		dateList.add(newDate);
	}

	public void setDateList(ArrayList<Date> dateList) {
		this.dateList = dateList;
	}

	public ArrayList<Date> getDateList() {
		return dateList;
	}

	public void clearDateList() {
		dateList.clear();
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getCountsInSpecifiedHour(Date start) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);

		int month = calendar.get(Calendar.MONTH);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);

		int count = 0; 

		for (Date obj : dateList) {
			Calendar calObj = Calendar.getInstance();
			calObj.setTime(obj);

			if (calObj.get(Calendar.MONTH) == month &&
					calObj.get(Calendar.WEEK_OF_YEAR) == week &&
					calObj.get(Calendar.DAY_OF_YEAR) == day &&
					calObj.get(Calendar.HOUR_OF_DAY) == hour) {

				++count;
			}
		}
		return count;
	}

	public int getCountsInSpecifiedDay(Date start) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);

		int month = calendar.get(Calendar.MONTH);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int day = calendar.get(Calendar.DAY_OF_YEAR);

		int count = 0; 

		for (Date obj : dateList) {
			Calendar calObj = Calendar.getInstance();
			calObj.setTime(obj);

			if (calObj.get(Calendar.MONTH) == month &&
					calObj.get(Calendar.WEEK_OF_YEAR) == week &&
					calObj.get(Calendar.DAY_OF_YEAR) == day) {

				++count;
			}
		}
		return count;
	}

	public int getCountsInSpecifiedWeek(Date start) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);

		int month = calendar.get(Calendar.MONTH);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);

		int count = 0; 

		for (Date obj : dateList) {
			Calendar calObj = Calendar.getInstance();
			calObj.setTime(obj);

			if (calObj.get(Calendar.MONTH) == month &&
					calObj.get(Calendar.WEEK_OF_YEAR) == week) {

				++count;
			}
		}
		return count;
	}

	public int getCountsInSpecifiedMonth(Date start) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);

		int month = calendar.get(Calendar.MONTH);
		int count = 0; 

		for (Date obj : dateList) {
			Calendar calObj = Calendar.getInstance();
			calObj.setTime(obj);

			if (calObj.get(Calendar.MONTH) == month) {
				++count;
			}
		}
		return count;
	}

	public ArrayList<String> getHourStrings() {
		ArrayList<String> hourStrings = new ArrayList<String>();
		for (Date date : dateList) {
			int count = this.getCountsInSpecifiedHour(date);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);

			String newString = (Months.values()[month] + " " + day + " " + Hours.values()[hour] + " -- " + count);

			boolean shouldAddString = true;
			for (String string : hourStrings) {
				if (string.equals(newString)) {
					shouldAddString = false;
				}
			} 

			if (shouldAddString) {
				hourStrings.add(newString);
			}
		}
		return hourStrings;
	}

	public ArrayList<String> getDayStrings() {
		ArrayList<String> dayStrings = new ArrayList<String>();
		for (Date date : dateList) {
			int count = this.getCountsInSpecifiedMonth(date);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);

			String newString = (Months.values()[month] + " " + day + " -- " + count);

			boolean shouldAddString = true;
			for (String string : dayStrings) {
				if (string.equals(newString)) {
					shouldAddString = false;
				}
			} 

			if (shouldAddString) {
				dayStrings.add(newString);
			}
		}
		return dayStrings;
	}

	public ArrayList<String> getWeekStrings() {
		ArrayList<String> weekStrings = new ArrayList<String>();
		for (Date date : dateList) {
			int count = this.getCountsInSpecifiedMonth(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			while (calendar.get(Calendar.DAY_OF_WEEK) > calendar.getFirstDayOfWeek()) {
			    calendar.add(Calendar.DATE, -1);
			}
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			
			String newString = ("Week of "+ Months.values()[month] + " " + day + " -- " + count);

			boolean shouldAddString = true;
			for (String string : weekStrings) {
				if (string.equals(newString)) {
					shouldAddString = false;
				}
			} 

			if (shouldAddString) {
				weekStrings.add(newString);
			}
		}
		return weekStrings;
	}

	public ArrayList<String> getMonthStrings() {
		ArrayList<String> monthStrings = new ArrayList<String>();
		for (Date date : dateList) {
			int count = this.getCountsInSpecifiedMonth(date);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			int month = calendar.get(Calendar.MONTH);

			String newString = ("Month of "+ Months.values()[month] + " -- " + count);

			boolean shouldAddString = true;
			for (String string : monthStrings) {
				if (string.equals(newString)) {
					shouldAddString = false;
				}
			} 

			if (shouldAddString) {
				monthStrings.add(newString);
			}
		}
		return monthStrings;
	}
}
