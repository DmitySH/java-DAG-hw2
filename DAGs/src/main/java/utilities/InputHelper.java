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
    @Override
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
    @Override
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
     * Gets integer from input.
     *
     * @param from         left limit.
     * @param to           right limit.
     * @param prompt       prompt to out.
     * @param errorMessage error to out.
     * @param attempts     max number of incorrect input.
     * @return introduced integer.
     */
    @Override
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
    @Override
    public String getLine() {
        return in.nextLine();
    }
}

