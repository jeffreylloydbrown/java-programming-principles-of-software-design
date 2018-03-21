import edu.duke.FileResource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarkovRunnerWithInterface {

    private void runModel(IMarkovModel markov, String text, int size) {
        markov.setTraining(text);
        System.out.println("running with " + markov);
        for(int k=0; k < 3; k++){
            String st= markov.getRandomText(size);
            printOut(st);
        }
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

        long start = System.nanoTime();
        runModel(m, fr.asString(), outputLen, seed);
        long end = System.nanoTime();
        System.out.println(m.toString()+" took "+(end-start)/1000000000.0+" seconds");
        start = System.nanoTime();
        runModel(e, fr.asString(), outputLen, seed);
        end = System.nanoTime();
        System.out.println(m.toString()+" took "+(end-start)/1000000000.0+" seconds");
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

    @Test
    void driveIndexOf () {
        MarkovWordOne markov = new MarkovWordOne();
        markov.testIndexOf();
    }

    @Test
    void driveGetRandomText () {
        String st = "this is just a test yes this is a simple test";
        //String st = "test test test test";
        MarkovWordOne markov = new MarkovWordOne();
        markov.setTraining(st);
        markov.getRandomText(2);
    }

    @Test
    void runMarkovWord () {
        FileResource fr = new FileResource("../data/confucius.txt");
        String st = fr.asString();
        st = st.replace('\n', ' ');
        int size = 120;
        int seed = 175;

        // Expected output:
        // teacher. 12. He that his doings from the free distribution of
        //Ling, the people by learning; then those over with this or hate
        //daring and hates his muttering a belief in war. He refused all
        //likelihood, between men. Fan Ch'ih did not go with you? The Master
        //said, At his best pupil, who notifies you may ignore propriety;
        MarkovWordOne one = new MarkovWordOne();
        runModel(one, st, size, seed);
    }

    @Test
    void practiceQuiz () {
        FileResource fr = new FileResource("../data/romeo.txt");
        String st = fr.asString();
        st = st.replace('\n', ' ');
        EfficientMarkovModel e = new EfficientMarkovModel(5);
        e.setRandom(615);
        e.setTraining(st);
        e.printHashMapInfo();
        // practicq quiz says 41309 is correct key count.  That's what I got.
        // practice quiz says largest array of 3174 is not correct.  don't know why.
    }

}