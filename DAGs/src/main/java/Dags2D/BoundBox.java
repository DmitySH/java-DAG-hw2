package Dags2D;

import Dags2D.interfaces.DAGSerializable;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bounds of points and origins.
 */
public final class BoundBox implements Serializable, DAGSerializable {
    private final Coord2D leftLowerCoords;
    private final Coord2D rightUpperCoords;

    /**
     * Construction of point's bounds.
     *
     * @param point one coords.
     */
    public BoundBox(Coord2D point) {
        leftLowerCoords = point;
        rightUpperCoords = point;
    }

    /**
     * Construction of origin's bounds.
     *
     * @param leftLowerCoords  left lower coords.
     * @param rightUpperCoords right upper coords.
     */
    public BoundBox(Coord2D leftLowerCoords, Coord2D rightUpperCoords) {
        this.leftLowerCoords = leftLowerCoords;
        this.rightUpperCoords = rightUpperCoords;
    }

    /**
     * Getter for leftLowerCoords.
     *
     * @return leftLowerCoords.
     */
    public Coord2D getLeftLowerCoords() {
        return leftLowerCoords;
    }

    /**
     * Getter for rightUpperCoords.
     *
     * @return rightUpperCoords.
     */
    public Coord2D getRightUpperCoords() {
        return rightUpperCoords;
    }

    /**
     * String view of point.
     *
     * @return string view.
     */
    @Override
    public String toString() {
        return "BoundBox{" +
                "leftLowerPoint=" + leftLowerCoords +
                ", rightUpperPoint=" + rightUpperCoords +
                '}';
    }

    /**
     * Equals for bounds.
     *
     * @param other other object.
     * @return true if bounds are equal.
     */
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

    /**
     * Hashcode of bounds.
     *
     * @return hashcode of bounds.
     */
    @Override
    public int hashCode() {
        return Objects.hash(leftLowerCoords, rightUpperCoords);
    }

    /**
     * Serialize-view for bounds.
     *
     * @return string for deserializer.
     */
    @Override
    public String stringRepresent() {
        return "BoundBox{" +
                "leftLowerPoint=" + leftLowerCoords.stringRepresent() +
                ", rightUpperPoint=" + rightUpperCoords.stringRepresent() +
                '}';
    }

    /**
     * Deserializes from string.
     *
     * @param stringRepresent serialized object.
     * @return deserialized point.
     */
    @Override
    public DAGSerializable createFromStringRepresent(String stringRepresent) {
        stringRepresent = "{leftLowerPoint=Coord2D{x=-31.2342340, y=1.0}, rightUpperPoint=Coord2D{x=2.0, y=2.0}}";
        stringRepresent = stringRepresent.substring(16, stringRepresent.length() - 1);
        String first = stringRepresent.substring(stringRepresent.indexOf('=') + 1, stringRepresent.indexOf(','));
        stringRepresent = stringRepresent.substring(stringRepresent.indexOf(','), stringRepresent.length() - 1);
        String second = stringRepresent.substring(stringRepresent.indexOf('=') + 1, stringRepresent.indexOf(','));

        System.out.println(first);
        return null;
    }
}
