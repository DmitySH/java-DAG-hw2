package Dags2D.utils;

import Dags2D.Coord2D;
import Dags2D.Origin;
import Dags2D.Point;
import Dags2D.Space;
import Dags2D.exceptions.DAGConstraintException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DAGUtilsTest {
    private Space instance;

    @BeforeEach
    void setInstance() throws DAGConstraintException {
        Coord2D coordsOfOrigin = new Coord2D(1, 1);
        Origin root = new Origin(coordsOfOrigin);
        root.setChildren(new HashSet<>(List.of(
                new Point[]{new Origin(new Coord2D(4, 4)), new Point(new Coord2D(4, 4))})));
        instance = new Space(root);
    }

    @Test
    void testSerializeEquals() throws DAGConstraintException {
        DAGUtils du = new DAGUtils();
        String str = assertDoesNotThrow(() -> du.exportAsString(instance));
        System.out.println(str);
        Space deserialized = assertDoesNotThrow(() -> du.importFromString(str));
        assertEquals(instance, deserialized);

        deserialized.getRoot().setChildren(new HashSet<>(List.of(new Point[]{new Point(new Coord2D(2, 3))})));
        instance.getRoot().setChildren(new HashSet<>(List.of(new Point[]{new Point(new Coord2D(2, 3))})));
        assertEquals(instance, deserialized);
    }

    @Test
    void testSerializeNotEquals() throws DAGConstraintException {
        DAGUtils du = new DAGUtils();
        String str = assertDoesNotThrow(() -> du.exportAsString(instance));
        System.out.println(str);

        instance.getRoot().setChildren(new HashSet<>(List.of(new Point[]{new Point(new Coord2D(2, 3))})));
        Space deserialized = assertDoesNotThrow(() -> du.importFromString(str));
        assertNotEquals(instance, deserialized);
    }
}