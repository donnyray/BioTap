package pistachio.com.biotap;

/* Tap.java.*/
/* Created by Cameron on 11/2/2015.*/
/* Interface for TapSequence class.*/

public interface Tap {
    public String toString();
    /*The call for System.currentTimeMillis and Calendar.getTimeInMillis both return long values.*/
    /*Setter methods.*/
    public void setTime(long value);
    public void setAction(String value);
    public void setXCoord(float value);
    public void setYCoord(float value);
    /*Getter methods.*/
    public long getTime();
    public String getAction();
    public float getXCoord();
    public float getYCoord();
}
