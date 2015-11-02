package pistachio.com.biotap;

/* SequenceTap.java*/
/* Created by Cameron on 11/2/2015.*/
/* Stores the data of one tap. This includes press down and
/*  up times stored in System/Calendar long millisecond time.*/

public class SequenceTap {
    private long down;  /*Time when a tap is pushed down.*/
    private long up;    /*Time when a push down is released.*/

    /*Constructor.*/
    public SequenceTap(long d, long u) {
        this.down = d;
        this.up = u;
    }

    /*Setter methods.*/
    public void setDown(long value)     {this.down = value;}
    public void setUp(long value)       {this.up = value;}
    /*Getter methods.*/
    public long getDown()               {return this.down;}
    public long getUp()                 {return this.up;}

}
