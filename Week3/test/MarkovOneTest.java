import edu.duke.FileResource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MarkovOneTest {
    @Test
    void getFollows () {
        String st = "this is a test yes this is a test.";
        MarkovOne markov = new MarkovOne();

        markov.setTraining("");
        assertEquals("[]", markov.getFollows("t").toString());

        markov.setTraining("tta");
        assertEquals("[t, a]", markov.getFollows("t").toString());

        markov.setTraining("this is a test yes this is a test.");
        assertEquals("[h, e,  , h, e, .]", markov.getFollows("t").toString());
        assertEquals("[s, s, s]", markov.getFollows("e").toString());
        assertEquals("[t,  , t]", markov.getFollows("es").toString());
        assertEquals("[]", markov.getFollows(".").toString());
        assertEquals("[]", markov.getFollows("t.").toString());
    }

    @Test
    void testGetFollowsWithFile() {
        MarkovOne markov = new MarkovOne();
        FileResource fr = new FileResource();
        markov.setTraining(fr.asString());
        String follows = "t";
        System.out.println("getFollows("+follows+") = "+markov.getFollows(follows).size());
    }

}