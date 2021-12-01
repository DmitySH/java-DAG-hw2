package Dags2D.exceptions;

import Dags2D.Point;

/**
 * Exception to be thrown if origin does not contain any physical points.
 */
public class DAGConstraintException extends Exception {
    private final Point cycling;

    public DAGConstraintException() {
        this(null);
    }

    public DAGConstraintException(Point cycling) {
        super();
        this.cycling = cycling;
    }

    @Override
    public String getMessage() {
        if (cycling == null) {
            return "DAG has cycles";
        }

        return "Origin on" + cycling.getPosition() + " has cycles";
    }
}
