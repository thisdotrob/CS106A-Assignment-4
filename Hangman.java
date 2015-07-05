/*
 * File: Hangman.java
 * ------------------
 * This program plays the Hangman game from
 * Assignment #4.
 */

import acm.program.*;
import acm.util.*;

public class Hangman extends ConsoleProgram {
	
	/** number of guesses available to the user */
	private static final int GUESSES = 8;
	
	private HangmanCanvas canvas;
	
	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}
		
    public void run() {
    	
    	while(true) {
    		
    		// Prints welcome message.
    		println("Welcome to Hangman!");
    		
    		// Resets the hangman canvas.
    		canvas.reset();
    	
    		// Plays the game
    		play();
    	
    		// Ask user if they want to play again
    		String playAgain = readLine("Enter Y to play again: ");
    		if (playAgain.toUpperCase().equals("Y")) {
				println();
    			println();
    		}
    		else {
    			println("Thanks for playing!");
    			break;
    		}
    	}

	}

    private void play() {
    	// Picks a random word for the answer and creates a dash masked version of this, storing both as a local variable.
    	String answer = setAnswer();
    	String maskedAnswer = setMaskedAnswer(answer);
    	
    	// Creates a local variable for number of user guesses remaining.
    	int guessesLeft = GUESSES;
    	
    	while(true){
    	
    		// Prints the masked answer in its currently revealed state, and the number of guesses left.
    		println("The word now looks like this: " + maskedAnswer);
    		println("You have " + guessesLeft + " guesses left.");
    		
    		// Displays the masked answer on the canvas
    		canvas.displayWord(maskedAnswer);
    		
    		// Retrieves a guessed uppercase character from the user.
    		char userGuess = getUserGuess();
    		
    		// Reveals correctly guessed characters in the masked answer.
    		if( correctGuess(userGuess, answer) ) {
    			maskedAnswer = updateMaskedAnswer(userGuess, maskedAnswer, answer);
    			
    			// Prints the win message if the answer is completely revealed.
    			if(answerRevealed(maskedAnswer)) {
    				println("You guessed the word: " + answer);
    				canvas.displayWord(maskedAnswer);
    				break;
    			}
    		}
    		else {
    			// Prints the wrong guess message, and reduces guesses left by one.
    			guessesLeft--;
    			println("There are no " + userGuess + "'s in the word.");
    			
    			// Reflects the wrong guess on the canvas.
    			canvas.noteIncorrectGuess(userGuess);
    		
    			
    			// Prints the lose message if there are no guesses left.
    			if(guessesLeft == 0) {
    				println("You're completely hung. " + "The word was: " + answer);
    				break;
    			}
    		}    		
    	}
		
	}

	/** Returns true if the answer has been revealed by the user */
	private boolean answerRevealed(String maskedAnswer) {
		if (maskedAnswer.indexOf('-') == -1) {
			return true;
		}
		else return false;
	}

	/** Reveals any instances of the user guessed character in the masked answer */
	private String updateMaskedAnswer(char ch, String maskedAnswer,
			String answer) {
		String result = maskedAnswer;
		for (int i = 0; i < answer.length(); i++) {
			if (answer.charAt(i) == ch) {
				result = result.substring(0, i) + Character.toString(ch) + result.substring(i+1);
			}
		}
		return result;
	}

	/** Returns true if the user guessed character exists in the answer */
	private boolean correctGuess(char ch, String answer) {
		if (answer.indexOf(ch) == -1) {
			return false;	
		}
		else return true;
	}

	/** Prompts the user to enter a character and returns this providing it is a legal guess */
	private char getUserGuess() {
		String userString = readLine("Your guess: ");
		while (true) {
			if (userString.length() == 1
					&&
					Character.isUpperCase(Character.toUpperCase( userString.charAt(0) ))) {
				return Character.toUpperCase( userString.charAt(0) );
			}
			else {
				userString = readLine("Enter a single letter please:");
			}
		}
	}

	/** Chooses and returns the answer */
	private String setAnswer() {
		
		// creates new HangManLexicon object and stores the length of its lexicon.
		HangmanLexicon lex = new HangmanLexicon();
		int wordCount = lex.getWordCount();
		
		// creates new RandomGenerator object and uses it to pick a random number from 0 to the length of the lexicon.
		RandomGenerator rgen = RandomGenerator.getInstance();		
		int index = rgen.nextInt(0,wordCount-1);
		
		// sets answer equal to a random word in the lexicon.
		String answer = lex.getWord(index);
		
		return answer;
    }
    
	/** Returns a masked version of the answer */
    private String setMaskedAnswer(String answer) {
    	String maskedAnswer = "";
    	while (maskedAnswer.length() != answer.length()) {
    		maskedAnswer += "-";
    	}
    	return maskedAnswer;
    }
    
}
