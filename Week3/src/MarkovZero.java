
/**
 * Write a description of class MarkovZero here.
 * 
 * @author Duke Software
 * @version 1.0
 */

import java.util.Random;

public class MarkovZero {
    private String myText;
	private Random myRandom;
	
	public MarkovZero() {
		myRandom = new Random();
		myText = "";  // no frickin' nulls
	}
	
	public void setRandom(int seed){
		myRandom = new Random(seed);
	}
	
	public void setTraining(String s){
		if (s != null && ! s.isEmpty()) myText = s.trim();
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
