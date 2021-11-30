package Dags2D;

import java.util.Objects;

public final class BoundBox {
    private final Coord2D leftLowerPoint;
    private final Coord2D rightUpperPoint;

    public BoundBox(Coord2D point) {
        leftLowerPoint = point;
        rightUpperPoint = point;
    }

    public BoundBox(Coord2D leftLowerPoint, Coord2D rightUpperPoint) {
        this.leftLowerPoint = leftLowerPoint;
        this.rightUpperPoint = rightUpperPoint;
    }

    public Coord2D getLeftLowerPoint() {
        return leftLowerPoint;
    }

    public Coord2D getRightUpperPoint() {
        return rightUpperPoint;
    }

    @Override
    public String toString() {
        return "BoundBox{" +
                "leftLowerPoint=" + leftLowerPoint +
                ", rightUpperPoint=" + rightUpperPoint +
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
        BoundBox boundBox = (BoundBox) other;
        return Objects.equals(leftLowerPoint, boundBox.leftLowerPoint) &&
                Objects.equals(rightUpperPoint, boundBox.rightUpperPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftLowerPoint, rightUpperPoint);
    }
}
