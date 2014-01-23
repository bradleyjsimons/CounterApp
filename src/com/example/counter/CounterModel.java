package com.example.counter;

import java.util.ArrayList;
import java.util.Date;

public class CounterModel {
	
    String 	       counterName;
    ArrayList<Date>    dateList;
    int		       count;
	
    CounterModel() {
	count = 0;
	dateList = new ArrayList<Date>();
    }
	
    public void incrementCounter() {
	count++;
	Date dateOfIncrement = new Date();
	dateList.add(dateOfIncrement);
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
