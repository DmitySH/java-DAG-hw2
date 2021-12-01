package Dags2D;

import Dags2D.exceptions.DAGConstraintException;
import Dags2D.exceptions.EmptyBoundsException;

import java.io.Serializable;
import java.util.*;

public final class Origin extends Point implements Iterable<Point>, Serializable {
    private HashSet<Point> children;

    public Origin(Coord2D position) {
        super(position);
        children = new HashSet<>();
    }

    public Origin(Coord2D position, Set<Point> children) {
        super(position);

        if (children != null) {
            this.children = new HashSet<>(children);
        } else {
            this.children = new HashSet<>();
        }
    }

    public Set<Point> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    public void setChildren(Set<Point> newValue) throws DAGConstraintException {
        children = new HashSet<>(newValue);
        children.remove(null);
        findCycle(this);
    }

    @Override
    protected BoundBox findBoundBox() {
        List<BoundBox> childrenBounds = new ArrayList<>();
        for (Point child : this) {
            if (child != null) {
                childrenBounds.add(child.findBoundBox());
            }
        }

        BoundBox current_bounds = findChildBounds(childrenBounds);
        if (current_bounds == null) {
            return null;
        }

        return new BoundBox(
                current_bounds.getLeftLowerCoords().offset(position.getX(), position.getY()),
                current_bounds.getRightUpperCoords().offset(position.getX(), position.getY()));
    }

    private BoundBox findChildBounds(List<BoundBox> bounds) {
        bounds.removeIf(Objects::isNull);
        if (bounds.size() == 0) {
            return null;
        }

        BoundBox current_bounds = bounds.get(0);
        for (int i = 1; i < bounds.size(); ++i) {
            current_bounds = new BoundBox(
                    Coord2D.coordLeftLower(
                            current_bounds.getLeftLowerCoords(), bounds.get(i).getLeftLowerCoords()),
                    Coord2D.coordRightUpper(
                            current_bounds.getRightUpperCoords(), bounds.get(i).getRightUpperCoords())
            );
        }

        return current_bounds;
    }

    @Override
    public BoundBox getBounds() throws EmptyBoundsException {
        BoundBox boundsWithCurrentOffset = findBoundBox();

        if (boundsWithCurrentOffset == null) {
            throw new EmptyBoundsException(this);
        }

        return new BoundBox(
                boundsWithCurrentOffset.getLeftLowerCoords().offset(-position.getX(), -position.getY()),
                boundsWithCurrentOffset.getRightUpperCoords().offset(-position.getX(), -position.getY()));
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
        return Objects.hash(super.hashCode());
    }

    @Override
    public Iterator<Point> iterator() {
        return getChildren().iterator();
    }

    @Override
    public String toString() {
        return "Origin{" +
                "children=" + children +
                ", position=" + position +
                '}';
    }
}
