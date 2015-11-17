package pistachio.com.biotap;

public interface TapInterface {

    /**
     * Get the timestamp of the tap event.
     *
     * @return
     */
    public long getTime();

    /**
     * Get the interval of the tap press.
     *
     * @return
     */
    public long getInterval();

    /**
     * Set the interval tap press.
     *
     * @param interval
     * @return
     */
    public long setInterval(long interval);

    /**
     * Get the x coordinate of down tap.
     *
     * @return
     */
    public int getX();

    /**
     * Get the y coordinate of up tap.
     *
     * @return
     */
    public int getY();

    /**
     * Get the timestamp of the down action in a tap.
     *
     * @return
     */
    public long getDownTime();

    /**
     * Get the timestamp of the up action in a tap.
     *
     * @return
     */
    public long getUpTime();

    /**
     * Get a / separated string of the time, interval, x and y values of the tap.
     * @return
     */
    public String toString();
}
