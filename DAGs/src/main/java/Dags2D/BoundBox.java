package Dags2D;

final class BoundBox {
    Coord2D leftUpperPoint;
    Coord2D rightLowerPoint;

    public BoundBox(Coord2D point) {
        leftUpperPoint = point;
        rightLowerPoint = point;
    }

    public BoundBox(Coord2D leftUpperPoint, Coord2D rightLowerPoint) {
        this.leftUpperPoint = leftUpperPoint;
        this.rightLowerPoint = rightLowerPoint;
    }
}
