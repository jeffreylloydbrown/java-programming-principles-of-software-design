import java.util.ArrayList;
import java.util.Random;

public class MarkovOne extends AbstractMarkovModel {

    public MarkovOne() {
        markovOrder = 1;
        myRandom = new Random();
        myText = "";  // no frickin' nulls
    }

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

}
