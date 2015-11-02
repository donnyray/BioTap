package pistachio.com.biotap;

/* Tap.java.*/
/* Created by Cameron on 11/2/2015.*/
/* Interface for TapSequence class.*/

public interface Tap {
    /*Setter and getter for tap time.*/
    /*The call for System.currentTimeMillis and Calendar.getTimeInMillis both return long values.*/
    void setUpTime(long up);
    void setDownTime(long down);
    long getUpTime();
    long getDownTime();

    /*More to be added for area/location of tap.*/
}
