package Dags2D;

import java.util.HashSet;
import java.util.Set;

public final class Origin extends Point {
    Set<Point> children;

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
}
