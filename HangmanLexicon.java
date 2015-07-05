/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import acm.util.*;

public class HangmanLexicon {
	
	/** Default constructor */
	public HangmanLexicon() {
		BufferedReader rd = createBufferedReader();
		lex = populateNewArrayListFromFile(rd);
	}

	
	/** Creates a new ArrayList<String> object and populates from the provided BufferedReader */
	private ArrayList<String> populateNewArrayListFromFile(BufferedReader rd) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			while (true) {
				String line = rd.readLine();
				if (line == null) break;
				list.add(line);				
			}
			rd.close();
			return list;
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
	}

	/** Creates a new BufferedReader object from the HangmanLexicon.txt file */
	private BufferedReader createBufferedReader() {
		try {
			BufferedReader rd = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			return rd;
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}		
	}
	
	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return lex.size();
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {
		return lex.get(index);
	}
	
	// private instance variable to allow methods access to the lexicon
	private ArrayList<String> lex;
}
