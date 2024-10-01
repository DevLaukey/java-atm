import java.util.Scanner;

/**
 * The InputScanner class wraps a Scanner object and provides
 * utility methods for reading input in a simplified manner.
 * It ensures that newline characters are properly handled after
 * reading integers and doubles to avoid input issues.
 *
 * This class provides three methods:
 * - nextLine(): Reads a full line of text input.
 * - nextIntLine(): Reads an integer and consumes the remaining newline.
 * - nextDoubleLine(): Reads a double and consumes the remaining newline.
 *
 * Usage:
 *   InputScanner scanner = new InputScanner(new Scanner(System.in));
 *   int number = scanner.nextIntLine();
 *   String text = scanner.nextLine();
 *
 */
public class InputScanner {
    private final Scanner input;

    /**
     * Constructs an InputScanner with a given Scanner.
     *
     * @param input the Scanner object used to read input.
     */
    public InputScanner(Scanner input) {
        this.input = input;
    }

    /**
     * Reads and returns the next line of text input.
     *
     * @return the next line of text input.
     */
    public String nextLine() {
        return input.nextLine();
    }

    /**
     * Reads and returns the next integer from input and consumes the newline character.
     *
     * @return the next integer input.
     * @throws NumberFormatException if the input is not a valid integer.
     */
    public int nextIntLine() {
        return Integer.parseInt(input.nextLine());
    }

    /**
     * Reads and returns the next double from input and consumes the newline character.
     *
     * @return the next double input.
     * @throws NumberFormatException if the input is not a valid double.
     */
    public double nextDoubleLine() {
        return Double.parseDouble(input.nextLine());
    }
}
