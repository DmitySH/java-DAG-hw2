package Dags2D;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void testEquals() {
        assertEquals(new Point(new Coord2D(3.12, 5)), new Point(new Coord2D(3.12, 5)));
        assertNotEquals(new Point(new Coord2D(311.12, 5)), new Point(new Coord2D(3.12, 5)));
        assertNotEquals(3.12, new Point(new Coord2D(3.12, 5)));
        assertNotEquals(null, new Point(new Coord2D(3.12, 5)));
    }

    @Test
    void testGetBounds() {
        Point point = new Point(new Coord2D(1, 2));
        BoundBox bounds = assertDoesNotThrow(point::getBounds);
        assertEquals(bounds, new BoundBox(new Coord2D(1, 2), new Coord2D(1, 2)));
    }
}