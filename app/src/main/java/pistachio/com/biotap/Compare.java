/* Compare.java                          @author(Cameron)          */
/* Several methods in class used for getting dissimilarity score   */
/*  between 2 ArrayLists of Taps (sequences). Also contains methods*/
/*  for taking in several sequences and averaging them to return a */
/*  sequence. Also has methods that calulate the standard deviation*/
/*  between a given number of sequences, which returns modified    */
/*  values in a Tap object for convience of passing data.          */
package pistachio.com.biotap;

import java.util.ArrayList;
import java.util.Math;

public class Compare {

    /*Class used for comparing 2 tap sequences. Returns a score by*/
    /* comparing the master and attempted sequence.*/
    public double dissimilarityScore(ArrayList<Tap> master,
                                     ArrayList<Tap> attempt,
                                     ArrayList<Tap> standDev) {
        /*Checks to make sure sequences are the same size. If not*/
        /* the same size, returns a negative to indicate non-*/
        /* acceptance, since all scores returned are positive.*/
        if (master.size() != attempt.size()) {
            return -1.00;
        }

        /*Used to sum all the magnitudes of each comparison.*/
        double magSum = 0.0;
        /*Goes through each element in sequences, adds to the sum.*/
        for (int i = 0; i < master.size(); i++) {
            magSum =+ tapFormula(master.get(i),
                                 attempt.get(i),
                                 standDev.get(i));
        }
        /*Return score.*/
        return magSum;
    }

    
    
    /*Breaks down Taps by their elements and performs formula.*/
    private double tapFormula(Tap master, Tap attempt, Tap sd) {
        double total = 0.0;
        total += longTapForm(master.getTime(),
                             attempt.getTime(),
                             sd.getTime());
        total += longTapForm(master.getDur(),
                             attempt.getDur()
                             sd.getDur());
        total += intTapForm(master.getX(),
                            attempt.getX(),
                            sd.getX());
        total += intTapForm(master.getY(),
                            attempt.getY(),
                            sd.getY());
        }
        return total;
    }

    
    
    
    /*Takes an array of Tap sequences and comes up with a standard*/
    /* deviation tap sequence.*/
    public ArrayList<Tap> standDeviation(int trials,
                                         ArrayList<Tap>[] sequences) {
        /*Sequence to be returned.*/
        ArrayList<Tap> sd = new ArrayList<Tap>;
        /*Number of taps in each sequence.*/
        int numTaps = sequences[0].size();
        /*Loop checks to make sure all sequences are same length.*/
        for (int i = 1; i < trials; i++) {
            if (numTaps != sequences[trials].size()) {
                /*THROW AN ERROR INSTEAD OF RETURNING EMPTY LIST*/
                return sd;
            }
        }
        /*Mean of the sequences.*/
        ArrayList<Tap> mean = Compare.meanSeq(trails, sequences);
        
        /*Calculates standard deviation.*/
        /*Looping through each tap in all sequences.*/
        for (int a = 0; a < numTaps; a++) {
            /*Sum of the difference of each trial element minus*/
            /* the mean element, squared.*/
            long timeSum = 0.0;
            long durSum = 0.0;
            int xSum = 0.0;
            int ySum = 0.0;
            /*Sums the element difference square in each sequence.*/
            for (int b = 0; b < trials; b++) {
                timeSum += ((sequence[b].get(a).getTime() -
                             mean.get(a).getTime()) *
                            (sequence[b].get(a).getTime() -
                             mean.get(a).getTime()));
                durSum  += ((sequence[b].get(a).getDur() -
                             mean.get(a).getDur()) *
                            (sequence[b].get(a).getDur() -
                             mean.get(a).getDur()));
                xSum    += ((sequence[b].get(a).getX() -
                             mean.get(a).getX()) *
                            (sequence[b].get(a).getX() -
                             mean.get(a).getX()));
                ySum    += ((sequence[b].get(a).getY() -
                             mean.get(a).getY()) *
                            (sequence[b].get(a).getY() -
                             mean.get(a).getY()));
            }
            /*Standard deviations formula in double form.*/
            double time = (Math.sqrt(timeSum))/trials;
            double dur = (Math.sqrt(durTime))/trials;
            double x = (Math.sqrt(xSum))/trials;
            double y = (Math.sqrt(ySum))/trials;
            /*Cast variables so they can be stored in a Tap object.*/
            /* Creates Tap and put into stand dev sequence.*/
            Tap castedTap = new Tap(dblToLong(time), dblToLong(dur),
                                    dblToInt(x), dlbToInt(y));
            sd.add(castedTap);
        }
        /*Send back standard deviation array.*/
        return sd;
    }
    
    /*Returns an array that is the mean of sequences.*/
    public ArrayList<Tap> meanSeq(int trial, ArrayList<Tap>[] seq) {
        ArrayList<Tap> mean = new ArrayList<Tap>;
        /*Loops through each Tap in all sequences.*/
        for (int k = 0; k < numTaps; k++) {
            /*Sum of each element of a Tap at kth Tap in sequence.*/
            long timeSum = 0;
            long durSum = 0;
            int xSum = 0;
            int ySum = 0;
            /*Creates a running sum of all the elements of the kth*/
            /* tap in a sequence.*/
            for (int j = 0; j < trials; j++) {
                timeSum += sequences[j].get(k).getTime();
                durSum  += sequences[j].get(k).getDuration();
                xSum    += sequences[j].get(k).getX();
                ySum    += sequences[j].get(k).getY();
            }
            /*Create an average tap, add it to the mean sequence.*/
            Tap ave = new Tap((long)(timeSum/trials),
                              (long)(durSum/trials),
                              (xSum/trials),
                              (ySum/trials));
            mean.add(ave);
        }
        /*Return average.*/
        return mean;
    }
    
    
    /*Dissimilarity score formula broken down by long and int.*/
    /* Differentiated because of the way stand dev is stored in*/
    /* Tap object. If statement checks for division by 0.*/
    private double longTapForm(long mast, long att, long sd) {
        double score = 0.0;
        if (sd == 0) {
            return score;
        }
        score = (double)(Math.abs(att-mast)/longToDbl(sd));
        return score;
    }    
    private double intTapForm(int mast, int att, int sd) {
        double score = 0.0;
        if (sd == 0) {
            return score;
        }
        score = (double)(Math.abs(att-mast)/intToDble(sd));
        return score;
    }
    /*Methods used in converting back and forth to doubles for*/
    /* storage of standard deviations in a Tap object. The standard*/
    /* deviation is multiplied by 1000 to be put into the Tap, and*/
    /* divided by 1000 when being extracted from a Tap to maintain*/
    /* precision.*/
    private long dblToLong(double d) {
        return (long)(d*1000);
    }
    private int dblToInt(double d) {
        return (int)(d*1000);
    }
    private double longToDbl(long l) {
        return (double)l/1000.0;
    }
    private double intToDbl(int i) {
        return (double)i/1000.0;
    }
}
