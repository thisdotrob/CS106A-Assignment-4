/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Color;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	/** Resets the game canvas */
	public void reset() {
		removeAll();
		partNum = 0;
		incorrectGuesses.setLabel("");
		midPointX = getWidth() / 2;
		paddingFromTop = 0.01 * getHeight();
		drawScaffold();
	}

	/** draws the scaffold and rope */
	private void drawScaffold() {
		double x = midPointX - BEAM_LENGTH;
		double y = paddingFromTop;
		add(new GLine( x, y, midPointX, y ));
		add(new GLine( x, y, x, y + SCAFFOLD_HEIGHT ));
		add(new GLine( midPointX, y, midPointX, y + ROPE_LENGTH ));
	}

	/**
	 * Updates the word on the screen to correspond to the current
	 * state of the game.  The argument string shows what letters have
	 * been guessed so far; unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {	
		answer.setLabel(word);
		answer.setFont("SansSerif-30");
		answer.setColor(Color.GREEN);
		double y = paddingFromTop + SCAFFOLD_HEIGHT + answer.getAscent() + 40;
		double x = midPointX - BEAM_LENGTH;
		answer.setLocation(x,y);
		add(answer);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the
	 * user.  Calling this method causes the next body part to appear
	 * on the scaffold and adds the letter to the list of incorrect
	 * guesses that appears at the bottom of the window.	
	 */
	public void noteIncorrectGuess(char ch) {
		drawHangmanPart(partNum++);
		
		String currentLabel = incorrectGuesses.getLabel();
		
		if (currentLabel.indexOf(ch) == -1) {
			incorrectGuesses.setLabel(currentLabel + Character.toString(ch));
			incorrectGuesses.setFont("SansSerif-20");
			incorrectGuesses.setColor(Color.RED);
			double y = paddingFromTop + SCAFFOLD_HEIGHT + answer.getAscent() + incorrectGuesses.getAscent() + 40;
			double x = midPointX - BEAM_LENGTH;
			incorrectGuesses.setLocation(x,y);
			add(incorrectGuesses);
		}
	}

	private void drawHangmanPart(int partNum) {
		switch (partNum) {
			case 0: drawHead();			break;
			case 1: drawBody();			break;
			case 2: drawArm(LEFT);		break;
			case 3: drawArm(RIGHT);		break;
			case 4: drawLeg(LEFT);		break;
			case 5: drawLeg(RIGHT);		break;
			case 6: drawFoot(LEFT);		break;
			case 7: drawFoot(RIGHT);	break;
			default:					break;
		}
	}

	private void drawFoot(int rightOrLeft) {
		double x = midPointX + (rightOrLeft * HIP_WIDTH);
		double x2 = x + (rightOrLeft * FOOT_LENGTH);
		double y = paddingFromTop + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH + LEG_LENGTH;
		add(new GLine(x,y,x2,y));
	}

	private void drawLeg(int rightOrLeft) {
		double x = midPointX + (rightOrLeft * HIP_WIDTH);
		double y = paddingFromTop + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH;
		add(new GLine(x,y,midPointX,y));
		add(new GLine(x,y,x,y + LEG_LENGTH));
	}

	private void drawArm(int rightOrLeft) {
		double x = midPointX + (rightOrLeft * UPPER_ARM_LENGTH);
		double y = paddingFromTop + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD;
		add(new GLine(x,y,midPointX,y));
		add(new GLine(x,y,x,y + LOWER_ARM_LENGTH));
	}

	private void drawBody() {
		double y = paddingFromTop + ROPE_LENGTH + HEAD_RADIUS * 2;
		add(new GLine(midPointX, y, midPointX, y + BODY_LENGTH));
	}

	private void drawHead() {
		double x = midPointX - HEAD_RADIUS;
		double y = paddingFromTop + ROPE_LENGTH;
		add(new GOval(x,y,HEAD_RADIUS*2,HEAD_RADIUS*2));
	}

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private static final int LEFT = -1;
	private static final int RIGHT = 1;
	
	// Instance variable for the answer and guess labels.
	private GLabel answer = new GLabel("");
	private GLabel incorrectGuesses = new GLabel("");
		
	// Instance variable for the number of hangman parts drawn on the canvas.
	private int partNum;
	
	private double midPointX;
	private double paddingFromTop;

}
