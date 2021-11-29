package Dags2D;

public final class Space {
    private final Origin root;
    private BoundBox bounds;

    public Space(Origin origin) {
        root = origin;
    }

    public Origin getRoot() {
        return root;
    }

    public BoundBox getWorldBounds() {
        BoundBox rootBounds = root.getBounds();
        bounds = new BoundBox(rootBounds.getLeftLowerPoint().offset(root.getPosition().getX(), root.getPosition().getY()),
                rootBounds.getRightUpperPoint().offset(root.getPosition().getX(), root.getPosition().getY())
        );
        return bounds;
    }
}
