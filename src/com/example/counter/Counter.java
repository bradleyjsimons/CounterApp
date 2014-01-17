package com.example.counter;

public class Counter {
	
	String 		counterName;
	int			count;
	
	Counter() {
		count = 0;
	}
	
	public void incrementCounter() {
		count++;
	}
	
	public void resetCounter() {
		count = 0;
	}
	
	public void setCounterName(String _counterName) {
		counterName = _counterName;
	}
	
	public String getCounterName() {
		return counterName;
	}
	
	public int getCount() {
		return count;
	}
}
