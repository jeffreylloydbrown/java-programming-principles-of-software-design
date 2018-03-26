
/**
 * Write a description of class EfficientMarkovWord here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.*;

public class EfficientMarkovWord implements IMarkovModel {
    private int myOrder;
    private String[] myText;
    private Random myRandom;
    private HashMap<String, ArrayList<String>> followsMap;

    public EfficientMarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
        followsMap = new HashMap<String, ArrayList<String>>();
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String text){
        if (text != null && ! text.isEmpty()) {
            myText = text.trim().split("\\s+");
            buildMap();
        }
    }

    public String toString() { return "EfficientMarkovWord of order "+myOrder; }

    private void buildMap () {
        // throw away the existing map.
        followsMap.clear();

        // An order 0 or smaller Markov has no follow characters
        // so check for that and abort if found.
        if (myOrder <= 0) return;

        // Now walk the training text, for every WordGram of size `myOrder`.
        // look up those keys with getFollows().  That will return either an
        // existing list to which we will add, or an empty list to which we will
        // add.  Then put the updated list back into the map using that same key.
        for (int k = 0; k < myText.length-myOrder; k++) {
            WordGram kGram = new WordGram(myText, k, myOrder);
            ArrayList<String> follows = getFollows(kGram);
            follows.add(myText[k+myOrder]);
            //System.out.println(kGram+": "+follows);
            followsMap.put(kGram.toString(), follows);
        }
        // now make sure the last possible key is in the map, even if it
        // needs an empty follows list.  Otherwise we get the wrong key count
        // in the quizzes.
        WordGram kGram = new WordGram(myText, myText.length-myOrder, myOrder);
        if (! followsMap.containsKey(kGram.toString()))
            followsMap.put(kGram.toString(), new ArrayList<String>());
    }

    void printHashMapInfo() {
        // we're told to print contents only if map is small.
        if (followsMap.keySet().size() < 15) {
            for (String kGram : followsMap.keySet())
                System.out.println(kGram + ": " + followsMap.get(kGram));
        }

        System.out.println("map has "+followsMap.keySet().size()+" keys.");
        int biggest = largestValue();
        System.out.println("size of largest array = "+biggest);
        System.out.println("keys of that size:");
        for (String kGram : followsMap.keySet()) {
            if (biggest == followsMap.get(kGram).size())
                System.out.println("'"+kGram+"'");
        }
        System.out.println("==============");
    }

    // find the size of the largest array in the hashmap
    private int largestValue () {
        int largestSize = 0;
        for (String kGram : followsMap.keySet()) {
            int thisSize = followsMap.get(kGram).size();
            if (thisSize > largestSize)
                largestSize = thisSize;
        }
        return largestSize;
    }

    // The getRandomText method has one integer parameter named numWords. This method
    // generates and returns random text that has numWords words. This method generates
    // each word by randomly choosing a word from the training text that follows the
    // current word(s) in the training text. When you copied the body of EfficientMarkovWordOne
    // into the EfficientMarkovWord class, you copied this method from EfficientMarkovWordOne. Much of
    // the code from the copied method will still be correct for EfficientMarkovWord, but you
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

    // Our getFollows() is now a simple map lookup.  By returning an empty
    // array list if `kGram` isn't found, I can use this method when building
    // the map.  Gotta appreciate getOrDefault() !!
    protected ArrayList<String> getFollows (WordGram kGram) {
        return followsMap.getOrDefault(kGram.toString(), new ArrayList<String>());
    }

}  // EfficientMarkovWord