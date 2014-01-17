package com.example.counter;

import java.util.ArrayList;

public class CounterListModel {
        
    private static ArrayList<CounterModel> counterList;

    public CounterListModel() {
        super();
        counterList = new ArrayList<CounterModel>();
    }
    
    public static ArrayList<CounterModel> getCounterList() {
        return counterList;
    }
    
    public static void setCounterList(ArrayList<CounterModel> counterList) {
        CounterListModel.counterList = counterList;
    }
}
