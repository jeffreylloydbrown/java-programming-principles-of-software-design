import edu.duke.FileResource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarkovRunnerWithInterface {
    @Test
    public void runModel(IMarkovModel markov, String text, int size) {
        markov.setTraining(text);
        System.out.println("running with " + markov);
        for(int k=0; k < 3; k++){
            String st= markov.getRandomText(size);
            printOut(st);
        }
    }

    @Test
    public void runMarkov() {
        assertTrue(false, "Must uncommment code once classes created");
        /*  remove this comment when ready.
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        int size = 200;

        MarkovZero mz = new MarkovZero();
        runModel(mz, st, size);

        MarkovOne mOne = new MarkovOne();
        runModel(mOne, st, size);

        MarkovModel mThree = new MarkovModel(3);
        runModel(mThree, st, size);

        MarkovFour mFour = new MarkovFour();
        runModel(mFour, st, size);
        remove this comment when ready */
    }

    @Test
    private void printOut(String s){
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for(int k=0; k < words.length; k++){
            System.out.print(words[k]+ " ");
            psize += words[k].length() + 1;
            if (psize > 60) {
                System.out.println();
                psize = 0;
            }
        }
        System.out.println("\n----------------------------------");
    }

}