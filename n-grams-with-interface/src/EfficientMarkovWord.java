
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
        // use those keys to call makeFollows() (which is what used to be
        // getFollows() in the other MarkovWord classes), because our
        // getFollows() will do a simple map lookup.  The +1 in the loop control
        // allows the last `myOrder` characters be included in the map, even
        // though it will never have follow characters.  Only doing this so my
        // results match what the assignment says I should get.  Be careful to
        // only call getFollows if the key doesn't exist, because every call for
        // `key` will return the same answer so we only need to call if `key`
        // doesn't exist.
//        System.out.println("k running thru "+(myText.length-myOrder));
 //       int added = 0;
        ArrayList<String> follows;
        for (int k = 0; k < myText.length-myOrder; k++) {
            WordGram kGram = new WordGram(myText, k, myOrder);
            /*
            if (k % 1000 == 0) {
                System.out.println("k = "+k+", added = "+added);
                System.out.println("map has "+ followsMap.size()+" keys");
            }
            */
            if (! followsMap.containsKey(kGram.toString())) {
                //ArrayList<String> follows = makeFollows(kGram, k);
                //followsMap.put(kGram.toString(), follows);
                follows = new ArrayList<String>();
            }
            else {
//                added++;
                follows = followsMap.get(kGram.toString());
            }
            follows.add(myText[k+myOrder]);
            //System.out.println(kGram.toString()+": "+follows);
            followsMap.put(kGram.toString(), follows);
            /*
            if (! followsMap.containsKey(kGram.toString()))
                followsMap.put(kGram.toString(), makeFollows(kGram, k));
            */
        }
        // now make sure the last possible key is in the map, even if it
        // needs an empty follows list.
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
    // generates and returns random text that has numWords words. This class generates
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

    // the private indexOf method has three parameters, a String array of all the words in the
    // training text named words, a WordGram named target, and an integer named start indicating
    // where to start looking for a WordGram match in words. This method should return the first
    // position from start that has words in the array words that match the WordGram target.
    // If there is no such match then return -1.
    private int indexOf (String[] words, WordGram target, int start) {
        // the loop check subtracts off the length of `target` because
        // if we are closer than that to the end, we cannot possible match.
        // it uses <= so that a target that happens to be at the very end
        // of `words` gets found instead of skipped.
        for (int k = start; k <= words.length-target.length(); k++) {
            WordGram wg = new WordGram(words, k, target.length());
            if (wg.equals(target))
                return k;
        }
        return -1;
    }

    // The makeFollows method has one WordGram parameter named kGram. This method returns
    // an ArrayList of all the single words that immediately follow an instance of the
    // WordGram parameter somewhere in the training text. This method should call indexOf
    // to find these matches.  This is the `getFollows` method in other MarkovWord classes.
    // I thought a long time about instaniating a MarkovWord object in order to reuse it,
    // and decided against the additional "uses a" dependency.
    private ArrayList<String> makeFollows(WordGram kGram, int pos) {
        ArrayList<String> follows = new ArrayList<String>();
        int kGramLength = kGram.length();
        while (pos < myText.length - kGramLength) {
            int start = indexOf(myText, kGram, pos);
            if (start == -1)
                break;
            if (start + kGramLength >= myText.length)
                break;
            follows.add(myText[start+kGramLength]);
            pos = start+kGramLength;
        }
        return follows;
    }

    // Our getFollows() is now a simple map lookup.  Protect ourselves from
    // unknown test cases by returning an empty list if `kGram` isn't part of our
    // training text.
    protected ArrayList<String> getFollows (WordGram kGram) {
        return followsMap.getOrDefault(kGram.toString(), new ArrayList<String>());
    }
}  // EfficientMarkovWord