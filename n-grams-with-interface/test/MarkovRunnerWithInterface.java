import edu.duke.FileResource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarkovRunnerWithInterface {

    private void runModel(IMarkovModel markov, String text, int size) {
        long start = System.nanoTime();

        markov.setTraining(text);
        System.out.println("running with " + markov);
        for(int k=0; k < 3; k++){
            String st= markov.getRandomText(size);
            printOut(st);
        }

        long end = System.nanoTime();
        System.out.println("run with "+markov.toString()+" took "+(end-start)/1000000000.0+" seconds");
    }

    private void runModel(IMarkovModel markov, String text, int size, int seed){
        markov.setRandom(seed);
        runModel(markov, text, size);
    }

    @Test
    public void runMarkov() {
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
    }

    @Test
    void testHashMap () {
        EfficientMarkovModel e = new EfficientMarkovModel(2);
        e.setRandom(42);
        e.setTraining("yes-this-is-a-thin-pretty-pink-thistle");
        e.printHashMapInfo();
    }

    @Test
    void compareMethods() {
        int order = 2;
        int seed = 42;
        int outputLen = 1000;
        FileResource fr = new FileResource("../data/hawthorne.txt");
        MarkovModel m = new MarkovModel(order);
        EfficientMarkovModel e = new EfficientMarkovModel(order);

        runModel(m, fr.asString(), outputLen, seed);
        runModel(e, fr.asString(), outputLen, seed);
    }

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