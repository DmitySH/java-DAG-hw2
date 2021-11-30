package Dags2D;

import Dags2D.exceptions.EmptyBoundsException;

public final class Space {
    private final Origin root;
    private BoundBox bounds;

    public Space(Origin origin) {
        root = origin;
    }

    public Origin getRoot() {
        return root;
    }

    public BoundBox getWorldBounds() throws EmptyBoundsException {
        BoundBox rootBounds = root.getBounds();
        bounds = new BoundBox(rootBounds.getLeftLowerPoint().offset(root.getPosition().getX(), root.getPosition().getY()),
                rootBounds.getRightUpperPoint().offset(root.getPosition().getX(), root.getPosition().getY())
        );

        return bounds;
    }

    @Override
    public String toString() {
        return "Space{" +
                "root=" + root +
                '}';
    }
}
