/**
 * This class stores timeStamps for increments of an object. Each CounterModel object has
 * a CounterStatsModel object as an attribute. These stats models keep track of increment 
 * time stamps, and return statistical information on increments by hour, day, week and month
 * These objects are Serializable, as to be passed through bundles into a fragment activity.
 * 
 * CURRENT ISSUES:
 * 	- the implementation for displaying the stats is asymptotically bad. It has a double nested
 * 	  for loop (across two methods) and slows down for large counts as it's runtime grows 
 * 	  (T(n) = O(n^2)). If given more time, a more efficient algorithm would have been implemented. 
 * 
 * @author Brad Simons
 * @date January 30, 2014
 */

package ca.ualberta.ca.simons_counter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CounterStatsModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Date> dateList;
	private Date dateCreated;

	/**
	 * This enum provides easy access to abbreviated months. Used in all "getStrings" methods
	 */
	private enum Months {Jan, Feb, Mar, Apr, May, June, July, Aug, Sept, Nov, Oct, Dec;};

	/**
	 * This enumerator provides strings to display time rounded down to the nearest hour.
	 * It is primarily used by the getHourStrings() method.
	 */
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

	/**
	 * Constructor. Inits new ArrayList and timeStamps its creation
	 */
	public CounterStatsModel() {
		dateList = new ArrayList<Date>();
		dateCreated = new Date();
	}

	/**
	 * Getter for the date created time stamp
	 * @return date object which is the date of creation
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * Setter for the dateList object
	 * @param dateList ArrayList object
	 */
	public void setDateList(ArrayList<Date> dateList) {
		this.dateList = dateList;
	}
	
	/**
	 * Adds a new time stamp for a count and adds it to the
	 * dateList array
	 * @param newDate object
	 */
	public void addCountDate(Date newDate) {
		dateList.add(newDate);
	}

	/**
	 * Getter for the dateList array
	 * @return dateList array
	 */
	public ArrayList<Date> getDateList() {
		return dateList;
	}
	
	/**
	 * Setter for date created
	 * @param date object which represents creation date
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * clears the dateList. This occurs when a Counter Model 
	 * object resets itself to zero. 
	 */
	public void clearDateList() {
		dateList.clear();
	}

	/**
	 * This method takes a date object as an argument, and 
	 * compares its time stamp to all other date objects in
	 * the dateList array. It then counts how many of the objects 
	 * in the array have the same specific hour as the date argument
	 * @param start date object
	 * @return count - number of counts in specified hour
	 */
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

	/**
	 * This method takes a date object as an argument, and 
	 * compares its time stamp to all other date objects in
	 * the dateList array. It then counts how many of the objects 
	 * in the array have the same specific day as the date argument
	 * @param start date object
	 * @return count - number of counts in specified day
	 */
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

	/**
	 * This method takes a date object as an argument, and 
	 * compares its time stamp to all other date objects in
	 * the dateList array. It then counts how many of the objects 
	 * in the array have the same specific week as the date argument.
	 * The method also backtracks the date 1 day at a time until
	 * it reaches the beginning of that week (the nearest Sunday)
	 * @param start date object
	 * @return count - number of counts in specified week
	 */
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

	/**
	 * This method takes a date object as an argument, and 
	 * compares its time stamp to all other date objects in
	 * the dateList array. It then counts how many of the objects 
	 * in the array have the same specific month as the date argument
	 * @param start date object
	 * @return count - number of counts in specified month
	 */
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

	/**
	 * this method populates a string arraylist with the results of 
	 * the getCountInSpecifiedHour method. It does this for every
	 * object in the dateList. It checks to see if each object is already
	 * in the string array. If not, it adds a string which display the number
	 * of counts that occurred in the same hour. This string array will then 
	 * contain the results of all counts in the same hour.
	 * @return string arraylist with results of counts in the same hour
	 */
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

	/**
	 * this method populates a string arraylist with the results of 
	 * the getCountInSpecifiedDay method. It does this for every
	 * object in the dateList. It checks to see if each object is already
	 * in the string array. If not, it adds a string which display the number
	 * of counts that occurred in the same day. This string array will then 
	 * contain the results of all counts in the same day.
	 * @return string arraylist with results of counts in the same day
	 */
	public ArrayList<String> getDayStrings() {
		ArrayList<String> dayStrings = new ArrayList<String>();
		for (Date date : dateList) {
			int count = this.getCountsInSpecifiedDay(date);

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

	/**
	 * this method populates a string arraylist with the results of 
	 * the getCountInSpecifiedWeek method. It does this for every
	 * object in the dateList. It checks to see if each object is already
	 * in the string array. If not, it adds a string which display the number
	 * of counts that occurred in the same week. This string array will then 
	 * contain the results of all counts in the same week.
	 * @return string arraylist with results of counts in the same week
	 */
	public ArrayList<String> getWeekStrings() {
		ArrayList<String> weekStrings = new ArrayList<String>();
		for (Date date : dateList) {
			int count = this.getCountsInSpecifiedWeek(date);
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

	/**
	 * this method populates a string arraylist with the results of 
	 * the getCountInSpecifiedMonth method. It does this for every
	 * object in the dateList. It checks to see if each object is already
	 * in the string array. If not, it adds a string which display the number
	 * of counts that occurred in the same month. This string array will then 
	 * contain the results of all counts in the same month.
	 * @return string arraylist with results of counts in the same month
	 */
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
