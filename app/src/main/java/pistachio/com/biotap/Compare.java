/* Compare.java                          @author(Cameron)          */
/* Several methods in class used for getting dissimilarity score   */
/*  between 2 ArrayLists of Taps (sequences). Also contains methods*/
/*  for taking in several sequences and averaging them to return a */
/*  sequence. Also has methods that calulate the standard deviation*/
/*  between a given number of sequences, which returns modified    */
/*  values in a Tap object for convience of passing data.          */
/* @version(11 Nov 2015) Tested AlgoTest.Java, returns a dis score.*/
package pistachio.com.biotap;


import java.util.ArrayList;

public class Compare {

    /*Class used for comparing 2 tap sequences. Returns a score by*/
    /* comparing the master and attempted sequence.*/
    static public double dissimilarityScore(ArrayList<Tap> master,
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
    static private double tapFormula(Tap master, Tap attempt, Tap sd) {
        double total = 0.0;
        total += longTapForm(master.getTime(),attempt.getTime(), sd.getTime());
        total += longTapForm(master.getDuration(),
                             attempt.getDuration(),
                             sd.getDuration());
        total += intTapForm(master.getX(),
                            attempt.getX(),
                            sd.getX());
        total += intTapForm(master.getY(),
                            attempt.getY(),
                            sd.getY());
        
        return total;
    }

    
    
    
    /*Takes an array of Tap sequences and comes up with a standard*/
    /* deviation tap sequence.*/
    static public ArrayList<Tap> standDeviation(int trials,
                                         ArrayList<Tap>[] sequences) {
        /*Sequence to be returned.*/
        ArrayList<Tap> sd = new ArrayList<>();
        /*Number of taps in each sequence.*/
        int numTaps = sequences[0].size();
        /*System.out.print("TEST numTaps:" + sequences[0].size() +"\n");*/
        /*System.out.print("TEST trials:" + trials +"\n");*/
        /*Loop checks to make sure all sequences are same length.*/
        for (int i = 0; i < trials; i++) {
            /*System.out.print("TEST i1:" + i +"\n");*/
            if (numTaps != sequences[i].size()) {
                /*THROW AN ERROR INSTEAD OF RETURNING EMPTY LIST*/
                /*System.out.print("TEST i2:" + i +"\n");*/
                /*System.out.print("TEST seq[" + i + "]: " + sequences[i].toString() + "\n");*/
                return sd;
            }
        }
        
        /*Mean of the sequences.*/
        ArrayList<Tap> mean = Compare.meanSeq(trials, sequences);
        /*System.out.print("TEST mean size: " + mean.size() + "\n");*/
        
        /*Calculates standard deviation.*/
        /*Looping through each tap in all sequences.*/
        for (int a = 0; a < numTaps; a++) {
            /*Sum of the difference of each trial element minus*/
            /* the mean element, squared.*/
            long timeSum = 0;
            long durSum = 0;
            int xSum = 0;
            int ySum = 0;
            /*Sums the element difference square in each sequence.*/
            for (int b = 0; b < trials; b++) {
                /*System.out.print("TEST a: " + a + " b: " + b + "\n");*/
                timeSum += ((sequences[b].get(a).getTime() -
                             mean.get(a).getTime()) *
                            (sequences[b].get(a).getTime() -
                             mean.get(a).getTime()));
                durSum  += ((sequences[b].get(a).getDuration() -
                             mean.get(a).getDuration()) *
                            (sequences[b].get(a).getDuration() -
                             mean.get(a).getDuration()));
                xSum    += ((sequences[b].get(a).getX() -
                             mean.get(a).getX()) *
                            (sequences[b].get(a).getX() -
                             mean.get(a).getX()));
                ySum    += ((sequences[b].get(a).getY() -
                             mean.get(a).getY()) *
                            (sequences[b].get(a).getY() -
                             mean.get(a).getY()));
            }
            /*Standard deviations formula in double form.*/
            double time = (Math.sqrt(timeSum))/trials;
            double dur = (Math.sqrt(durSum))/trials;
            double x = (Math.sqrt(xSum))/trials;
            double y = (Math.sqrt(ySum))/trials;
            /*Cast variables so they can be stored in a Tap object.*/
            /* Creates Tap and put into stand dev sequence.*/
            Tap castedTap = new Tap(dblToLong(time), dblToLong(dur),
                                    dblToInt(x), dblToInt(y));
            sd.add(castedTap);
        }
        /*Send back standard deviation array.*/
        return sd;
    }
    
    /*Returns an array that is the mean of sequences.*/
    public static ArrayList<Tap> meanSeq(int trial, ArrayList<Tap>[] seq) {
        ArrayList<Tap> average = new ArrayList<>();
        int numTaps = seq[0].size();
        /*Loops through each Tap in all sequences.*/
        for (int k = 0; k < numTaps; k++) {
            /*Sum of each element of a Tap at kth Tap in sequence.*/
            long timeSum = 0;
            long durSum = 0;
            int xSum = 0;
            int ySum = 0;
            /*Creates a running sum of all the elements of the kth*/
            /* tap in a sequence.*/
            for (int j = 0; j < trial; j++) {
                /*System.out.print("TEST j: " + j + " k: " + k + "\n");*/
                timeSum += seq[j].get(k).getTime();
                /*System.out.print("TEST timeSum: " + timeSum + "\n");*/
                durSum  += seq[j].get(k).getDuration();
                /*System.out.print("TEST durSum: " + durSum + "\n");*/
                xSum    += seq[j].get(k).getX();
                /*System.out.print("TEST xSum: " + xSum + "\n");*/
                ySum    += seq[j].get(k).getY();
                /*System.out.print("TEST ySum: " + ySum + "\n");*/
                /*System.out.print("TEST seq[" + j + "]: " + seq[j].toString() + "\n");*/
            }
            /*Create an average tap, add it to the mean sequence.*/
            Tap ave = new Tap((long)(timeSum/trial),
                              (long)(durSum/trial),
                              (xSum/trial),
                              (ySum/trial));
            average.add(ave);
        }
        /*Return average.*/
        return average;
    }
    
    
    /*Dissimilarity score formula broken down by long and int.*/
    /* Differentiated because of the way stand dev is stored in*/
    /* Tap object. If statement checks for division by 0.*/
    static private double longTapForm(long mast, long att, long sd) {
        double score = 0.0;
        if (sd == 0) {
            return score;
        }
        score = (double)(Math.abs(att-mast)/longToDbl(sd));
        return score;
    }    
    static private double intTapForm(int mast, int att, int sd) {
        double score = 0.0;
        if (sd == 0) {
            return score;
        }
        score = (double)(Math.abs(att-mast)/intToDbl(sd));
        return score;
    }
    /*Methods used in converting back and forth to doubles for*/
    /* storage of standard deviations in a Tap object. The standard*/
    /* deviation is multiplied by 1000 to be put into the Tap, and*/
    /* divided by 1000 when being extracted from a Tap to maintain*/
    /* precision.*/
    static private long dblToLong(double d) {
        return (long)(d*1000);
    }
    static private int dblToInt(double d) {
        return (int)(d*1000);
    }
    static private double longToDbl(long l) {
        return (double)l/1000.0;
    }
    static private double intToDbl(int i) {
        return (double)i/1000.0;
    }
}
