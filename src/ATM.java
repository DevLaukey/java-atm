/**
 * The ATM class simulates a basic ATM system, allowing users to deposit, withdraw,
 * and view their account balance. The ATM interacts with both BankMapping to retrieve
 * bank details and AccountMapping to manage account balances.
 *
 * It continuously runs until the user chooses to exit, prompting for actions like
 * deposit, withdraw, display balance, eject card, or exit.
 */
public final class ATM {
    private final BankMapping bankMapping;
    private final AccountMapping accountMapping;
    private final InputScanner input;

    /**
     * Constructor for the ATM class. Initializes the BankMapping, AccountMapping, and InputScanner objects.
     *
     * @param bankMapping the BankMapping instance to retrieve bank details.
     * @param accountMapping the AccountMapping instance to manage account balances.
     * @param input the InputScanner to capture user input.
     */
    public ATM(final BankMapping bankMapping,
               final AccountMapping accountMapping,
               final InputScanner input) {
        this.bankMapping = bankMapping;
        this.accountMapping = accountMapping;
        this.input = input;
    }

    /**
     * The Action enum defines the available actions a user can perform at the ATM,
     * including DEPOSIT, WITHDRAW, DISPLAY, EJECT, EXIT, and ERROR for invalid input.
     */
    public enum Action {
        DEPOSIT, WITHDRAW, DISPLAY, EJECT, EXIT, ERROR;

        /**
         * Parses the user's input into one of the valid actions.
         * Returns ERROR if the input doesn't match any valid action.
         *
         * @param s the user input string.
         * @return the corresponding Action enum value.
         */
        public static Action parse(final String s) {
            return switch (s.toLowerCase()) {
                case "deposit"  -> DEPOSIT;
                case "withdraw" -> WITHDRAW;
                case "display"  -> DISPLAY;
                case "eject"    -> EJECT;
                case "exit"     -> EXIT;
                default         -> ERROR;
            };
        }
    }

    /**
     * Starts the ATM system by printing a welcome message and invoking the run method
     * to continuously accept user inputs until the session ends.
     */
    public void start() {
        System.out.println("ATM is now on.");
        run();
    }

    /**
     * This method is the core loop of the ATM system. It prompts the user for their card number,
     * validates their account, and continuously asks for actions (deposit, withdraw, display, eject, or exit).
     */
    private void run() {
        boolean running = true;
        while (running) {
            try {
                System.out.print("Input Card Number: ");
                int cardNum = input.nextIntLine();
                CreditCard cc = new CreditCard(cardNum);
                final int accountId = cc.getAccountId();
                // Call display to validate bank and account information
                display(bankMapping.getBankName(cc.getBankId()),
                        accountMapping.getAccountBalance(accountId));
                boolean inserted = true;
                while (inserted) {
                    System.out.println("Enter desired action: deposit, withdraw, display, eject, exit");
                    String userInput = input.nextLine();
                    switch (Action.parse(userInput)) {
                        case DEPOSIT -> deposit(accountId);
                        case WITHDRAW -> withdraw(accountId);
                        case DISPLAY -> display(bankMapping.getBankName(cc.getBankId()),
                                accountMapping.getAccountBalance(accountId));
                        case EJECT -> inserted = false;
                        case EXIT -> {
                            inserted = false;
                            running = false;
                        }
                        case ERROR -> System.out.println("I do not recognize the command: " + userInput + ", please try again.");
                    }
                }
            }
            catch (IllegalArgumentException | NoSuchException | AccountMapping.InsufficientFunds e) {
                System.out.println(e);
            }
        }
    }

    /**
     * This method facilitates depositing funds into the given account ID.
     * It prompts the user for the deposit amount, parses it, and then updates the account balance.
     *
     * @param accountId the ID of the account where the deposit is made.
     * @throws NoSuchException if the account does not exist.
     */
    private void deposit(final int accountId) throws NoSuchException {
        System.out.print("Enter amount to deposit: ");
        double amount = input.nextDoubleLine();
        if (amount <= 0) {
            System.out.println("Invalid deposit amount. Must be greater than zero.");
        } else {
            accountMapping.changeAccountAmount(accountId, amount);
            System.out.println("Successfully deposited: " + amount);
        }
    }

    /**
     * This method facilitates withdrawing funds from the given account ID.
     * It prompts the user for the withdrawal amount, parses it, and then attempts to deduct
     * the specified amount from the account balance.
     *
     * @param accountId the ID of the account from which the withdrawal is made.
     * @throws NoSuchException if the account does not exist.
     * @throws AccountMapping.InsufficientFunds if there are not enough funds in the account.
     */
    private void withdraw(final int accountId) throws NoSuchException {
        System.out.print("Enter amount to withdraw: ");
        double amount = input.nextDoubleLine();
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount. Must be greater than zero.");
        } else {
            try {
                accountMapping.changeAccountAmount(accountId, -amount);  // Negative for withdrawal
                System.out.println("Successfully withdrew: " + amount);
            } catch (AccountMapping.InsufficientFunds e) {
                System.out.println("Insufficient funds: " + e);
            }
        }
    }

    /**
     * Displays the bank name and the associated account balance.
     *
     * @param bankName the name of the bank.
     * @param accountBalance the balance of the account.
     */
    private void display(final String bankName, final double accountBalance) {
        System.out.println(bankName + " | Account Balance: " + accountBalance);
    }
}
