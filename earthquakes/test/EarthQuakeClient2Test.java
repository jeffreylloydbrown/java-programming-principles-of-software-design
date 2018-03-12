import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EarthQuakeClient2Test {
    @Test
    void quakesWithFilter () {
        EarthQuakeClient2 ec = new EarthQuakeClient2();
        ec.quakesWithFilter();
    }

}