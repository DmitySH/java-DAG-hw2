package Dags2D.areas;

import Dags2D.entities.Coord2D;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoundBoxTest {

    @Test
    void testToString() {
        assertEquals(new BoundBox(new Coord2D(1, 3)).toString(),
                "BoundBox{leftLowerPoint={x=1.0, y=3.0}, rightUpperPoint={x=1.0, y=3.0}}");
    }

    @Test
    void testEquals() {
        assertEquals(new BoundBox(new Coord2D(1, 3), new Coord2D(3, 2)),
                new BoundBox(new Coord2D(1, 3), new Coord2D(3, 2)));
    }

    @Test
    void testHashCode() {
        BoundBox bounds = new BoundBox(new Coord2D(1, 3),
                new Coord2D(3, 2));
        assertEquals(bounds.hashCode(), Objects.hash(bounds.getLeftLowerCoords(),
                bounds.getRightUpperCoords()));
    }
}