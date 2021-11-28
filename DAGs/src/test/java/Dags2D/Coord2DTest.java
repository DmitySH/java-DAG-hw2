package Dags2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Coord2DTest {
    private Coord2D instance;

    @Test
    void testEquals() {
        assertEquals(new Coord2D(3.12, 5), new Coord2D(3.12, 5));
        assertNotEquals(new Coord2D(3, 5), new Coord2D(3.12, 5));
        assertNotEquals(3.12, new Coord2D(3.12, 5));
        assertNotEquals(null, new Coord2D(3.12, 5));
        Coord2D coord = new Coord2D(6, 1.123);
        assertEquals(coord, coord);
    }

}