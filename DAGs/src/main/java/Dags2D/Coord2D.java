package Dags2D;

import java.io.Serializable;
import java.util.Objects;

public final class Coord2D implements Serializable {
    private static final double COMPARE_EPSILON = 0.000001d;

    private final double x;
    private final double y;

    public Coord2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Coord2D offset(double x, double y) {
        return new Coord2D(this.x + x, this.y + y);
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static Coord2D makeCopy(Coord2D copiable) {
        return new Coord2D(copiable.x, copiable.y);
    }

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

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
