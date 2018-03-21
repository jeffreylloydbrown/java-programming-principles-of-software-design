
/**
 * Write a description of class MarkovWordOne here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MarkovWordOne implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    
    public MarkovWordOne() {
        myRandom = new Random();
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
		if (text != null && ! text.isEmpty())
    		myText = text.split("\\s+");
	}

    public String toString() { return "MarkovWordOne"; }
	
	public String getRandomText(int numWords){
		StringBuilder sb = new StringBuilder();
		int index = myRandom.nextInt(myText.length-1);  // random word to start with
		String key = myText[index];
		sb.append(key);
		sb.append(" ");
		for(int k=0; k < numWords-1; k++){
		    ArrayList<String> follows = getFollows(key);
		    //System.out.println(key+": "+follows);
		    if (follows.size() == 0) {
		        break;
		    }
			index = myRandom.nextInt(follows.size());
			String next = follows.get(index);
			sb.append(next);
			sb.append(" ");
			key = next;
		}
		
		return sb.toString().trim();
	}

	// the private method indexOf that has three parameters, a String array named words, a String
	// named target, and an integer named start. This method starts looking at the start position
	// and returns the first index location in words that matches target. If no word is found,
	// then this method returns -1. The signature for this method should be:
	private int indexOf (String[] words, String target, int start) {
		for (int k = start; k < words.length; k++)
			if (words[k].equals(target))
				return k;
		return -1;
	}

	// a public void method named testIndexOf that has no parameters. This method is only for
	// testing the indexOf method. This method should create a simple String array with the words
	// “this is just a test yes this is a simple test” then look for the words: “this” starting
	// at 0, “this” starting at 3, “frog” starting at 0, “frog” starting at 5, “simple” starting
	// at 2 and “test” starting at 5.
	public void testIndexOf() {
		String[] words = "this is just a test yes this is a simple test".split("\\s+");
		System.out.println("should be 0: "+indexOf(words, "this", 0));
		System.out.println("should be 6: "+indexOf(words, "this", 3));
		System.out.println("should be -1: "+indexOf(words, "frog", 0));
		System.out.println("should be -1: "+indexOf(words, "frog", 5));
		System.out.println("should be 9: "+indexOf(words, "simple", 2));
		System.out.println("should be 10: "+indexOf(words, "test", 5));
	}

	// Complete the method named getFollows that has been started for you with String
	// parameter key. This method should return an ArrayList of all the single words
	// that immediately follow the key in the training text. This method should call
	// the indexOf method.
	private ArrayList<String> getFollows(String key) {
	    ArrayList<String> follows = new ArrayList<String>();
        int pos = 0;
        while (pos < myText.length) {
            int start = indexOf(myText, key, pos);
            if (start == -1)
                break;
            if (start + 1 >= myText.length)
                break;
            follows.add(myText[start+1]);
            pos = start+1;
        }
	    return follows;
    }

}  // MarkovWordOne
