package com.example.counter;

import java.util.ArrayList;

public class CounterListModel {
;
	private static ArrayList<CounterModel> counterList;

    public CounterListModel() {
        super();
        counterList = new ArrayList<CounterModel>();
    }
    
    public ArrayList<CounterModel> getCounterList() {
        return counterList;
    }
    
    public void setCounterList(ArrayList<CounterModel> counterList) {
        CounterListModel.counterList = counterList;
    }
    
    public void addCounterToList(CounterModel counterModel) {
        counterList.add(counterModel);
    }
}
