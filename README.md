DigiCount:                 
=========
  
            App Name in Launcher - simons.DigiCount  
            
            Counter for Android            
                                          
            README.md                  


--------------------------------------------------------------------------------------------------------------------

Description:

This is an Android Application is a simple counter that helps a user keep track of whatever they want; anything that can be counted. Users create counter objects and name them when they are created. 

The app allows the user to then increment the counter, by displaying a view with a large button on it. This was designed to make it very simple for the user to increment. Users can reset a counter from this view as well. 

The main list view page allows a user to see all counters in a scrollable list. They can see the name of the counter and its current count. New counters are created at the botton of this page by typing in a name and clicking "+". Counter names must be unique, and they must be between 0 and 40 characters.

If a user selects a counter by tapping/clicking and holding, an options context menu will pop up. This menu allows a user to rename the selected counter, reset the selected counter, delete the selected counter, see stats for the selected counter, sort all of the counters (from largest count to smallest count) or cancel. The reset, delete, sort and cancel options happen in place and remain in this view

If the user selects rename, they are taken to a new view which allows the user to enter a new name. They can also cancel if they change their mind. Again, the new counter name must be between 0 and 40 characters, and must be unique (cannot be the same as the existing counter or anything other existing counter). 

Finally, a user is able to see statistics on their app. They do this in the context menu. The user is taken to a new view which will have a drop down menu. They can select a period of "Per Hour", "Per day", "Per Week", or "Per Month". This will display the number of increments made in each period. If no increments were made in a certain time period, then that period will not be displayed to the view. Note, these statistics will represent the object that was selected from the listView. 

---------------------------------------------------------------------------------------------------------------------

Classes:


1) CounterListModel - This class acted as a container to hold all of the CounterModel objects for the app. It uses
                      an ArrayList<CounterModel> container to hold these objects. Methods include (args not included                         here):
                      
                                    - CounterListModel()
                                    - getCounterList()   
                                    - setCounterList()
                                    - addCounterToList()
                                    - deleteCounter()
                                    - sortCounterList()
                      
2) CounterModel     - This class was used to model the counters themselves. These objects keep track of the current 
                      count, their name, when they were created, and hold a CounterStatsModel object as an attribute. 
                      Everytime that a counter is incremented, it sends a time stamp (instance of the Date class) to 
                      the stats model. If the counter is reset, it clears the stats model and sets it count to zero.                         Methods include (args not included here):
                      
                                    - CounterModel()
                                    - incrementCounter()
                                    - getDateCreated()
                                    - setDateCreated()
                                    - getDateList()
                                    - setDateList()
                                    - resetCounter()
                                    - setCounterName()
                                    - getCounterName()
                                    - setCount()
                                    - getCount()
                                    - getStatsModel()
                                    - compare()
                                    
3) CounterStatsModel - An instance of this class holds the statistical information for it's corresponding CounterModel                        object. Each object holds an ArrayList of all the timeStamps from each increment of the counter.                        It also calculates the number of counts per frequency based upon hour, day, week or month. It
                       can then return strings that represent these statistics. Methods include (args not included                            here):
                       
                                    - counterStatsModel()
                                    - getDateCreated()
                                    - setDateList()
                                    - addCountDate()
                                    - getDateList()
                                    - setDateCreated()
                                    - clearDateList()
                                    - getCountsInSpecifiedHour()
                                    - getCountsInSpecifiedDay()
                                    - getCountsInSpecifiedWeek()
                                    - getCountsInSpecifiedMonth()
                                    - getHourStrings()
                                    - getDayStrings()
                                    - getWeekStrings()
                                    - getMonthStrings()



--------------------------------------------------------------------------------------------------------------------

Android Activity Classes:

1) MainActivity - essentially a welcome page with a get started button

2) CounterListActivity - can add new counters, and displays current counters and their count in list view
                       - can click on counter to begin counting
                       - can click and hold on counter to display contextual menu, which displays:
                                    - rename
                                    - reset
                                    - delete
                                    - sort (all counters)
                                    - stats
                                    - cancel
                                    
3) CounterAcivity - this activity is the main area where the counting happens. Has a large button for easy increment
                  - has a reset button for simple reset. 
                  
4) RenameCounterActivity - allows a user to rename their counter

5) StatsActivity - Uses a drop down menu to display the type of stats (hour, day, week, or month)

--------------------------------------------------------------------------------------------------------------------

Other Java Classes:   

1) CounterListArrayAdapter - used to display the counterModel's name and count in a single line in the                                              CounterListActivity listView. 

--------------------------------------------------------------------------------------------------------------------
