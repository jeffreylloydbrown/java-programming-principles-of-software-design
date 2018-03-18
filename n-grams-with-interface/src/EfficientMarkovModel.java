import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class EfficientMarkovModel extends AbstractMarkovModel {

    private HashMap<String, ArrayList<String>> followsMap;

    public EfficientMarkovModel (int order) {
        markovOrder = order;
        myRandom = new Random();
        myText = "";  // no frickin' nulls
        followsMap = new HashMap<String, ArrayList<String>>();
    }

    public void setTraining(String s) {
        if (s != null && !s.isEmpty()) {
            myText = s.trim();
            buildMap();
        }
    }

    public String toString() { return "EfficientMarkovModel of order "+markovOrder; }

    public String getRandomText(int numChars){

        StringBuilder sb = new StringBuilder();
        // Exclude the last `markovOrder` characters from the possible indices,
        // because if they occur at the end of the string, there won't be a following
        // character to include.  This avoids testing for that end-of-buffer instance.
        int index = myRandom.nextInt(myText.length()-markovOrder);

        // find a random starting sequence of the order size we're using and
        // put it into the buffer.
        String key = myText.substring(index, index+markovOrder);
        sb.append(key);

        // Now, for each key and the number of characters we're supposed to do,
        // get the list of characters (as 1-character strings) that follows the
        // key.  Then pick one of those following 1-character strings at random
        // and add it to the string buffer.  Finally update the key to remove
        // its first character, and append the character from `follows` just used.
        for(int k=0; k < numChars-markovOrder; k++){
            ArrayList<String> follows = getFollows(key);
            //System.out.println("key '"+key+ "' " + follows);
            if (follows.size() == 0)
                break;
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            // remove the number of characters returned from `follows` from the
            // front of the key, then add `next` to the end of that to make a new
            // key.  substring(next.length()) will remove the leading characters.
            // It returns an empty string when next.length() = 1, which is perfect.
            key = key.substring(next.length())+next;
        }

        return sb.toString();
    }

    // Our getFollows() is now a simple map lookup.  Protect ourselves from
    // unknown test cases by returning an empty list if `key` isn't part of our
    // training text.
    protected ArrayList<String> getFollows (String key) {
        return followsMap.getOrDefault(key, new ArrayList<String>());
    }

    private void buildMap () {
        // throw away the existing map.
        followsMap.clear();

        // An order 0 or smaller Markov has no follow characters
        // so check for that and abort if found.
        if (markovOrder <= 0) return;

        // Now walk the training text, for every substring of size `markovOrder`.
        // use those keys to call getFollows() in the superclass, because our
        // getFollows() will do a simple map lookup.  The +1 in the loop control
        // allows the last `markovOrder` characters be included in the map, even
        // though it will never have follow chacacters.  Only doing this so my
        // results match what the assignment says I should get.  Be careful to
        // only call getFollows if the key doesn't exist, because every call for
        // `key` will return the same answer so we only need to call if `key`
        // doesn't exist.
        for (int k = 0; k < myText.length()-markovOrder+1; k++) {
            String key = myText.substring(k, k+markovOrder);
            if (! followsMap.containsKey(key))
                followsMap.put(key, super.getFollows(key));
        }
    }

    void printHashMapInfo() {
        // we're told to print contents only if map is small.
        if (followsMap.keySet().size() < 15) {
            for (String key : followsMap.keySet())
                System.out.println(key + ": " + followsMap.get(key));
        }

        System.out.println("map has "+followsMap.keySet().size()+" keys.");
        int biggest = largestValue();
        System.out.println("size of largest array = "+biggest);
        System.out.println("keys of that size:");
        for (String key : followsMap.keySet()) {
            if (biggest == followsMap.get(key).size())
                System.out.println(key);
        }
        System.out.println("==============");
    }

    // find the size of the largest array in the hashmap
    private int largestValue () {
        int largestSize = 0;
        for (String key : followsMap.keySet()) {
            int thisSize = followsMap.get(key).size();
            if (thisSize > largestSize)
                largestSize = thisSize;
        }
        return largestSize;
    }

}  // EfficientMarkovModel
