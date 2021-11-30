package Dags2D.exceptions;

import Dags2D.Origin;

/**
 * Exception to be thrown if origin does not contain any physical points.
 */
public class EmptyBoundsException extends Exception {
    private final Origin emptyOrigin;

    public EmptyBoundsException() {
        this(null);
    }

    public EmptyBoundsException(Origin emptyOrigin) {
        super();
        this.emptyOrigin = emptyOrigin;
    }

    @Override
    public String getMessage() {
        if (emptyOrigin == null) {
            return "Origin has no points";
        }

        return "Origin " + emptyOrigin + " has no points";
    }
}
