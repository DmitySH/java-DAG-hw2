package Dags2D;

import java.io.Serializable;
import java.util.Objects;

public final class BoundBox implements Serializable {
    private final Coord2D leftLowerCoords;
    private final Coord2D rightUpperCoords;

    public BoundBox(Coord2D point) {
        leftLowerCoords = point;
        rightUpperCoords = point;
    }

    public BoundBox(Coord2D leftLowerCoords, Coord2D rightUpperCoords) {
        this.leftLowerCoords = leftLowerCoords;
        this.rightUpperCoords = rightUpperCoords;
    }

    public Coord2D getLeftLowerCoords() {
        return leftLowerCoords;
    }

    public Coord2D getRightUpperCoords() {
        return rightUpperCoords;
    }

    @Override
    public String toString() {
        return "BoundBox{" +
                "leftLowerPoint=" + leftLowerCoords +
                ", rightUpperPoint=" + rightUpperCoords +
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
        return Objects.equals(leftLowerCoords, boundBox.leftLowerCoords) &&
                Objects.equals(rightUpperCoords, boundBox.rightUpperCoords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftLowerCoords, rightUpperCoords);
    }
}
