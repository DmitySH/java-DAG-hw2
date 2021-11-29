package Dags2D;

import Dags2D.exceptions.DAGConstraintException;

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

    public BoundBox getBounds() {
        bounds = findBounds();
        return bounds;
    }

    private BoundBox findBounds() {
        if (this.getClass() == Point.class) {
            return new BoundBox(Coord2D.makeCopy(this.getPosition()));
        } else {
            List<BoundBox> childrenBounds = new ArrayList<>();
            for (Point child :
                    (Origin) this) {
                if (child != null) {
                    childrenBounds.add(child.findBounds());
                }
            }
            //todo polymorphism
//todo: nulls
            BoundBox current_bounds = findChildBounds(childrenBounds);
            return new BoundBox(
                    current_bounds.getLeftLowerPoint().offset(this.position.getX(), this.position.getY()),
                    current_bounds.getRightUpperPoint().offset(this.position.getX(), this.position.getY()));
        }
    }

    private BoundBox findChildBounds(List<BoundBox> bounds) {
        if (bounds.size() == 0) {
            return null;
        }

        BoundBox current_bounds = bounds.get(0);
        for (int i = 1; i < bounds.size(); ++i) {
            current_bounds = new BoundBox(
                    Coord2D.coordLeftLower(
                            current_bounds.getLeftLowerPoint(), bounds.get(i).getLeftLowerPoint()),
                    Coord2D.coordRightUpper(
                            current_bounds.getRightUpperPoint(), bounds.get(i).getRightUpperPoint())
            );
        }

        return current_bounds;
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
}
