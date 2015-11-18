/* AlgoUnitTest.java            @author(Cameron)                   */
/* Used to test comparison algorithm.*/
package pistachio.com.biotap;

import org.junit.Test;
import static org.junit.Assert.*;

/* To work on unit tests, switch the Test Artifact in the Build*/
/*  Variants view.*/
public class AlgoUnitTest {
    @Test

    /*Create two identical ArrayLists to be tested.*/
            ArrayList<Tap> mast = new ArrayList<Tap>;
    ArrayList<Tap> attempt = new ArrayList<Tap>;
    /*Five taps that will be added to sequence.*/
    Tap t0 = new Tap(50, 405.0, 605.0);
    Tap t1 = new Tap(40, 305.0, 400.0);
    Tap t2 = new Tap(60, 265.0, 265.0);
    Tap t3 = new Tap(55, 195.0, 345.0);
    Tap t4 = new Tap(35, 345.0, 305.0);
    /*Adding taps to the master sequence.*/
    mast.add(t0);
    mast.add(t1);
    mast.add(t2);
    mast.add(t3);
    mast.add(t4);
    /*Adding taps to the atttempt sequence.*/
    attempt.add(t0);
    attempt.add(t1);
    attempt.add(t2);
    attempt.add(t3);
    attempt.add(t4);

    public void algo_is_correct() throws Exception {
        assertEquals(4, 2 + 2);
    }
}