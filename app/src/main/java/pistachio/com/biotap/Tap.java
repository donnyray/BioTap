package pistachio.com.biotap;

public class Tap {

    /**
     * Timestamp
     */
    private long time;

    /**
     * Create a new instance of a tap.
     *
     * @param time
     */
    private Tap(long time) {
        this.set(time);
    }


    /**
     * Named constructor for a tap.
     *
     * @param time
     *
     * @return Tap
     */
    public static Tap record(long time) {
        return new Tap(time);
    }

    /**
     * Set properties of at tap.
     *
     * @param time
     */
    protected void set(long time) {

        this.time = time;

    }

    public String toString() {
        return String.valueOf(this.getTime());
    }

    /**
     * Get the time interval between down and up.
     * @return
     */
    public long getTime() {
        return this.time;
    }
}
