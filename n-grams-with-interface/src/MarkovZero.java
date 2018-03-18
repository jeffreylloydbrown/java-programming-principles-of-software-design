
/**
 * Write a description of class MarkovZero here.
 * 
 * @author Duke Software
 * @version 1.0
 */

import java.util.Random;

public class MarkovZero extends AbstractMarkovModel {

	public MarkovZero() {
		markovOrder = 0;
		myRandom = new Random();
		myText = "";  // no frickin' nulls
	}

	public String getRandomText(int numChars){

		StringBuilder sb = new StringBuilder();
		for(int k=0; k < numChars; k++){
			int index = myRandom.nextInt(myText.length());
			sb.append(myText.charAt(index));
		}
		
		return sb.toString();
	}
}
