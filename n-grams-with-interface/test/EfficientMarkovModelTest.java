import edu.duke.FileResource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EfficientMarkovModelTest {
    @Test
    void setTraining () {
        EfficientMarkovModel e = new EfficientMarkovModel(1);
        e.setTraining("tta.");
        e.printHashMapInfo();
    }

    @Test
    void showString () {
        EfficientMarkovModel e = new EfficientMarkovModel(19);
        assertEquals("EfficientMarkovModel of order 19", e.toString());
        EfficientMarkovModel e2 = new EfficientMarkovModel(-1);
        assertEquals("EfficientMarkovModel of order -1", e2.toString());
    }

    @Test
    void getRandomText () {
    }

    @Test
    void getFollows () {
    }

}