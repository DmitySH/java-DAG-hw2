package Dags2D.entities;

import Dags2D.areas.BoundBox;
import Dags2D.exceptions.DAGConstraintException;
import Dags2D.exceptions.EmptyBoundsException;
import Dags2D.interfaces.DAGSerializable;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Origin with position and other origins and points.
 */
public final class Origin extends Point implements Iterable<Point>, Serializable, DAGSerializable {
    private HashSet<Point> children;

    /**
     * Construction with position.
     *
     * @param position position.
     */
    public Origin(Coord2D position) {
        super(position);
        children = new HashSet<>();
    }

    /**
     * Construction with position and children.
     *
     * @param position position.
     * @param children children.
     */
    public Origin(Coord2D position, Set<Point> children) {
        super(position);

        if (children != null) {
            this.children = new HashSet<>(children);
            this.children.remove(null);
            try {
                findCycle(this);
            } catch (DAGConstraintException ex) {
                this.children = new HashSet<>();
            }
        } else {
            this.children = new HashSet<>();
        }
    }

    /**
     * Children getter.
     *
     * @return children copy.
     */
    public Set<Point> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    /**
     * Children setter.
     *
     * @param newValue new children.
     * @throws DAGConstraintException if it has cycles.
     */
    public void setChildren(Set<Point> newValue) throws DAGConstraintException {
        HashSet<Point> prevChildren = children;

        try {
            children = new HashSet<>(newValue);
            children.remove(null);
            findCycle(this);
        } catch (DAGConstraintException ex) {
            children = prevChildren;
            throw ex;
        }
    }

    /**
     * Finds bounds.
     *
     * @return bounds.
     */
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

    /**
     * Finds children bounds.
     *
     * @param bounds children's bounds.
     * @return bounds of all children.
     */
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

    /**
     * Getter for bounds.
     *
     * @return bounds of origin.
     * @throws EmptyBoundsException if there is no points.
     */
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

    /**
     * Equals for origin.
     *
     * @param other other object.
     * @return true if they are equal.
     */
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

    /**
     * Hashcode for origin.
     *
     * @return hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    /**
     * Iterator of origin.
     *
     * @return iterator of children.
     */
    @Override
    public Iterator<Point> iterator() {
        return getChildren().iterator();
    }

    /**
     * String view of origin.
     *
     * @return string view.
     */
    @Override
    public String toString() {
        return "Origin{" +
                "children=" + children +
                ", position=" + position +
                '}';
    }

    /**
     * Serialize-view for origin.
     *
     * @return string for deserializer.
     */
    @Override
    public String stringRepresent() {

        return "Origin{" +
                "children=" + childrenRepresentation() +
                ", position=" + position.stringRepresent() +
                '}';
    }

    private String childrenRepresentation() {
        Iterator<Point> it = children.iterator();
        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (; ; ) {
            DAGSerializable e = it.next();
            sb.append(e == this ? "(this Collection)" : e.stringRepresent());
            if (!it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }

    /**
     * Deserializes from string.
     *
     * @param stringRepresent serialized object.
     * @return deserialized origin.
     */
    public static DAGSerializable createFromStringRepresent(String stringRepresent) {
        String set = stringRepresent.substring(stringRepresent.indexOf('[') + 1, stringRepresent.lastIndexOf(']'));
        HashSet<Point> children = getChildrenFromString(set);

        stringRepresent = stringRepresent.substring(stringRepresent.lastIndexOf(']'));
        Pattern pattern = Pattern.compile("Coord2D\\{.*?}");
        Matcher matcher = pattern.matcher(stringRepresent);
        matcher.find();
        Coord2D coords = (Coord2D) Coord2D.createFromStringRepresent(matcher.group());

        return new Origin(coords, children);
    }

    private static HashSet<Point> getChildrenFromString(String childString) {
        Pattern pattern = Pattern.compile("Origin\\{.*?}");
        Matcher matcher = pattern.matcher(childString);

        HashSet<Point> children = new HashSet<>();

        while (matcher.find()) {
            children.add((Origin) Origin.createFromStringRepresent(childString.substring(matcher.start(), matcher.end())));
        }

        pattern = Pattern.compile("Point\\{.*?}");
        matcher = pattern.matcher(childString);
        while (matcher.find()) {
            children.add((Point) Point.createFromStringRepresent(childString.substring(matcher.start(), matcher.end())));
        }

        return children;
    }
}
