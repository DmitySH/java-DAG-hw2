package Dags2D;

public class Point {
    private Coord2D position;
    private BoundBox bounds;

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
}
