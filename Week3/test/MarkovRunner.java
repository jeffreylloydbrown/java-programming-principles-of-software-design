import edu.duke.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MarkovRunner {
    @Test
    public void runModel(IMarkovModel markov, String text, int size){
        markov.setTraining(text);
        System.out.println("running with " + markov);
        for(int k=0; k < 3; k++){
            String st = markov.getRandomText(size);
            printOut(st);
        }
    }

    @Test
    public void runModel(IMarkovModel markov, String text, int size, int seed){
        markov.setTraining(text);
        markov.setRandom(seed);
        System.out.println("running with " + markov);
        for(int k=0; k < 3; k++){
            String st = markov.getRandomText(size);
            printOut(st);
        }
    }

    @Test
    public void runMarkov() {
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');

        MarkovTwo markov = new MarkovTwo();
        markov.setTraining(st);
        for(int k=0; k < 3; k++){
            String res = markov.getRandomText(200);
            printOut(res);
        }
        //MarkovWordOne markovWord = new MarkovWordOne();
        //runModel(markovWord, st, 200);
    }

    @Test
    public void runMarkovZero() {
        FileResource fr = new FileResource("src/data/romeo.txt");
        String st = fr.asString();
        st = st.replace('\n', ' ');

        MarkovZero markov = new MarkovZero();
        markov.setTraining(st);
        markov.setRandom(101);
        for(int k=0; k < 1; k++){
            String res = markov.getRandomText(200);
            printOut(res);
        }
    }

    @Test
    public void runMarkovOne() {
        FileResource fr = new FileResource("src/data/confucius.txt");
        String st = fr.asString();
        st = st.replace('\n', ' ');

        MarkovOne markov = new MarkovOne();
        markov.setTraining(st);
        markov.setRandom(42);
        String check = markov.getRandomText(200);
        assertTrue(check.startsWith("nd are,  Prevedowalvism n thastsour tr ndsang  heag ti. the ffinthe"));
        printOut(check);
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