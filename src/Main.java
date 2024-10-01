import java.util.Scanner;

/**
 * The Main class is the entry point for the ATM system. It initializes the system
 * by creating instances of BankMapping, AccountMapping, and ATM, and starts the
 * interaction with the user.
 */
public class Main {
    public static void main(String[] args) {
        final InputScanner input = new InputScanner(new Scanner(System.in));

        // Read the number of banks and accounts
        final int numBanksAccounts = input.nextIntLine();

        // Initialize mappings for banks and accounts
        BankMapping bankMapping = new BankMapping(numBanksAccounts, input);
        AccountMapping accountMapping = new AccountMapping(numBanksAccounts, input);

        // Create the ATM instance and start the system
        ATM atm = new ATM(bankMapping, accountMapping, input);
        atm.start();
    }
}
