package pistachio.com.biotap;

/* SequenceTap.java*/
/* Created by Cameron on 11/2/2015.*/
/* Stores the data of one tap. This includes press down and
/*  up times stored in System/Calendar long millisecond time.*/

public class SequenceTap implements Tap {
    private long lTime;     /*Time when a tap is pushed down.*/
    private String sAction; /*Action type.*/
    private float fXCoord;  /*x-coordinate of where the tap occured.*/
    private float fYCoord;  /*y-coordinate of where the tap occured.*/

    /*Constructor.*/
    public SequenceTap(long t, String a, float x, float y) {
        this.lTime = t;
        this.sAction = a;
        this.fXCoord = x;
        this.fYCoord = y;
    }

    /*Print out of the Tap sequence.*/
    public String toString() {
        return sAction + ", @System Time: " + lTime + " @coordinates (x, y): (" +
                fXCoord + ", " + fYCoord + ").";
    }

    /*Setter methods.*/
    public void setTime(long value)     {this.lTime = value;}
    public void setAction(String value) {this.sAction = value;}
    public void setXCoord(float value)  {this.fXCoord = value;}
    public void setYCoord(float value)  {this.fYCoord = value;}
    /*Getter methods.*/
    public long getTime()     {return this.lTime;}
    public String getAction() {return this.sAction;}
    public float getXCoord()  {return this.fXCoord;}
    public float getYCoord()  {return this.fYCoord;}

}
