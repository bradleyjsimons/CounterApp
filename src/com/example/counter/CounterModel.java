package com.example.counter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class CounterModel implements Serializable, Comparator<CounterModel> {

    private static final long serialVersionUID = 1L;
    private String counterName;
    private Date dateCreated;
    private ArrayList<Date> dateList;
    int	count;

    CounterModel(String name) {
        counterName = name;
        count = 0;
        dateCreated = new Date();
        dateList = new ArrayList<Date>();
    }
    
    CounterModel() {
        count = 0;
        dateCreated = new Date();
        dateList = new ArrayList<Date>();
    }

    public void incrementCounter() {
        count++;
        Date timeStamp = new Date();
        dateList.add(timeStamp);
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ArrayList<Date> getDateList() {
        return dateList;
    }

    public void setDateList(ArrayList<Date> dateList) {
        this.dateList = dateList;
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
        return this.getCounterName() + " - " + Integer.toString(this.getCount());
    }

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
