package Dags2D;

import Dags2D.exceptions.DAGConstraintException;
import Dags2D.exceptions.EmptyBoundsException;
import Dags2D.interfaces.DAGSerializable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple physical point.
 */
public class Point implements Serializable, DAGSerializable {
    private static final Map<Point, Boolean> Colors = new HashMap<>();

    protected Coord2D position;
    private BoundBox bounds;

    /**
     * Construction of point.
     *
     * @param position point's postion.
     */
    public Point(Coord2D position) {
        if (position == null) {
            throw new IllegalArgumentException("Position can not be null");
        }

        this.position = position;
        bounds = new BoundBox(position);
    }

    /**
     * Position getter.
     *
     * @return position.
     */
    public Coord2D getPosition() {
        return position;
    }

    /**
     * Position setter.
     *
     * @param newValue new position.
     */
    public void setPosition(Coord2D newValue) {
        position = newValue;
    }

    /**
     * Finds point's bounds.
     *
     * @return coords of point as bounds.
     */
    protected BoundBox findBoundBox() {
        bounds = new BoundBox(getPosition());
        return bounds;
    }

    /**
     * Getter for bounds.
     *
     * @return bounds of point.
     * @throws EmptyBoundsException if it has no bounds.
     */
    public BoundBox getBounds() throws EmptyBoundsException {
        return findBoundBox();
    }

    /**
     * Checks if DAG has cycles.
     *
     * @param startPoint first vertex.
     * @throws DAGConstraintException if there is cycle.
     */
    protected void findCycle(Point startPoint) throws DAGConstraintException {
        dfs(startPoint);
        Point.Colors.clear();
    }

    /**
     * @param vertex current vertex.
     * @throws DAGConstraintException if there is cycle.
     */
    private void dfs(Point vertex) throws DAGConstraintException {
        Point.Colors.put(vertex, true);

        if (vertex instanceof Origin originVertex) {
            for (Point point : originVertex.getChildren()) {
                if (!Colors.containsKey(point)) {
                    dfs(point);
                }
                if (Colors.get(point)) {
                    throw new DAGConstraintException(point);
                }
            }
        }

        Colors.put(vertex, false);
    }

    /**
     * Equals for point.
     *
     * @param other other object.
     * @return true if coords are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Point point = (Point) other;
        return Objects.equals(point.getPosition(), this.getPosition());
    }

    /**
     * Hashcode of point.
     *
     * @return hashcode of point.
     */
    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    /**
     * String view of point.
     *
     * @return string view.
     */
    @Override
    public String toString() {
        return "Point{" +
                "position=" + position +
                '}';
    }

    /**
     * Serialize-view for point.
     *
     * @return string for deserializer.
     */
    @Override
    public String stringRepresent() {
        return "Point{" +
                "position=" + position.stringRepresent() +
                '}';
    }

    /**
     * Deserializes from string.
     *
     * @param stringRepresent serialized object.
     * @return deserialized point.
     */
    public static DAGSerializable createFromStringRepresent(String stringRepresent) {
        Pattern pattern = Pattern.compile("Coord2D\\{.*?}");
        Matcher matcher = pattern.matcher(stringRepresent);

        matcher.find();
        return new Point((Coord2D) Coord2D.createFromStringRepresent(matcher.group()));
    }
}
