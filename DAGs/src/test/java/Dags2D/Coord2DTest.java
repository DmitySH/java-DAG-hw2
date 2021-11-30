package Dags2D;

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

    @Test
    void coordLeftLowerTest() {
        Coord2D first = new Coord2D(1, 3);
        Coord2D second = new Coord2D(2, 2);

        assertEquals(Coord2D.coordLeftLower(first, second), new Coord2D(1, 2));
    }

    @Test
    void coordRightUpperTest() {
        Coord2D first = new Coord2D(1, 3);
        Coord2D second = new Coord2D(2, 2);

        assertEquals(Coord2D.coordRightUpper(first, second), new Coord2D(2, 3));
    }
}