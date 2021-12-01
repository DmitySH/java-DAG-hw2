package Dags2D.entities;

import Dags2D.areas.BoundBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    private Point instance;

    @BeforeEach
    void init() {
        instance = new Point(new Coord2D(2, -3));
    }

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

    @Test
    void testGetPosition() {
        assertEquals(instance.getPosition(), new Coord2D(2, -3));
    }

    @Test
    void testSetPosition() {
        Coord2D newPos = new Coord2D(3, -5);
        instance.setPosition(newPos);
        assertEquals(instance.getPosition(), new Coord2D(3, -5));
    }

    @Test
    void testHashCode() {
        assertEquals(instance.hashCode(), Objects.hash(instance.getPosition()));
    }

    @Test
    void testToString() {
        assertEquals(instance.toString(), "Point{position={x=2.0, y=-3.0}}");
    }
}