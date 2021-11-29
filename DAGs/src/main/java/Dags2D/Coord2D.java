package Dags2D;

import java.util.Objects;

public final class Coord2D {
    private final double x;
    private final double y;

    public Coord2D(double x, double y) {
        this.x = x;
        this.y = y;
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
        return Double.compare(otherCoord2D.x, x) == 0 && Double.compare(otherCoord2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
