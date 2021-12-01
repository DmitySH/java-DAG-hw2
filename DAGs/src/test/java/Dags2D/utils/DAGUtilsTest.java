package Dags2D.utils;

import Dags2D.areas.BoundBox;
import Dags2D.areas.Space;
import Dags2D.entities.Coord2D;
import Dags2D.entities.Origin;
import Dags2D.entities.Point;
import Dags2D.exceptions.DAGConstraintException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DAGUtilsTest {
    private Space instance;

    private BoundBox b;
    private Coord2D c;
    private Point p;
    private Origin o;
    private Space s;

    private DAGUtils dagUtils;

    @BeforeEach
    void mainInit() {
        dagUtils = new DAGUtils();

        b = new BoundBox(new Coord2D(1, 1), new Coord2D(2, 2));
        c = new Coord2D(-3.123, 5);
        p = new Point(new Coord2D(4, 4));
        o = new Origin(new Coord2D(5, 5), new HashSet<>(List.of(
                new Point[]{new Point(new Coord2D(6, 6)), new Origin(new Coord2D(7, 7))})));
        s = new Space(o);
    }

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
        String str = assertDoesNotThrow(() -> dagUtils.exportAsBinaryString(instance));
        System.out.println(str);
        Space deserialized = assertDoesNotThrow(() -> dagUtils.importFromBinaryString(str));
        assertEquals(instance, deserialized);

        deserialized.getRoot().setChildren(new HashSet<>(List.of(new Point[]{new Point(new Coord2D(2, 3))})));
        instance.getRoot().setChildren(new HashSet<>(List.of(new Point[]{new Point(new Coord2D(2, 3))})));
        assertEquals(instance, deserialized);
    }

    @Test
    void testSerializeNotEquals() throws DAGConstraintException {
        String str = assertDoesNotThrow(() -> dagUtils.exportAsBinaryString(instance));
        System.out.println(str);

        instance.getRoot().setChildren(new HashSet<>(List.of(new Point[]{new Point(new Coord2D(2, 3))})));
        Space deserialized = assertDoesNotThrow(() -> dagUtils.importFromBinaryString(str));
        assertNotEquals(instance, deserialized);
    }

    @Test
    void mySerializeTest() {
        System.out.println(c.stringRepresent());
        System.out.println(b.stringRepresent());
        System.out.println(p.stringRepresent());
        System.out.println(o.stringRepresent());
        System.out.println(s.stringRepresent());
        System.out.println();
        System.out.println(Coord2D.createFromStringRepresent(c.stringRepresent()));
        System.out.println(BoundBox.createFromStringRepresent(b.stringRepresent()));
        System.out.println(Point.createFromStringRepresent(p.stringRepresent()));
        System.out.println(Origin.createFromStringRepresent(o.stringRepresent()));
        System.out.println(Space.createFromStringRepresent(s.stringRepresent()));

        assertEquals(new Space(new Origin(new Coord2D(1, 1))),
                Space.createFromStringRepresent(new Space(new Origin(new Coord2D(1, 1))).stringRepresent()));

        assertEquals(c, Coord2D.createFromStringRepresent(c.stringRepresent()));
        assertEquals(b, BoundBox.createFromStringRepresent(b.stringRepresent()));
        assertEquals(p, Point.createFromStringRepresent(p.stringRepresent()));
        assertEquals(o, Origin.createFromStringRepresent(o.stringRepresent()));
        assertEquals(s, Space.createFromStringRepresent(s.stringRepresent()));
    }

    @Test
    void TestImportFromString() {
        assertEquals(s, dagUtils.importFromString(dagUtils.exportAsString(s)));
    }

    @Test
    void testExportAsString() {
        assertEquals(dagUtils.exportAsString(s),
                "Space{root=Origin{children=[Point{position=Coord2D{x=6.0, y=6.0}}, " +
                        "Origin{children=[], position=Coord2D{x=7.0, y=7.0}}], position=Coord2D{x=5.0, y=5.0}}}");
    }
}