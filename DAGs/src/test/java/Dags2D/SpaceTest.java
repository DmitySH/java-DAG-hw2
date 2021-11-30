package Dags2D;

import Dags2D.exceptions.DAGConstraintException;
import Dags2D.exceptions.EmptyBoundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpaceTest {
    private Space instance;
    private Space space;
    private Point onePoint;
    private Origin firstSpaceChild;
    private Origin secondSpaceChild;

    @BeforeEach
    void setInstance() throws DAGConstraintException {
        Coord2D coordsOfOrigin = new Coord2D(1, 1);
        Origin root = new Origin(coordsOfOrigin);
        root.setChildren(new HashSet<>(List.of(
                new Point[]{new Origin(new Coord2D(4, 4)), new Point(new Coord2D(4, 4))})));
        instance = new Space(root);
    }

    @BeforeEach
    void initExample() {
        space = new Space(new Origin(new Coord2D(1, 1)));
        onePoint = new Point(new Coord2D(-1, -1));
    }

    @Test
    void testGetRoot() {
        HashSet<Point> children = new HashSet<>();

        children.add(new Point(new Coord2D(4, 4)));
        children.add(new Origin(new Coord2D(4, 4)));

        assertEquals(instance.getRoot().getPosition(), new Coord2D(1, 1));
        assertEquals(instance.getRoot().getChildren(), children);
    }

    @Test
    void testGetWorldBounds() {
        firstSpaceChild = new Origin(new Coord2D(-2, 1), new HashSet<>(List.of(new Point[]{onePoint})));
        secondSpaceChild = new Origin(new Coord2D(1, 0), new HashSet<>(List.of(new Point[]{onePoint})));

        assertDoesNotThrow(() -> space.getRoot().setChildren(
                new HashSet<>(List.of(new Origin[]{firstSpaceChild, secondSpaceChild}))
        ));

        BoundBox bounds = assertDoesNotThrow(space::getWorldBounds);
        assertEquals(bounds, new BoundBox(new Coord2D(-2, 0), new Coord2D(1, 1)));
    }

    @Test
    void testGetEmptyWorldBounds() {
        HashSet<Point> children = new HashSet<Point>(
                List.of(new Origin[]{
                        new Origin(new Coord2D(2, 2)),
                        new Origin(new Coord2D(3, 3))
                })
        );

        space = new Space(new Origin(new Coord2D(2, -1), children));


        Throwable thrown = assertThrows(
                EmptyBoundsException.class,
                () -> space.getWorldBounds()
        );
        System.out.println(thrown.getMessage());

        children.add(new Point(new Coord2D(10, 12)));

        BoundBox bounds = assertDoesNotThrow(()->space.getWorldBounds());
        assertEquals(bounds, new BoundBox(new Coord2D(12, 11), new Coord2D(12, 11)));
    }
}