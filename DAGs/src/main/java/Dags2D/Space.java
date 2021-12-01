package Dags2D;

import Dags2D.exceptions.EmptyBoundsException;
import Dags2D.interfaces.DAGSerializable;

import java.io.Serializable;
import java.util.Objects;

/**
 * World's origin.
 */
public final class Space implements Serializable, DAGSerializable {
    private final Origin root;
    private BoundBox bounds;

    /**
     * Construction with root origin.
     *
     * @param origin root.
     */
    public Space(Origin origin) {
        if (origin == null) {
            throw new IllegalArgumentException("Origin can not be null");
        }

        root = origin;
    }

    /**
     * Getter for root.
     *
     * @return root.
     */
    public Origin getRoot() {
        return root;
    }

    /**
     * Bounds for whole world.
     *
     * @return root's bounds with offset.
     * @throws EmptyBoundsException if it has no points.
     */
    public BoundBox getWorldBounds() throws EmptyBoundsException {
        BoundBox rootBounds = root.getBounds();
        bounds = new BoundBox(rootBounds.getLeftLowerCoords().offset(root.getPosition().getX(), root.getPosition().getY()),
                rootBounds.getRightUpperCoords().offset(root.getPosition().getX(), root.getPosition().getY()));

        return bounds;
    }

    /**
     * String view of space.
     *
     * @return string view.
     */
    @Override
    public String toString() {
        return "Space{" +
                "root=" + root +
                '}';
    }

    /**
     * Equals for space.
     *
     * @param other other object.
     * @return true if space are equal.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Space space = (Space) other;
        return root.equals(space.root);
    }

    /**
     * Hashcode of point.
     *
     * @return hashcode of point.
     */
    @Override
    public int hashCode() {
        return Objects.hash(root.getPosition());
    }

    /**
     * Serialize-view for space.
     *
     * @return string for deserializer.
     */
    @Override
    public String stringRepresent() {
        return "Space{" +
                "root=" + root.stringRepresent() +
                '}';
    }
}
