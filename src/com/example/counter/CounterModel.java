package com.example.counter;

public class CounterModel {
	
    String 	counterName;
    int		count;
	
    CounterModel() {
	count = 0;
    }
	
    public void incrementCounter() {
	count++;
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
}
