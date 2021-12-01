package Dags2D;

import Dags2D.exceptions.DAGConstraintException;
import Dags2D.exceptions.EmptyBoundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OriginTest {
    private Origin firstOrigin;
    private Origin secondOrigin;
    private HashSet<Point> children;

    private Space space;
    private Origin firstSpaceChild;
    private Origin secondSpaceChild;

    @BeforeEach
    void initOrigins() {
        children = new HashSet<>();

        children.add(new Point(new Coord2D(3.1, 4)));
        children.add(new Point(new Coord2D(1, 33)));

        firstOrigin = new Origin(new Coord2D(23, 43.2), children);
        secondOrigin = new Origin(new Coord2D(23, 43.2), children);
    }

    @BeforeEach
    void initExample() {
        space = new Space(new Origin(new Coord2D(1, 1)));
        Point onePoint = new Point(new Coord2D(-1, -1));
        firstSpaceChild = new Origin(new Coord2D(-2, 1), new HashSet<>(List.of(new Point[]{onePoint})));
        secondSpaceChild = new Origin(new Coord2D(1, 0), new HashSet<>(List.of(new Point[]{onePoint})));
    }

    @Test
    void testEqualsByPoints() {
        assertEquals(firstOrigin, secondOrigin);
        secondOrigin.position = new Coord2D(3, 123);
        assertNotEquals(firstOrigin, secondOrigin);
    }

    @Test
    void testEqualsDifferentLinksSameObjects() {
        firstOrigin = new Origin(new Coord2D(1, 2), new HashSet<>(List.of(
                new Point[]{new Origin(new Coord2D(3, 4), new HashSet<>(List.of(
                        new Point[]{
                                new Origin(new Coord2D(10, 10), new HashSet<>(List.of(
                                        new Point[]{new Origin(new Coord2D(11, 10))}))),
                                new Point(new Coord2D(5, 6)),
                                new Point(new Coord2D(7, 8))
                        }
                ))
                )})));

        secondOrigin = new Origin(new Coord2D(1, 2), new HashSet<>(List.of(
                new Point[]{new Origin(new Coord2D(3, 4), new HashSet<>(List.of(
                        new Point[]{
                                new Point(new Coord2D(7, 8)),
                                new Point(new Coord2D(5, 6)),
                                new Point(new Coord2D(5, 6)),
                                new Origin(new Coord2D(10, 10), new HashSet<>(List.of(
                                        new Point[]{new Origin(new Coord2D(11, 10))})))
                        }
                ))
                )})));

        assertEquals(firstOrigin, secondOrigin);
    }

    @Test
    void testEqualsByConstantChildren() throws DAGConstraintException {
        secondOrigin.position = new Coord2D(23, 43.2);
        children = new HashSet<>();
        children.add(new Point(new Coord2D(2, 12)));
        children.add(new Point(new Coord2D(12, 2)));
        secondOrigin.setChildren(children);
        assertNotEquals(firstOrigin, secondOrigin);
    }

    @Test
    void testEqualsByDynamicChildren() throws DAGConstraintException {
        children = new HashSet<>();
        Point point = new Point(new Coord2D(1, 33));
        children.add(point);
        children.add(new Point(new Coord2D(3.1, 4)));
        secondOrigin.setChildren(children);
        assertEquals(secondOrigin, firstOrigin);

        point.setPosition(new Coord2D(123, 12312));
        assertNotEquals(firstOrigin, secondOrigin);

        point.setPosition(new Coord2D(1, 33));
        assertEquals(secondOrigin, firstOrigin);
    }

    @Test
    void testSetChildrenMinimalCycle() {
        HashSet<Point> anotherChildren = new HashSet<>();
        anotherChildren.add(firstOrigin);

        assertDoesNotThrow(() -> firstOrigin.setChildren(new HashSet<>()));
        assertDoesNotThrow(() -> firstOrigin.setChildren(children));

        assertThrows(
                DAGConstraintException.class,
                () -> firstOrigin.setChildren(anotherChildren)
        );
    }

    @Test
    void testSetChildrenBigCycle() {
        Origin origin1 = new Origin(new Coord2D(0, 1));
        Origin origin2 = new Origin(new Coord2D(1, 2));
        Origin origin3 = new Origin(new Coord2D(1, 3));
        Origin origin4 = new Origin(new Coord2D(2, 4));

        Point point1 = new Point(new Coord2D(4, 1));
        Point point2 = new Point(new Coord2D(4, 1));

        assertDoesNotThrow(() -> origin1.setChildren(new HashSet<>(List.of(origin2, origin3))));
        assertDoesNotThrow(() -> origin2.setChildren(new HashSet<>(List.of(origin4))));
        assertDoesNotThrow(() -> origin3.setChildren(new HashSet<>(List.of(origin4))));
        assertThrows(
                DAGConstraintException.class,
                () -> origin4.setChildren(new HashSet<>(List.of(new Point[]{point1, point2, origin3})))
        );
    }

    @Test
    void getBoundsNotEmpty() {
        assertDoesNotThrow(() -> space.getRoot().setChildren(
                new HashSet<>(List.of(new Origin[]{firstSpaceChild, secondSpaceChild}))
        ));

        BoundBox bounds = assertDoesNotThrow(() -> space.getRoot().getBounds());
        assertEquals(bounds, new BoundBox(new Coord2D(-3, -1), new Coord2D(0, 0)));
    }

    @Test
    void getBoundsEmpty() {
        assertDoesNotThrow(() -> space.getRoot().setChildren(
                new HashSet<>(List.of(new Origin[]{firstSpaceChild, secondSpaceChild}))
        ));

        assertDoesNotThrow(() -> secondOrigin.setChildren(new HashSet<>(List.of(
                new Point[]{new Origin(new Coord2D(5, 4))}))));

        Throwable thrown = assertThrows(
                EmptyBoundsException.class,
                () -> secondOrigin.getBounds()
        );
        System.out.println(thrown.getMessage());
    }

    @Test
    void testSetChildrenByCopy() {
        Origin origin = new Origin(new Coord2D(2, 3));
        Set<Point> children = new HashSet<>();

        children.add(new Origin(new Coord2D(4, 5)));
        children.add(new Point(new Coord2D(6, 7)));
        children.add(null);

        assertDoesNotThrow(() -> origin.setChildren(children));
        assertFalse(origin.getChildren().contains(null));

        children.add(new Point(new Coord2D(2, 123)));
        assertNotEquals(children, origin.getChildren());

        children.remove(new Point(new Coord2D(6, 7)));
        assertNotEquals(children, origin.getChildren());
    }

    @Test
    void testGetChildren() {
        Origin origin = new Origin(new Coord2D(2, 3));
        Set<Point> children = new HashSet<>();

        children.add(new Origin(new Coord2D(4, 5)));
        children.add(new Point(new Coord2D(6, 7)));
        assertDoesNotThrow(() -> origin.setChildren(children));

        Set<Point> newChildren = origin.getChildren();
        for (Point point : newChildren) {
            point = new Point(new Coord2D(7, 7));
        }

        assertEquals(origin.getChildren(), children);

        assertThrows(UnsupportedOperationException.class, () -> newChildren.add(new Point(new Coord2D(1, 123))));
        assertThrows(UnsupportedOperationException.class, () -> newChildren.remove(new Point(new Coord2D(1, 123))));
    }

    @Test
    void testHashCode() {
        assertEquals(firstOrigin.hashCode(), Objects.hash(Objects.hash(firstOrigin.getPosition().hashCode())));
    }

    @Test
    void testIterator() {
        ArrayList<Point> iteratorPoints = new ArrayList<>();

        for (Point point : firstOrigin) {
            iteratorPoints.add(point);
        }

        ArrayList<Point> childrenIteratorPoints = new ArrayList<>(firstOrigin.getChildren());

        assertEquals(iteratorPoints, childrenIteratorPoints);
    }

    @Test
    void testToString() {
        assertEquals(firstOrigin.toString(), "Origin{children=[Point{position={x=1.0, y=33.0}}, " +
                "Point{position={x=3.1, y=4.0}}]," +
                " position={x=23.0, y=43.2}}");
    }
}