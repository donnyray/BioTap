package pistachio.com.biotap;

/**
 * Created by travismoretz on 10/30/15.
 *
 * this class is for the start button, adds a boolean value that can be used to lock/ unlock
 * the touch area for a tap sequence.
 */
public class SequenceTimer implements Timer {

    boolean status;

    @Override
    public void setTimer(boolean value) {
        this.status = value;
    }

    public boolean getTimer() {
        return this.status;
    }

    public SequenceTimer(boolean status) {
        this.status = status;
    }

}
