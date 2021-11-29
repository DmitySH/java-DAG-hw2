package Dags2D;

final class BoundBox {
    Coord2D leftLowerPoint;
    Coord2D rightUpperPoint;

    public BoundBox(Coord2D point) {
        leftLowerPoint = point;
        rightUpperPoint = point;
    }

    public BoundBox(Coord2D leftLowerPoint, Coord2D rightUpperPoint) {
        this.leftLowerPoint = leftLowerPoint;
        this.rightUpperPoint = rightUpperPoint;
    }
}
