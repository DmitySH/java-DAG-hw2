package Dags2D.entities;

import Dags2D.interfaces.DAGSerializable;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple coordinates.
 */
public final class Coord2D implements Serializable, DAGSerializable {
    private static final double COMPARE_EPSILON = 0.000001d;

    private final double x;
    private final double y;

    /**
     * Coords constructor.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     */
    public Coord2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * X getter.
     *
     * @return x coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Y getter.
     *
     * @return y coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Offsets coord.
     *
     * @param x x-offset.
     * @param y y-offset.
     * @return new ofsetted coord.
     */
    public Coord2D offset(double x, double y) {
        return new Coord2D(this.x + x, this.y + y);
    }

    /**
     * Equals for coords.
     *
     * @param other other object.
     * @return true if coords have same location.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Coord2D otherCoord2D = (Coord2D) other;
        return Math.abs(otherCoord2D.x - x) < COMPARE_EPSILON && Math.abs(otherCoord2D.y - y) < COMPARE_EPSILON;
    }

    /**
     * Coord hashcode.
     *
     * @return hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Copies coord.
     *
     * @param copiable coord to copy.
     * @return copy of coord.
     */
    public static Coord2D makeCopy(Coord2D copiable) {
        return new Coord2D(copiable.x, copiable.y);
    }

    /**
     * Finds lower and left coordinates from two cords.
     *
     * @param first  first coord.
     * @param second second coord.
     * @return left-lower new coord.
     */
    public static Coord2D coordLeftLower(Coord2D first, Coord2D second) {
        if (first.x < second.x && first.y < second.y) {
            return first;
        } else if (first.x < second.x && second.y < first.y) {
            return new Coord2D(first.x, second.y);
        } else if (second.x < first.x && first.y < second.y) {
            return new Coord2D(second.x, first.y);
        } else {
            return second;
        }
    }

    /**
     * Finds upper and right coordinates from two cords.
     *
     * @param first  first coord.
     * @param second second coord.
     * @return right-upper new coord.
     */
    public static Coord2D coordRightUpper(Coord2D first, Coord2D second) {
        if (second.x < first.x && second.y < first.y) {
            return first;
        } else if (second.x < first.x && first.y < second.y) {
            return new Coord2D(first.x, second.y);
        } else if (first.x < second.x && second.y < first.y) {
            return new Coord2D(second.x, first.y);
        } else {
            return second;
        }
    }

    /**
     * String view of coord.
     *
     * @return string view.
     */
    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * Serialize-view for coord.
     *
     * @return string for deserializer.
     */
    @Override
    public String stringRepresent() {
        return "Coord2D" + this;
    }

    /**
     * Deserializes from string.
     *
     * @param stringRepresent serialized object.
     * @return deserialized coord.
     */
    public static DAGSerializable createFromStringRepresent(String stringRepresent) {
        Pattern pattern = Pattern.compile("x=.*?,");
        Matcher matcher = pattern.matcher(stringRepresent);
        matcher.find();
        String x = matcher.group();
        x = x.substring(2, x.length() - 1);

        pattern = Pattern.compile("y=.*?}");
        matcher = pattern.matcher(stringRepresent);
        matcher.find();
        String y = matcher.group();
        y = y.substring(2, y.length() - 1);

        return new Coord2D(Double.parseDouble(x), Double.parseDouble(y));
    }
}
