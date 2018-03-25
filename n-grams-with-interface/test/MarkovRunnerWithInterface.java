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
/*
        EfficientMarkovModel e = new EfficientMarkovModel(2);
        e.setRandom(42);
        e.setTraining("yes-this-is-a-thin-pretty-pink-thistle");
        e.printHashMapInfo();
*/
        EfficientMarkovWord ew = new EfficientMarkovWord(2);
        ew.setRandom(42);
        //ew.setTraining("this is a test yes this is really a test");
        ew.setTraining("this is a test yes this is really a test yes a test this is wow");
        ew.printHashMapInfo();
    }

    @Test
    void compareMethods() {
        int order = 2;
        int seed = 42;
        int outputLen = 20;
        FileResource fr = new FileResource("../data/hawthorne.txt");
        //FileResource fr = new FileResource("../data/romeo.txt");
        String st = fr.asString().replace('\n', ' ');
        /*
        MarkovModel m = new MarkovModel(order);
        EfficientMarkovModel e = new EfficientMarkovModel(order);
        */
        MarkovWord m = new MarkovWord(order);
        EfficientMarkovWord e = new EfficientMarkovWord(order);

        long start = System.nanoTime();
        runModel(m, st, outputLen, seed);
        long end = System.nanoTime();
        System.out.println(m.toString()+" took "+(end-start)/1000000000.0+" seconds");
        start = System.nanoTime();
        runModel(e, st, outputLen, seed);
        end = System.nanoTime();
        System.out.println(e.toString()+" took "+(end-start)/1000000000.0+" seconds");
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
        MarkovWordOne one = new MarkovWordOne();
        System.out.println(one);
        one.testIndexOf();
        MarkovWordTwo two = new MarkovWordTwo();
        System.out.println(two);
        two.testIndexOf();
        MarkovWord mw = new MarkovWord(3);
        System.out.println(mw);
        mw.testIndexOf();
    }

    @Test
    void driveGetRandomText () {
        String st = "this is just a test yes this is a simple test";  // rarely 10 words cuz most follows are single word choices.
        //String st = "test test test test";  // always length 10 cuz key has multiple follows values to choose.

        MarkovWordTwo markov = new MarkovWordTwo();
        markov.setTraining(st);
        System.out.println(markov);
        System.out.println(markov.getRandomText(10));

        MarkovWord mw = new MarkovWord(3);
        mw.setTraining(st);
        System.out.println(mw);

        System.out.println(mw.getRandomText(10));
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

        // Expected first line:
        // the minister know me? Because I was not so great; xix. 21, says
        MarkovWordTwo two = new MarkovWordTwo();
        runModel(two, st, size, 549);
    }

    @Test
    void runMarkovWordwithWordGram() {
        FileResource fr = new FileResource("../data/confucius.txt");
        String st = fr.asString();
        st = st.replace('\n', ' ');
        int size = 120;
        int seed = 643;

        // Expected first line:
        // failure. The sense of his wasted powers may well have tempted
        MarkovWord three = new MarkovWord(3);
        runModel(three, st, size, seed);
    }

    @Test
    void practiceQuiz () {
        /*
        FileResource fr = new FileResource("../data/romeo.txt");
        String st = fr.asString();
        st = st.replace('\n', ' ');
        EfficientMarkovModel e = new EfficientMarkovModel(5);
        e.setRandom(615);
        e.setTraining(st);
        e.printHashMapInfo();
        // practicq quiz says 41309 is correct key count.  That's what I got.
        // practice quiz says largest array of 3174 is not correct.  don't know why.
        */
        FileResource fr = new FileResource("../data/confucius.txt");
        String st = fr.asString();
        st = st.replace('\n', ' ');
        //MarkovWordOne one = new MarkovWordOne();
        //runModel(one, st, 80, 139);
        MarkovWordTwo two = new MarkovWordTwo();
        runModel(two, st, 80, 832);
    }

}