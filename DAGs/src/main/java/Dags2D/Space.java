package Dags2D;

public final class Space {
    private static Space instance;

    Origin root;

    private Space(Origin origin) {
        root = origin;
    }

    /**
     * Creates instance of singleton.
     *
     * @param origin origin.
     * @return instance of Space.
     */
    public static Space getInstance(Origin origin) {
        if (instance == null) {
            instance = new Space(origin);
        }

        return instance;
    }
}
