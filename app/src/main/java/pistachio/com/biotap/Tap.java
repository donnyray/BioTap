package pistachio.com.biotap;

/* Tap.java.*/
/* Created by Cameron on 11/2/2015.*/
/* Interface for TapSequence class.*/

public interface Tap {
    /*Setter and getter for tap time.*/
    /*The call for System.currentTimeMillis and Calendar.getTimeInMillis both return long values.*/
    void setUp(long up);
    void setDown(long down);
    long getUp();
    long getDown();

    /*More to be added for area/location of tap.*/
}
