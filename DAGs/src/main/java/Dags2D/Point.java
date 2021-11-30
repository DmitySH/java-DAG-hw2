package Dags2D;

import Dags2D.exceptions.DAGConstraintException;
import Dags2D.exceptions.EmptyBoundsException;

import java.io.Serializable;
import java.util.*;

public class Point implements Serializable {
    private static final Map<Point, Boolean> Colors = new HashMap<>();

    protected Coord2D position;
    private BoundBox bounds;

    public Point(Coord2D position) {
        if (position == null) {
            throw new IllegalArgumentException("Position can not be null");
        }

        this.position = position;
        bounds = new BoundBox(position);
    }

    public Coord2D getPosition() {
        return position;
    }

    public void setPosition(Coord2D newValue) {
        position = newValue;
    }

    protected BoundBox findBoundBox() {
        bounds = new BoundBox(getPosition());
        return bounds;
    }

    public BoundBox getBounds() throws EmptyBoundsException {
        return findBoundBox();
    }

    protected void findCycle(Point startPoint) throws DAGConstraintException {
        dfs(startPoint);
        Point.Colors.clear();
    }

    private void dfs(Point vertex) throws DAGConstraintException {
        Point.Colors.put(vertex, true);

        if (vertex instanceof Origin originVertex) {
            for (Point point : originVertex.getChildren()) {
                if (!Colors.containsKey(point)) {
                    dfs(point);
                }
                if (Colors.get(point)) {
                    throw new DAGConstraintException();
                }
            }
        }

        Colors.put(vertex, false);
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public String toString() {
        return "Point{" +
                "position=" + position +
                '}';
    }
}
