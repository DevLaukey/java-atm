import java.util.Scanner;

public class InputScanner {
    private Scanner scanner;

    public InputScanner() {
        this.scanner = new Scanner(System.in);
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    public int nextIntLine() {
        int value = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        return value;
    }

    public double nextDoubleLine() {
        double value = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline
        return value;
    }
}
