/* AlgoUnitTest.java            @author(Cameron)                   */
/* Used to test comparison algorithm. Makes some random Taps and   */
/*  seqeunces runs them through the Compare class algorithm.       */
package pistachio.com.biotap;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Cameron
 */
import java.util.ArrayList;

public class TestClass {
    public static void main(String[] args) {
        /*Create two identical ArrayLists to be tested.*/
        ArrayList<Tap> att1 = new ArrayList<>();
        ArrayList<Tap> att2 = new ArrayList<>();
        ArrayList<Tap> att3 = new ArrayList<>();
        /*Taps that will be added to sequence.*/
        Tap t0 = new Tap( 0, 50, 405, 605);
        Tap t1 = new Tap( 5, 40, 305, 400);
        Tap t2 = new Tap(10, 60, 265, 265);
        Tap t3 = new Tap( 5, 55, 195, 345);
        Tap t4 = new Tap(20, 35, 345, 305);
        Tap t5 = new Tap(25, 55, 200, 360);
        Tap t6 = new Tap(10, 80,  55, 105);
        Tap t7 = new Tap( 5, 85, 255, 605);
        Tap t8 = new Tap(45, 75, 355, 405);
        Tap t9 = new Tap(30, 85, 205, 255);
        
        /*Adding taps to the trial sequences.*/
        att1.add(t0);
        att2.add(t6);
        att3.add(t3);
        
        att1.add(t1);
        att2.add(t2);
        att3.add(t1);
        
        att1.add(t7);
        att2.add(t5);
        att3.add(t2);
        
        att1.add(t1);
        att2.add(t3);
        att3.add(t1);
        
        att1.add(t1);
        att2.add(t4);
        att3.add(t6);
        
        att1.add(t8);
        att2.add(t3);
        att3.add(t2);
        
        /*Creates a false attempt.*/
        ArrayList<Tap> attempt = new ArrayList<>();
        attempt.add(t4);
        attempt.add(t3);
        attempt.add(t0);
        attempt.add(t6);
        attempt.add(t2);
        attempt.add(t7);        
        
        /*Put attempts in an array of sequences.*/
        ArrayList<Tap>[] diffSeq = (ArrayList<Tap>[])new ArrayList[3];
        diffSeq[0] = att1;
        diffSeq[1] = att2;
        diffSeq[2] = att3;
        
        ArrayList<Tap>[] sameSeq = (ArrayList<Tap>[])new ArrayList[3];
        sameSeq[0] = att1;
        sameSeq[1] = att1;
        sameSeq[2] = att1;
        
        /*Test for dissimilarity score.*/
        System.out.print("\nTESTING TESTING\n");
        System.out.print("Sequence att1: " + att1.toString() + "\n");
        System.out.print("Sequence att2: " + att2.toString() + "\n");
        System.out.print("Sequence att3: " + att3.toString() + "\n");
        
        System.out.print("diffSeq Array = att1, att2, att3\n");
        System.out.print("sameSeq Array = att1, att1, att1\n");
        
        System.out.print("Test 1: Mean, StdDev, and DisScore");
        System.out.print(" of diffSeq\n");
        /*Create mean sequence of different trials.*/
        ArrayList<Tap> meanDiff = Compare.meanSeq(3, diffSeq);
        System.out.print("MeanDiff " + meanDiff.toString() + "\n");
        /*Create standard deviation sequence of different trials*/
        ArrayList<Tap> sdDiff = Compare.standDeviation(3, diffSeq);
        System.out.print(sdDiff.toString() + "\n");
        /*Determine similarity score.*/
        double d1 = Compare.dissimilarityScore(meanDiff, attempt, sdDiff);
        System.out.print("Dissimilarity Score MeanDiff: " + d1 + "\n\n");
        
        System.out.print("Test 2: Mean, StdDev, and DisScore");
        System.out.print(" of sameSeq\n");
        /*Create mean sequence of different trials.*/
        ArrayList<Tap> meanSame = Compare.meanSeq(3, sameSeq);
        System.out.print("MeanSame " + meanSame.toString() + "\n");
        /*Create standard deviation sequence of different trials*/
        ArrayList<Tap> sdSame = Compare.standDeviation(3, sameSeq);
        System.out.print(sdSame.toString() + "\n");
        /*Determine similarity score.*/
        double d2 = Compare.dissimilarityScore(meanSame, attempt, sdSame);
        System.out.print("Dissimilarity Score MeanSame: " + d2 + "\n\n");
        
    }
    

    
    
    
    
    
    
    
}
