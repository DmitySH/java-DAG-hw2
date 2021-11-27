package Dags2D;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class Origin extends Point {
    private Set<Point> children;

    public Origin(Coord2D position) {
        super(position);
        children = new HashSet<>();
    }

    public Origin(Coord2D position, Set<Point> children) {
        super(position);
        this.children = children;
    }

    public Set<Point> getChildren() {
        return children;
    }

    public void setChildren(Set<Point> newValue) {
        children = newValue;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }

        Origin origin = (Origin) other;
        return Objects.equals(children, origin.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), children);
    }
}
