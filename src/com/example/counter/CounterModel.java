package com.example.counter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class CounterModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	String 	       		counterName;
    Date             	dateCreated;
    ArrayList<Date>    	dateList;
    int		       	   	count;
	
    CounterModel(String name) {
        counterName = name;
	count = 0;
	dateCreated = new Date();
	dateList = new ArrayList<Date>();
    }
	
    public void incrementCounter() {
	count++;
	Date timeStamp = new Date();
	dateList.add(timeStamp);
    }
	
    public void resetCounter() {
        count = 0;
    }
	
    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }
	
    public String getCounterName() {
        return counterName;
    }
	    
    public void setCount(int count)
    {
        this.count = count;
    }

    public int getCount() {
	return count;
    }
    
    @Override
    public String toString() {
        return this.counterName;
    }
}
