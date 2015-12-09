package main.java.edu.ccsu.pistachio;

import java.util.ArrayList;
import java.util.List;

import main.java.edu.ccsu.pistachio.interfaces.TapInterface;

/**
 * Several methods in class used for getting dissimilarity score 
 * between two lists of TapInterfaces. Contains method for taking
 * in several sequences and averaging them to return a
 * sequence. Also has methods that calculate the standard deviation
 * between a given number of sequences, which returns modified 
 * values in a TapInterface object for convenience of passing data.
 * 
 * @author Cameron
 *
 */
public class Compare {

	/**
	 * Compare two TapInterface sequences.
	 * 
	 * @param master
	 * @param attempt
	 * @param standDev
	 * @return
	 */
    static public double dissimilarityScore(List<TapInterface> master, List<TapInterface> attempt, List<TapInterface> standDev) {

        if (master.size() != attempt.size()) {
            return -1.00;
        }

        double magSum = 0.0;
  
        for (int i = 0; i < master.size(); i++) {
            magSum =+ TapInterfaceFormula(master.get(i), attempt.get(i), standDev.get(i));
        }

        return magSum;
    }


    /**
     * Breaks down TapInterfaces by their elements and performs formula.
     * 
     * @param master
     * @param attempt
     * @param sd
     * @return
     */
    static private double TapInterfaceFormula(TapInterface master, TapInterface attempt, TapInterface sd) {
        double total = 0.0;
        total += longTapForm(master.getTime(), attempt.getTime(), sd.getTime());
        total += longTapForm(master.getDuration(), attempt.getDuration(), sd.getDuration());
//        total += intTapForm(master.getX(), attempt.getX(), sd.getX());
//        total += intTapForm(master.getY(), attempt.getY(), sd.getY());

        return total;
    }


    /**
     * Takes an array of TapInterface sequences and comes up with a standard
     * deviation TapInterface sequence.
     * 
     * @param sequences
     * @return
     */
    static public List<TapInterface> standDeviation(List<List<TapInterface>> sequences) {

    	int trials = sequences.size();
        ArrayList<TapInterface> sd = new ArrayList<>();
        int numTapInterfaces = sequences.get(0).size();

        for (int i = 0; i < trials; i++) {
            if (numTapInterfaces != sequences.get(i).size()) {
                return sd;
            }
        }
        
        List<TapInterface> mean = Compare.meanSeq(sequences);
        
        long timeSum = 0;
        long durSum = 0;
        int xSum = 0;
        int ySum = 0;
        double time, dur, x, y;

        for (int a = 0; a < numTapInterfaces; a++) {

            for (int b = 0; b < trials; b++) {
                timeSum += ((sequences.get(b).get(a).getTime() -
                             mean.get(a).getTime()) *
                            (sequences.get(b).get(a).getTime() -
                             mean.get(a).getTime()));
                durSum  += ((sequences.get(b).get(a).getDuration() -
                             mean.get(a).getDuration()) *
                            (sequences.get(b).get(a).getDuration() -
                             mean.get(a).getDuration()));
                xSum    += ((sequences.get(b).get(a).getX() -
                             mean.get(a).getX()) *
                            (sequences.get(b).get(a).getX() -
                             mean.get(a).getX()));
                ySum    += ((sequences.get(b).get(a).getY() -
                             mean.get(a).getY()) *
                            (sequences.get(b).get(a).getY() -
                             mean.get(a).getY()));
            }
 
            time = (Math.sqrt(timeSum)) / trials;
            dur = (Math.sqrt(durSum)) / trials;
            x = (Math.sqrt(xSum)) / trials;
            y = (Math.sqrt(ySum)) / trials;
            TapInterface castedTapInterface = new Tap(dblToLong(time), dblToLong(dur), dblToInt(x), dblToInt(y));
            sd.add(castedTapInterface);
        }
        
        return sd;
    }

    /**
     * Get the mean of the sequences.
     * 
     * @param seq
     * @return
     */
    public static List<TapInterface> meanSeq(List<List<TapInterface>> seq) {
        ArrayList<TapInterface> average = new ArrayList<>();
        int trial = seq.size();
        int numTap = seq.get(0).size();

        for (int k = 0; k < numTap; k++) {
            long timeSum = 0;
            long durSum = 0;
            int xSum = 0;
            int ySum = 0;
            for (int j = 0; j < trial; j++) {
                timeSum += seq.get(j).get(k).getTime();
                durSum  += seq.get(j).get(k).getDuration();
                xSum    += seq.get(j).get(k).getX();
                ySum    += seq.get(j).get(k).getY();
            }
            TapInterface ave = new Tap(timeSum/trial, durSum/trial, xSum/trial, ySum/trial);
            average.add(ave);
        }
        
        return average;
    }
    
    /**
     * Converts to long tap form.
     *
     * @param mast
     * @param att
     * @param sd
     * @return
     */
    static private double longTapForm(long mast, long att, long sd) {
        double score = 0.0;
        if (sd == 0) {
        	return score;
        }
        score = (double)(Math.abs(att-mast)/longToDbl(sd));
        
        return score;
    }   
    
    /**
     * Converts to integer tap form.
     * 
     * @param mast
     * @param att
     * @param sd
     * @return
     */
    static private double intTapForm(int mast, int att, int sd) {
        double score = 0.0;
        if (sd == 0) {
            return score;
        }
        score = (double)(Math.abs(att-mast)/intToDbl(sd));
        
        return score;
    }
    
    /**
     * Convert double to long.
     * 
     * @param d
     * @return
     */
    static private long dblToLong(double d) {
        return (long)(d*1000);
    }
    
    /**
     * Convert double to integer.
     * 
     * @param d
     * @return
     */
    static private int dblToInt(double d) {
        return (int)(d*1000);
    }
    
    /**
     * Convert long to double.
     * 
     * @param l
     * @return
     */
    static private double longToDbl(long l) {
        return (double)l/1000.0;
    }
    
    /**
     * Convert integer to double.
     * 
     * @param i
     * @return
     */
    static private double intToDbl(int i) {
        return (double)i/1000.0;
    }
}