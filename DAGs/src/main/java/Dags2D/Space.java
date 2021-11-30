package Dags2D;

import Dags2D.exceptions.EmptyBoundsException;

import java.io.Serializable;
import java.util.Objects;

public final class Space implements Serializable {
    private final Origin root;
    private BoundBox bounds;

    public Space(Origin origin) {
        if (origin == null) {
            throw new IllegalArgumentException("Origin can not be null");
        }

        root = origin;
    }

    public Origin getRoot() {
        return root;
    }

    public BoundBox getWorldBounds() throws EmptyBoundsException {
        BoundBox rootBounds = root.getBounds();
        bounds = new BoundBox(rootBounds.getLeftLowerCoords().offset(root.getPosition().getX(), root.getPosition().getY()),
                rootBounds.getRightUpperCoords().offset(root.getPosition().getX(), root.getPosition().getY()));

        return bounds;
    }

    @Override
    public String toString() {
        return "Space{" +
                "root=" + root +
                '}';
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(root.getPosition());
    }
}
