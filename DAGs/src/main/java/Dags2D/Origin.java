package Dags2D;

import java.util.HashSet;

public final class Origin extends Point {
    HashSet<Point> children;

    public Origin(Coord2D position) {
        super(position);
        children = new HashSet<>();
    }

    public Origin(Coord2D position, HashSet<Point> children) {
        super(position);
        this.children = children;
    }

    public HashSet<Point> getChildren() {
        return children;
    }

    public void setChildren(HashSet<Point> newValue) {
        children = newValue;
    }
}
