package pistachio.com.biotap;

public class Tap {

    /**
     * Timestamp
     */
    private long time;

    private Action action;

    public static enum Action {
        UP,
        DOWN
    };

    /**
     * Create a new instance of a tap.
     *
     * @param time
     */
    private Tap(long time, Tap.Action action) {
        this.set(time, action);
    }

    /**
     * Named constructor for a up tap.
     *
     * @param time
     *
     * @return Tap
     */
    public static Tap up(long time) {
        return new Tap(time, Action.UP);
    }


    /**
     * Named constructor for a down tap.
     *
     * @param time
     *
     * @return Tap
     */
    public static Tap down(long time) {
        return new Tap(time, Action.DOWN);
    }

    /**
     * Set properties of at tap.
     *
     * @param time
     */
    protected void set(long time, Tap.Action action) {

        this.time = time;
        this.action = action;

    }

    /*Convert object to string.*/
    /* @return String */
    public String toString() {
        return this.getTime() + ", " + this.getAction();
    }

    public long getTime() {
        return this.time;
    }

    public Action getAction() {
        return this.action;
    }
}
