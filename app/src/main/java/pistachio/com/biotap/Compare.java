/* Compare.java                     @author(Cameron)               */
/* @version(Iteration 2)            @editor(Cameron)               */
package pistachio.com.biotap;

import java.util.ArrayList;
import java.lang.Math;

public class Compare {

    /*Method used for comparing 2 tap sequences. Returns a score by*/
    /* comparing the master and attempted sequence.*/
    public double dissimilarityScore(ArrayList<Tap> master,
                                     ArrayList<Tap> attempt,
                                     double[] standDev) {
        /*Checks to make sure sequences are the same size. If not*/
        /* the same size, returns a negative to indicate non-*/
        /* acceptance, since all scores returned are >= 0.*/
        if (master.size() != attempt.size()) {
            return -1.00;
        }

        /*Used to sum all the magnitudes of each comparison.*/
        double magSum = 0.0;
        /*Goes through each elements in sequences, and adds to the*/
        /* over all sum. Returns sum as dissimilarity score.*/
        for (int i = 0; i < master.size(); i++) {
            magSum =+ tapFormula(master.get(i), attempt.get(i), standDev[i]);
        }
        return magSum;
    }

    /*Goes through each element in a Tap class necessary to produce*/
    /* a dissimilarity score, and sums the result of each element's*/
    /* formula value.*/
    private double tapFormula(Tap master, Tap attempt, double sd) {
        double total = 0.0;
        /*Formula: |(Xi - Ti) / SDi | for all elements in vector.*/
        /*Will eventually add x coord, y coord, and area. Checks*/
        /* for perfect match. Makes no division by 0 if standard*/
        /* deviation is 0.*/
        if (sd != 0) {
            total = ((double) Math.abs(attempt.getTime() -
                    master.getTime()) / sd);
        }
        return total;
    }
}
