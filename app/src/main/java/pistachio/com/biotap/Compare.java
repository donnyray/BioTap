package pistachio.com.biotap;
/* Created by Cameron on 11/12/2015. */

import java.util.ArrayList;

public class Compare {

    /*Class used for comparing 2 tap sequences. Returns a score by comparing the master and*/
    /* attempted sequence.*/
    public double dissimilarityScore(ArrayList<Tap> master, ArrayList<Tap> attempt,
                                     double[] standDev) {
        /*Checks to make sure sequences are the same size. If not the same size, returns a*/
        /* negative to indicate non-acceptance, since all scores returned are positive.*/
        if (master.size() != attempt.size()) {
            return -1.00; /*!!make sure negative will indicate error!!*/
        }

        double magSum = 0.0; /*Used to sum all the magnitudes of each comparison.*/
        /*Goes through each elements in sequences, and adds to the sum of magnitudes.*/
        for (int i = 0; i < master.size(); i++) {
            magSum =+ tapFormula(master.get(i), attempt.get(i), standDev[i]);
        }

        return magSum;
    }

    private double tapFormula(Tap master, Tap attempt, double sd) {
        /**/
        /*FORMULA HERE*/
        /**/
        return 0.0;
    }
}
