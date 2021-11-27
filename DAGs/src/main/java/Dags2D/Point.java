package Dags2D;

import java.util.Objects;

public class Point {
    protected Coord2D position;
    protected BoundBox bounds;

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
        return bounds;
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
