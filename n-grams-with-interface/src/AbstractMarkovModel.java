
/**
 * Abstract class AbstractMarkovModel - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */

import java.util.*;

public abstract class AbstractMarkovModel implements IMarkovModel {
    protected int markovOrder;
    protected String myText;
    protected Random myRandom;
    
    public void setTraining(String s) {
        if (s != null && !s.isEmpty())
            myText = s.trim();
    }

    public void setRandom(int seed){
        myRandom = new Random(seed);
    }
 
    abstract public String getRandomText(int numChars);

    public String toString() { return "MarkovModel of order "+markovOrder; }

    // For every occurence of the string `key` in `myText`, add the character after
    // it to `follows`.  Do it as a string though to make future generalization easier.
    protected ArrayList<String> getFollows(String key) {
        ArrayList<String> follows = new ArrayList<String>();
        int pos = 0;
        while (pos < myText.length()) {
            int start = myText.indexOf(key, pos);
            if (start == -1)
                break;
            if (start + key.length()+1 > myText.length())
                break;
            String next = myText.substring(start+key.length(), start+key.length()+1);
            follows.add(next);
            pos = start+key.length();
        }

        return follows;
    }

}
