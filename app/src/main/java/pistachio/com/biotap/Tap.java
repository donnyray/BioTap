package pistachio.com.biotap;

public class Tap implements TapInterface {

    /**
     * Timestamp of tap.
     */
    private long time;

    /**
     * Interval of tap.
     */
    private long interval;

    /**
     * X coordinate of tap.
     */
    private int x;

    /**
     * Y coordinate of tap.
     */
    private int y;

    /**
     * Create a new instance of a tap with an interval.
     *
     * @param time
     * @param interval;
     */
    public Tap(long time, long interval, int x, int y) {
        this.set(time, interval, x, y);
    }

    /**
     * Create a new instance of a tap without an interval.
     *
     * @param time
     * @param x
     * @param y
     */
    public Tap(long time, int x, int y) {
        this.set(time, 0, x, y);
    }

    /**
     * Set properties on instance.
     *
     * @param time
     * @param interval
     * @param x
     * @param y
     */
    protected void set(long time, long interval, int x, int y) {
        this.time = time;
        this.interval = interval;
        this.x = x;
        this.y = y;
    }

    /**
     * Get string value of tap.
     *
     * @return
     */
    public String toString() {
        return this.getTime() + "/" + this.getInterval() + "/" + this.getX() + "/" + this.getY();
    }

    /**
     * Get the time interval between down and up.
     *
     * @return
     */
    public long getTime() { return this.time; }

    /**
     * Get the action of the tap.
     *
     * @return
     */
    public long getInterval() { return this.interval; }

    /**
     * Set the interval of the tap.
     *
     * @param interval
     * @return
     */
    public long setInterval(long interval) { return this.interval = interval; }

    /**
     * Get the x coordinate of tap.
     *
     * @return
     */
    public int getX() { return this.x; }

    /**
     * Get the y coordinate of tap.
     *
     * @return
     */
    public int getY() { return this.y; }

    /**
     * Get the timestamp of down touch.
     *
     * @return
     */
    public long getDownTime() { return this.time; }

    /**
     * Get the timestamp of up touch.
     *
     * @return
     */
    public long getUpTime() { return this.time + this.interval; }

}
