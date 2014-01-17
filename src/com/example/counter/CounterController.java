package com.example.counter;

import java.util.ArrayList;

public class CounterController implements CounterControllerInterface
{
    private CounterListModel counterListModel;

    public CounterController() {
        super();
        counterListModel = new CounterListModel();
    }
    
    public void addCounters(CounterModel counter) {
        ArrayList<CounterModel> list = counterListModel.getCounterList();
        list.add(new CounterModel());
    }

    
}
