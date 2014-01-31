/**
 * This class creates counters which stores a number of counts, can be reset,
 * and renamed. It also keeps a statsModel object as an attribute, which is 
 * used to timeStamp each increment and later to display summaries of counts by
 * time. These objects implement a Comparator so that they can be sorted by their
 * count attribute, and implement Serializable so they can be passed from activity
 * to activity (and activity fragments).
 * 
 * @author Brad Simons
 * @date January 30, 2014
 */

package ca.ualberta.ca.simons_counter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class CounterModel implements Serializable, Comparator<CounterModel> {

	private static final long serialVersionUID = 1L;
	private String counterName;
	private Date dateCreated; // didn't end up using this, but could be useful in other situations
	private int	count;
	private CounterStatsModel statsModel;

	/**
	 * Constructor when supplied with name string
	 * @param a string which names the counter object
	 */
	CounterModel(String name) {
		counterName = name;
		count = 0;
		dateCreated = new Date();
		statsModel = new CounterStatsModel();
	}

	/**
	 * Basic Constructor
	 */
	CounterModel() {
		count = 0;
		dateCreated = new Date();
		statsModel = new CounterStatsModel();
	}

	/** 
	 * Increments the count int, timeStamps the increment, and adds
	 * the timestamp to the StatsModel object attribute
	 */
	public void incrementCounter() {
		count++;
		Date timeStamp = new Date();
		statsModel.addCountDate(timeStamp);
	}

	/**
	 * getter for dateCreated
	 * @return returns a date object for when this object was created
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * setter for dateCreated
	 * @param a date object when this model was created
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * getter for the dateList of the stats model
	 * @return statsModel arrayList with timeStamps of increments
	 */
	public ArrayList<Date> getDateList() {
		return statsModel.getDateList();
	}

	/**
	 * setter for the dateList of the stats model
	 */
	public void setDateList(ArrayList<Date> dateList) {
		statsModel.setDateList(dateList);
	}

	/**
	 * this simply resets the count int var to 0, and clears
	 * the stats model list.
	 */
	public void resetCounter() {
		count = 0;
		statsModel.clearDateList();
	}

	/**
	 * Setter for the counter objects name
	 * @param a counterName string for naming the object
	 */
	public void setCounterName(String counterName) {
		this.counterName = counterName;
	}

	/**
	 * Getter for the counter objects name
	 * @return returns a string corresponding to the counter name
	 */
	public String getCounterName() {
		return counterName;
	}

	/**
	 * setter for the count int var
	 * @param an int to set the current count;
	 */
	public void setCount(int count)
	{
		this.count = count;
	}

	/**
	 * Getter for the count var
	 * @return an int for the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Getter for the counterStatsModel object
	 * @return the statsModel object
	 */
	public CounterStatsModel getStatsModel() {
		return statsModel;
	}

	/**
	 * Compare function for comparing two counter objects
	 * Used to sort counter objects based on their count attribute
	 * Sorts from highest to lowest
	 */
	@Override
	public int compare(CounterModel counter1, CounterModel counter2) {
		int count1 = counter1.getCount();
		int count2 = counter2.getCount();
		if(count1 < count2 ){
			return 1;
		}else if(count1 > count2){
			return -1;
		}else {
			return 0;
		}
	}
}
