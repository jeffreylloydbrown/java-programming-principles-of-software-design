
/**
 * Write a description of class MarkovWord here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.*;

public class MarkovWord implements IMarkovModel {
    private int myOrder;
    private String[] myText;
    private Random myRandom;

    public MarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String text){
        if (text != null && ! text.isEmpty())
            myText = text.split("\\s+");
    }

    public String toString() { return "MarkovWord of order "+myOrder; }

    // The getRandomText method has one integer parameter named numWords. This method
    // generates and returns random text that has numWords words. This class generates
    // each word by randomly choosing a word from the training text that follows the
    // current word(s) in the training text. When you copied the body of MarkovWordOne
    // into the MarkovWord class, you copied this method from MarkovWordOne. Much of
    // the code from the copied method will still be correct for MarkovWord, but you
    // will need to make a few changes so that it works for any order (not just order
    // one), and uses WordGram objects. You may want to use the shiftAdd method you
    // wrote in WordGram.
    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-myOrder);
        String[] initialKeys = new String[myOrder];
        for (int k=0; k < myOrder; k++) {
            initialKeys[k] = myText[index+k];
            sb.append(initialKeys[k]).append(" ");
        }
        WordGram key = new WordGram(initialKeys, 0, initialKeys.length);

        for(int k=0; k < numWords-myOrder; k++){
            ArrayList<String> follows = getFollows(key);
            //System.out.println(key+": "+follows);
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key = key.shiftAdd(next);
        }

        return sb.toString().trim();
    }

    // the private indexOf method has three parameters, a String array of all the words in the
    // training text named words, a WordGram named target, and an integer named start indicating
    // where to start looking for a WordGram match in words. This method should return the first
    // position from start that has words in the array words that match the WordGram target.
    // If there is no such match then return -1.
    private int indexOf (String[] words, WordGram target, int start) {
        for (int k = start; k < words.length; k++) {
            WordGram wg = new WordGram(words, k, target.length());
            if (wg.equals(target))
                return k;
        }
        return -1;
    }

    // a public void method named testIndexOf that has no parameters. This method is only for
    // testing the indexOf method.
    public void testIndexOf() {
        String[] words = "this is just a test yes this is a simple test".split("\\s+");
        WordGram t1 = new WordGram("this is".split("\\s+"), 0, 2);
        System.out.println("should be 0: "+indexOf(words, t1, 0));
        System.out.println("should be 6: "+indexOf(words, t1, 3));
        WordGram frog = new WordGram("frog".split("\\s+"), 0, 1);
        System.out.println("should be -1: "+indexOf(words, frog, 0));
        System.out.println("should be -1: "+indexOf(words, frog, 5));
        WordGram simple = new WordGram("simple test".split("\\s+"), 0, 2);
        System.out.println("should be 9: "+indexOf(words, simple, 2));
        WordGram test = new WordGram("test".split("\\s+"), 0, 1);
        System.out.println("should be 10: "+indexOf(words, test, 5));
    }

    // The getFollows method has one WordGram parameter named kGram. This method returns
    // an ArrayList of all the single words that immediately follow an instance of the
    // WordGram parameter somewhere in the training text. This method should call indexOf
    // to find these matches.
    private ArrayList<String> getFollows(WordGram kGram) {
        ArrayList<String> follows = new ArrayList<String>();
        int pos = 0;
        while (pos < myText.length) {
            int start = indexOf(myText, kGram, pos);
            if (start == -1)
                break;
            if (start + 1 >= myText.length)
                break;
            follows.add(myText[start+1]);
            pos = start+1;
        }
        return follows;
    }

}  // MarkovWord
