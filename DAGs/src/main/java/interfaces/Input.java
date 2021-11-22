package interfaces;

public interface Input {
    /**
     * From string int parser.
     */
    int parseIntFromString(String input, int from, int to);

    /**
     * Boolean parser.
     */
    boolean enterBoolean(String prompt, String errorMessage, int attempts);

    /**
     * Cell parser.
     */
    int[] enterCell(int from1, int to1, int from2, int to2,
                    String prompt, String errorMessage, int attempts, String action1, String action2);

    /**
     * Int parser.
     */
    int parseInt(int from, int to, String prompt, String errorMessage, int attempts);

    /**
     * Get line.
     */
    String getLine();
}
