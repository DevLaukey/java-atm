/**
 * The AccountMapping class stores and manages the mapping between account identifiers
 * and their respective balances. It provides functionality for retrieving balances,
 * modifying account amounts (for deposits or withdrawals), and checking if an account
 * has sufficient funds.
 *
 * It throws custom exceptions in cases where the account ID is not found, or if there
 * are insufficient funds during a withdrawal.
 */
public final class AccountMapping {
    private final int[] accountIds;
    private final double[] accountBalances;

    /**
     * Constructor that reads account identifiers and their corresponding balances
     * from the InputScanner. The input is read in pairs where the first input
     * is the account ID and the second input is the balance.
     *
     * @param numAccounts the number of accounts to read in.
     * @param input the InputScanner used to read the input data.
     */
    public AccountMapping(final int numAccounts, final InputScanner input) {
        accountIds = new int[numAccounts];
        accountBalances = new double[numAccounts];
        for (int i = 0; i < numAccounts; i++) {
            accountIds[i] = input.nextIntLine();  // Read account ID
            accountBalances[i] = input.nextDoubleLine();  // Read account balance
        }
    }

    /**
     * NoSuchAccount is a custom exception that is thrown when an account identifier
     * is not found in the accountIds array.
     */
    public static final class NoSuchAccount extends NoSuchException {
        private final int accountId;

        /**
         * Constructor that initializes the exception with the account ID that was not found.
         *
         * @param accountId the account ID that was not found.
         */
        public NoSuchAccount(final int accountId) {
            this.accountId = accountId;
        }

        /**
         * @return the name of the exception ("Account").
         */
        @Override
        public String getName() {
            return "Account";
        }

        /**
         * @return the account ID that caused the exception.
         */
        @Override
        public int getId() {
            return accountId;
        }
    }

    /**
     * Retrieves the account balance associated with a given account ID.
     *
     * @param accountId the account identifier.
     * @return the balance of the account.
     * @throws NoSuchAccount if the account ID is not found.
     */
    public double getAccountBalance(final int accountId) throws NoSuchAccount {
        int index = findAccount(accountId);  // Find the index of the account ID
        return accountBalances[index];  // Return the corresponding balance
    }

    /**
     * InsufficientFunds is a custom runtime exception thrown when an attempt is made
     * to withdraw an amount that exceeds the current account balance.
     */
    public final class InsufficientFunds extends RuntimeException {
        private final String message;

        /**
         * Constructor that creates an exception indicating insufficient funds for the withdrawal.
         *
         * @param accountId the account ID that has insufficient funds.
         * @param amount the amount that was attempted to withdraw.
         * @throws NoSuchAccount if the account does not exist.
         */
        public InsufficientFunds(final int accountId, final double amount) throws NoSuchAccount {
            this.message = "INSUFFICIENT FUNDS: Attempted to withdraw " + amount +
                    " from account with balance of " + getAccountBalance(accountId);
        }

        @Override
        public String toString() {
            return message;
        }
    }

    /**
     * Changes the balance of a given account by a specified amount. If the account does not
     * have sufficient funds for a withdrawal, an InsufficientFunds exception is thrown.
     *
     * @param accountId the account identifier.
     * @param amount the amount to change the balance by (negative for withdrawal, positive for deposit).
     * @throws NoSuchAccount if the account ID is not found.
     * @throws InsufficientFunds if there are insufficient funds for a withdrawal.
     */
    public void changeAccountAmount(final int accountId, final double amount) throws NoSuchAccount, InsufficientFunds {
        int index = findAccount(accountId);  // Find the index of the account ID
        double newBalance = accountBalances[index] + amount;  // Calculate new balance

        if (newBalance < 0) {
            throw new InsufficientFunds(accountId, amount);  // Throw exception if funds are insufficient
        }

        accountBalances[index] = newBalance;  // Update balance
    }

    /**
     * Searches for the given account ID in the accountIds array. If found, returns the index
     * of the account ID in the array. If not found, throws a NoSuchAccount exception.
     *
     * @param accountId the account identifier.
     * @return the index of the account in the accountIds array.
     * @throws NoSuchAccount if the account ID is not found.
     */
    private int findAccount(final int accountId) throws NoSuchAccount {
        for (int i = 0; i < accountIds.length; i++) {
            if (accountIds[i] == accountId) {
                return i;  // Return index if account ID is found
            }
        }
        throw new NoSuchAccount(accountId);  // Throw exception if account ID is not found
    }
}
