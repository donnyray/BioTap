package pistachio.com.biotap;

public class Tap {
    /**/
    /*The time variable needs to be changed to the difference*/
    /* between down press and up press for the algorithm.*/
    /**/
    private long time;  /*down press timestamp*/
    private float x;    /*x coordinate*/
    private float y;    /*y coordinate*/

    /*Create a new instance of a tap.*/
    /* @param time */
    /* @param x */
    /* @param y */
    private Tap(long time, float x, float y) {
        this.set(time, x, y);
    }

    /*Named constructor for a tap.*/
    /* @param time */
    /* @param x */
    /* @param y */
    /* @return Tap */
    public static Tap record(long time, float x, float y){
        return new Tap(time, x, y);
    }

    /*Set properties of at tap.*/
    /* @param time */
    /* @param x */
    /* @param y */
    protected void set(long time, float x, float y) {
        this.time = time;
        this.x = x;
        this.y = y;
    }

    /*Convert object to string.*/
    /* @return String */
    public String toString() {
        return this.time + "/" + this.x + "/" + this.y;
    }

    /*Getters. Used for comparison algorithm.*/
    public float getX() {
        return this.x;
    }
    public float getY() {
        return this.y;
    }
    public long getTime() {
        return this.time;
    }
}
