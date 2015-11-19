/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tapaverager;


public interface TapInterface {

    /**
     * Get the timestamp of the tap event.
     *
     * @return
     */
    public long getTime();

    /**
     * Get the duration of the tap press.
     *
     * @return
     */
    public long getDuration();

    /**
     * Set the duration tap press.
     *
     * @param duration
     * @return
     */
    public long setDuration(long duration);

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
