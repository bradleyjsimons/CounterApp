package com.example.counter;

import java.util.ArrayList;

public interface CounterControllerInterface {
    public void addCounter(CounterModel counter);
    public ArrayList<CounterModel> getCounterArrayList();
}
