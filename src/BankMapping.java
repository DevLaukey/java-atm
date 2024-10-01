/**
 * The BankMapping class is responsible for storing and managing the mapping
 * of bank identifiers to their respective bank names. It allows searching
 * for a bank name by its identifier and throws a custom exception if the bank
 * identifier is not found.
 *
 * This class handles the input of bank data and maintains two arrays:
 * - bankIds: an array of bank identifiers.
 * - bankNames: an array of corresponding bank names.
 */
public final class BankMapping {
    private final int[] bankIds;
    private final String[] bankNames;

    /**
     * Constructor that reads in bank identifiers and bank names from the InputScanner.
     * The input is read in pairs, where the first input is the bank ID and the second
     * input is the bank name. This is repeated 'numBanks' times.
     *
     * @param numBanks the number of banks to read in.
     * @param input the InputScanner used to read the input data.
     */
    public BankMapping(final int numBanks, final InputScanner input) {
        final int[] bankIds = new int[numBanks];
        final String[] bankNames = new String[numBanks];
        for (int i = 0; i < numBanks; i++) {
            bankIds[i] = input.nextIntLine();  // Read bank ID
            bankNames[i] = input.nextLine();   // Read bank name
        }
        this.bankIds = bankIds;
        this.bankNames = bankNames;
    }

    /**
     * The NoSuchBank class is a custom exception that is thrown when a requested
     * bank identifier is not found in the bankIds array.
     */
    public static final class NoSuchBank extends NoSuchException {
        private final int bankId;

        /**
         * Constructor that initializes the exception with the bank ID that was not found.
         *
         * @param bankId the bank ID that was not found.
         */
        public NoSuchBank(final int bankId) {
            this.bankId = bankId;
        }

        /**
         * @return the name of the exception ("Bank").
         */
        @Override
        public String getName() {
            return "Bank";
        }

        /**
         * @return the bank ID that caused the exception.
         */
        @Override
        public int getId() {
            return bankId;
        }
    }

    /**
     * Searches for the given bank ID in the bankIds array. If found, it returns the
     * corresponding bank name from the bankNames array. If not found, it throws a
     * NoSuchBank exception.
     *
     * @param bankId the bank identifier to search for.
     * @return the name of the bank associated with the bank ID.
     * @throws NoSuchBank if the bank ID is not found in the bankIds array.
     */
    public String getBankName(final int bankId) throws NoSuchBank {
        for (int i = 0; i < bankIds.length; i++) {
            if (bankIds[i] == bankId) {
                return bankNames[i];
            }
        }
        throw new NoSuchBank(bankId); // Bank ID not found
    }

}
