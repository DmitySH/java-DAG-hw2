package Dags2D;

import Dags2D.exceptions.DAGConstraintException;
import Dags2D.exceptions.EmptyBoundsException;

import java.util.*;

public class Point {
    protected Coord2D position;
    protected BoundBox bounds;

    private static final Map<Point, Boolean> colors = new HashMap<>();

    public Point(Coord2D position) {
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
        Point.colors.clear();
    }

    private void dfs(Point vertex) throws DAGConstraintException { //true = inside
        Point.colors.put(vertex, true);
        if (vertex instanceof Origin originVertex) {
            for (Point point : originVertex.getChildren()
            ) {
                if (!colors.containsKey(point)) {
                    dfs(point);
                }
                if (colors.get(point)) {
                    throw new DAGConstraintException();
                }
            }
        }
        colors.put(vertex, false);
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
