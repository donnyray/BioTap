package main.java.edu.ccsu.pistachio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import main.java.edu.ccsu.pistachio.interfaces.TapInterface;

public class CompareTaps {
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	
	/**
	 * Main entry point.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		

		
		if (args.length < 3) {
			System.out.println(ANSI_RED + "Invalid argument count.");
			System.out.println("java -jar [file].jar [input] [passing score] [averaging count]");
			return;
		}
		
		double acceptableScore = Double.parseDouble(args[1]);
		int averageCount = Integer.parseInt(args[2]);
		
		
		
		String[] lines = {};
		
		try {
			lines = readFile(new File(args[0]));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		List<List<TapInterface>> collection = mapToCollection(lines);
		
		if (averageCount + 1 > collection.size()) {
			System.out.println(ANSI_RED + "Error: not enough inputs to average " + averageCount + " sequences.");
			return;
		}

		List<List<TapInterface>> keys = new ArrayList<>(collection.subList(0, averageCount));
		collection = new ArrayList<>(collection.subList(averageCount, collection.size()));
		
		List<TapInterface> master = Compare.meanSeq(keys);
		List<TapInterface> deviation = Compare.standDeviation(keys);
		
		System.out.println(
			ANSI_CYAN
			+ "\n"
			+ "Compare the similarity between two sequences.\n"
			+ "The more similar, the closer to zero.\n"
			+ ANSI_GREEN
			+ "Anything under " + acceptableScore + " is a PASS.\n"
		);
		
		
		for (int i = 0; i < collection.size(); i++) {
			
			double score = Compare.dissimilarityScore(master, collection.get(i), deviation);
			
			System.out.print(ANSI_RESET + "Attempt #" + (i + 1) + ": " + ANSI_YELLOW + String.format("%.5f", score) + "\t");
		
			System.out.println(score < acceptableScore && score != -1.0 ? ANSI_GREEN + "PASS" : ANSI_RED + "FAIL");
		
		}
		
		System.out.println();
		
	}
	
	
	/**
	 * Read file.
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private static String[] readFile(File file) throws Exception {
		ArrayList<String> lines = new ArrayList<>();
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		 
		String line = null;
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
	 
		br.close();
		
		
		return lines.toArray(new String[lines.size()]);
	}

	/**
	 * Map strings to a collection of sequences.
	 *
	 * @param lines
	 * @return
	 */
	private static List<List<TapInterface>> mapToCollection(String[] lines) {
		List<List<TapInterface>> collection = new ArrayList<>();

		for (String line : lines) {
			collection.add(mapToSequence(line));
		}

		return collection;
	}

	/**
	 * Map a string to sequence.
	 * 
	 * @param line
	 * @return
	 */
	private static List<TapInterface> mapToSequence(String line) {
		ArrayList<TapInterface> sequence = new ArrayList<>();
		
		String[] taps = line.split(",");
		String[] unparsedTap;
		
		for (String tap : taps) {
			unparsedTap = tap.split("/");
			
			sequence.add(
					new Tap(
							Long.parseLong(unparsedTap[0]), 
							Long.parseLong(unparsedTap[1]),
							Integer.parseInt(unparsedTap[2]), 
							Integer.parseInt(unparsedTap[3])
					)
			);
		}
		
		return sequence;
	}

}
