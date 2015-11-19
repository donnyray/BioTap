/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tapaverager;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author travismoretz
 */
public abstract class  TapAverager implements TapInterface {

    static ArrayList<String> one = new ArrayList<>();
        
    static ArrayList<String> two = new ArrayList<>();
   
    static ArrayList<String> three = new ArrayList<>();
    
    static ArrayList<TapInterface> taps = new ArrayList<>();
    
    static TapInterface currentTap;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    
   /**
    * takes the first three lines from file, writes them to new file.
    * also fills the three array list used for averaging
    */ 

            String file1 = "/Users/travismoretz/Desktop/avgPassKey.txt";
            String file2 = "/Users/travismoretz/Desktop/outputFile.txt";

            FileReader inFile = null;
            FileWriter outFile = null;
            
            int count = 0;


            try {
                BufferedReader buffd = new BufferedReader(new FileReader(file1));
                BufferedWriter outfd = new BufferedWriter(new FileWriter(file2));
                String line;
                while ( (line = buffd.readLine()) != null && count != 3 ) {
                    System.out.println(line);
                    outfd.write(line);
                    outfd.newLine();
                    if(count == 0) {
                       parseSequence(line, one); 
                    }
                    if(count == 1) {
                        parseSequence(line, two);
                    }
                    if(count == 2) {
                        parseSequence(line, three);
                    }
                    
                    count++;
                }
                buffd.close();
                outfd.close();
            }
            catch (IOException e) {
                System.out.println("**Error reading file**");
                e.printStackTrace();
            }
 
        System.out.println("******************************************");
        
        parseTapAndAverage(one, two, three, file2);
            
    
    }

    /**
     * parse the Sequence into individual elements
     */
    static void parseSequence(String taps, ArrayList list) {
        
       taps = taps.substring(1, taps.length() -1); 
       String[] tokens =  taps.split(",");
       
        for (String token : tokens) {
            list.add(token);
        }
    }

    /**
     * average each tap sequence together, put into a tap and write new averaged
     * tap sequence to file
     * 
     * @param list1
     * @param list2
     * @param list3
     * @param file 
     */
    static void parseTapAndAverage(ArrayList<String> list1, ArrayList<String> list2,
                                                    ArrayList<String> list3, String file) {
        long timestamp = 0; 
        long duration = 0;
        int x = 0;
        int y = 0;
        
        String errTaps = "Tap Sequence do not have same number of Taps";
        
        /* open file for append */
        
        try(BufferedWriter avg = new BufferedWriter(new FileWriter(file, true))) {
        
            /* if the Tap sequences do not all have the same length they can not be
            averaged print error message to file */
            
            if( list1.size() != list2.size() && list2.size() != list3.size()) {
                System.out.println(errTaps);
                avg.write(errTaps);
            }
            
            for (int i = 0; i < list1.size(); i++) {
                
                String temp1, temp2, temp3;
                
                temp1 = list1.get(i);
                temp2 = list2.get(i);
                temp3 = list3.get(i);
                String[] instance1 = temp1.split("/");
                String[] instance2 = temp2.split("/");
                String[] instance3 = temp3.split("/");
                
                 /* adds the three taps together puts them into a tap object */
                 
                try {
                    timestamp = ((Long.parseLong(instance1[0]) + Long.parseLong(instance2[0]) 
                                + Long.parseLong(instance3[0])) / 3);
                    duration = ((Long.parseLong(instance1[1]) + Long.parseLong(instance2[1])
                                + Long.parseLong(instance3[1])) / 3);
                    x = ((Integer.parseInt(instance1[2]) + Integer.parseInt(instance1[2]) 
                         + Integer.parseInt(instance1[2])) / 3);
                    y = ((Integer.parseInt(instance1[3]) + Integer.parseInt(instance2[3]) 
                         + Integer.parseInt(instance3[3])) / 3);

                    System.out.println(timestamp);
                    System.out.println(duration);
                    System.out.println(x);
                    System.out.println(y);
                    
                    currentTap = new Tap(timestamp, duration, x, y);
                    
                    taps.add(currentTap);
                    
                    
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
            
            avg.write(taps.toString() + "\n");
            avg.close();
            
        } catch (IOException e) {
            System.out.println("**Output File Error**");
            e.printStackTrace();
        }        
    }
}

