/**
 * This simple class is used to hold all the counterModel objects
 * currently active in the app. It does this by implementing an
 * ArrayList of CounterModel objects. It also provides sorting
 * methods, as well as adding and deleting counter objects.
 * 
 * @author Brad Simons
 * @date January 30, 2014
 */

package com.digicount.counter;

import java.util.ArrayList;
import java.util.Collections;

public class CounterListModel {

    private static ArrayList<CounterModel> counterList;

    /**
     * Constructor
     */
    public CounterListModel() {
        super();
        counterList = new ArrayList<CounterModel>();
    }

    /**
     * Getter for the counterList ArrayList
     * @return returns the counterList ArrayList
     */
    public ArrayList<CounterModel> getCounterList() {
        return counterList;
    }

    /**
     * Setter for the counterList ArrayList
     * @param a ArrayList<CounterModel> object
     */
    public void setCounterList(ArrayList<CounterModel> counterList) {
        CounterListModel.counterList = counterList;
    }

    /**
     * Adds a new counterModel object to the counterList.
     * @param counterModel object
     */
    public void addCounterToList(CounterModel counterModel) {
        counterList.add(counterModel);
    }
    
    /**
     * Removes a counterModel object from the counterList
     * @param counterModel object
     */
    public void deleteCounter(CounterModel counterModel) {
    	counterList.remove(counterModel);
    }

    /**
     * Sorts the counterList from counterModel with highest
     * count attribute to lowest count attribute
     */
    public void sortCounterList() {
        Collections.sort(this.getCounterList(), new CounterModel());
    }
}
