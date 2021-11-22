package utilities;


import interfaces.Input;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

public final class InputHelper implements Input {
    private static InputHelper instance;

    private PrintStream out;
    private Scanner in;

    private InputHelper() {
        in = new Scanner(System.in);
        out = System.out;
    }

    /**
     * Creates instance of singleton.
     *
     * @return instance of InputHelper.
     */
    public static InputHelper getInstance() {
        if (instance == null) {
            instance = new InputHelper();
        }

        return instance;
    }

    /**
     * Changes input stream.
     *
     * @param inStream new input stream.
     */
    public void setIn(InputStream inStream) {
        this.in = new Scanner(inStream);
    }

    /**
     * Changes output stream.
     *
     * @param outStream new output stream.
     */
    public void setOut(PrintStream outStream) {
        this.out = outStream;
    }

    /**
     * Tries to get integer from string.
     *
     * @param input string to get.
     * @param from  left limit.
     * @param to    right limit.
     * @return parsed integer.
     */
    public int parseIntFromString(String input, int from, int to) {
        int res = Integer.parseInt(input);
        if (!(res >= from && res <= to)) {
            throw new NumberFormatException("Incorrect input!");
        }

        return res;
    }

    /**
     * Gets boolean from input.
     *
     * @param prompt       prompt to out.
     * @param errorMessage error to out.
     * @param attempts     max number of incorrect input.
     * @return parsed boolean.
     */
    public boolean enterBoolean(String prompt, String errorMessage, int attempts) {
        out.print(prompt);
        String input;
        boolean isCorrect = false;
        int current_attempt = 0;
        boolean res = true;

        while (!isCorrect) {
            if (current_attempt >= attempts) {
                throw new NumberFormatException("You did not enter yes or no!");
            }
            input = in.nextLine();
            if (input.toLowerCase(Locale.ROOT).equals("yes")) {
                isCorrect = true;
            } else if (input.toLowerCase(Locale.ROOT).equals("no")) {
                isCorrect = true;
                res = false;
            } else {
                out.print(errorMessage);
            }
            ++current_attempt;
        }

        return res;
    }

    /**
     * @param from1        first left limit.
     * @param to1          first right limit.
     * @param from2        second left limit.
     * @param to2          second right limit.
     * @param prompt       prompt to out.
     * @param errorMessage message to out.
     * @param attempts     max number of incorrect input.
     * @param action1      input to break.
     * @return array with coordinates and may be torpedoes info.
     */
    public int[] enterCell(int from1, int to1, int from2, int to2,
                           String prompt, String errorMessage, int attempts, String action1, String action2) {
        out.print(prompt);
        String[] input;
        int current_attempt = 0;
        int[] res = null;
        boolean isCorrect = false;

        while (!isCorrect) {
            if (current_attempt >= attempts) {
                throw new NumberFormatException("You did not enter correct pair!");
            }
            try {
                String rawInput = in.nextLine();
                if (rawInput.toLowerCase(Locale.ROOT).equals(action1)) {
                    return new int[]{-1, -1, -1};
                }
                if (rawInput.toLowerCase(Locale.ROOT).equals(action2)) {
                    return new int[]{-2, -2, -2};
                }
                input = rawInput.split(" ");
                res = new int[input.length];

                if (input.length != 2 && input.length != 3) {
                    throw new NumberFormatException("You did not enter correct coordinates!");
                }
                res[0] = input[input.length / 3].charAt(0) - 'a';
                res[1] = Integer.parseInt(input[1 + input.length / 3]) - 1;

                isCorrect = isCorrectCell(from1, to1, from2, to2, errorMessage, input, res, isCorrect);
            } catch (NumberFormatException ex) {
                out.print(errorMessage);
            }
            ++current_attempt;
        }

        return res;
    }

    /**
     * Checks if cell is correct.
     */
    private boolean isCorrectCell(int from1, int to1, int from2, int to2,
                                  String errorMessage, String[] input, int[] res, boolean isCorrect) {
        if (res[1] >= from2 && res[1] <= to2 && input[0].length() == 1
                && res[0] >= from1 && res[0] <= to1) {
            isCorrect = true;
            if (input.length == 3) {
                if (input[0].equals("T")) {
                    res[2] = 1;
                } else {
                    isCorrect = false;
                    out.print(errorMessage);
                }
            }
        } else {
            out.print(errorMessage);
        }
        return isCorrect;
    }

    /**
     * Gets integer from input.
     *
     * @param from         left limit.
     * @param to           right limit.
     * @param prompt       prompt to out.
     * @param errorMessage error to out.
     * @param attempts     max number of incorrect input.
     * @return introduced integer.
     */
    public int parseInt(int from, int to, String prompt, String errorMessage, int attempts) {
        out.print(prompt);
        String input;

        int current_attempt = 0;
        int res = 0;
        boolean isCorrect = false;

        while (!isCorrect) {
            if (current_attempt >= attempts) {
                throw new NumberFormatException("You did not enter correct number!");
            }

            try {
                input = in.nextLine();
                res = Integer.parseInt(input);
                if (res >= from && res <= to) {
                    isCorrect = true;
                } else {
                    out.print(errorMessage);
                }
            } catch (NumberFormatException ex) {
                out.print(errorMessage);
            }
            ++current_attempt;
        }

        return res;
    }

    /**
     * Gets new line.
     *
     * @return next line.
     */
    public String getLine() {
        return in.nextLine();
    }
}

