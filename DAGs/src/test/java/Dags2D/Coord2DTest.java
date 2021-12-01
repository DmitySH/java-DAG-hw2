package Dags2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class Coord2DTest {
    private Coord2D instance;

    @BeforeEach
    void init() {
        instance = new Coord2D(1.3, -2);
    }

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

    @Test
    void testGetX() {
        assertEquals(instance.getX(), 1.3);
    }

    @Test
    void testGetY() {
        assertEquals(instance.getY(), -2);
    }

    @Test
    void testHashCode() {
        assertEquals(instance.hashCode(), Objects.hash(instance.getX(), instance.getY()));
    }

    @Test
    void testMakeCopy() {
        Coord2D copy = Coord2D.makeCopy(instance);

        assertNotSame(copy, instance);
        assertEquals(copy, instance);
    }

    @Test
    void testToString() {
        assertEquals("{x=1.3, y=-2.0}", instance.toString());
    }

    @Test
    void testOffset() {
        Coord2D ofsetted = instance.offset(1, -1);
        Coord2D sameCoords = new Coord2D(2.3, -3);

        assertEquals(ofsetted, sameCoords);
        assertNotSame(ofsetted, instance);
    }
}