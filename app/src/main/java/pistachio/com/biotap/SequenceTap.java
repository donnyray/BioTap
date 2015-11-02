package pistachio.com.biotap;

/* SequenceTap.java*/
/* Created by Cameron on 11/2/2015.*/
/* Stores the data of one tap. This includes press down and
/*  up times stored in System/Calendar long millisecond time.*/

public class SequenceTap implements Tap {
    private long downTime;  /*Time when a tap is pushed down.*/
    private long upTime;    /*Time when a push down is released.*/

    /*Constructor.*/
    public SequenceTap(long dT, long uT) {
        this.downTime = dT;
        this.upTime = uT;
    }

    /*Setter methods.*/
    public void setDownTime(long value)     {this.downTime = value;}
    public void setUpTime(long value)       {this.upTime = value;}
    /*Getter methods.*/
    public long getDownTime()               {return this.downTime;}
    public long getUpTime()                 {return this.upTime;}

}
