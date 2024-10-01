/**
 * CreditCard class verifies a card number and extracts
 * the bankId, accountId, and check digit.
 * The class enforces two main checks:
 * 1. The card number must be exactly CARD_LENGTH long (8 digits).
 * 2. The card number must pass Luhn's algorithm to verify its validity.
 * If these conditions are not met, an IllegalArgumentException is thrown.
 */
public class CreditCard {
    // Constants
    private static final int CARD_LENGTH = 8;
    private static final int BANK_ID_LENGTH = 2;
    private static final int ACCOUNT_ID_LENGTH = 5;
    private static final String LENGTH_ERROR = "Card number must be length " + CARD_LENGTH;

    private final int bankId;
    private final int accountId;
    private final int checkDigit;

    /**
     * Constructor that verifies the card number's validity and initializes the bankId, accountId, and checkDigit.
     * The card number is checked for the correct length and must pass Luhn's algorithm.
     *
     * @param cardNum The credit card number to verify and process.
     * @throws IllegalArgumentException if the card number length is invalid or the check digit does not pass Luhn's algorithm.
     */
    public CreditCard(final int cardNum) {
        if (numDigits(cardNum) != CARD_LENGTH) {
            throw new IllegalArgumentException(LENGTH_ERROR);
        }

        int[] cardDigits = splitIntoDigits(cardNum);
        this.bankId = extractBankId(cardDigits);
        this.accountId = extractAccountId(cardDigits);
        this.checkDigit = cardDigits[CARD_LENGTH - 1]; // Last digit is the check digit

        // Validate the card number using Luhn's algorithm
        if (!verifyCheckDigit(cardNum)) {
            throw new IllegalArgumentException("Check digit, " + checkDigit +
                    ", for card with number: " + cardNum + " is invalid");
        }
    }

    public int getBankId() {
        return bankId;
    }

    public int getAccountId() {
        return accountId;
    }

    private static int digitsToInt(int[] digits) {
        int number = 0;
        for (int digit : digits) {
            number = number * 10 + digit;
        }
        return number;
    }

    private static int extractBankId(int[] cardNums) {
        int[] bankIdDigits = new int[BANK_ID_LENGTH];
        System.arraycopy(cardNums, 0, bankIdDigits, 0, BANK_ID_LENGTH);
        return digitsToInt(bankIdDigits);
    }

    private static int extractAccountId(int[] cardNums) {
        int[] accountIdDigits = new int[ACCOUNT_ID_LENGTH];
        System.arraycopy(cardNums, BANK_ID_LENGTH, accountIdDigits, 0, ACCOUNT_ID_LENGTH);
        return digitsToInt(accountIdDigits);
    }

    private static int[] splitIntoDigits(final int num) {
        int length = numDigits(num);
        int[] digits = new int[length];
        int temp = num;
        for (int i = length - 1; i >= 0; i--) {
            digits[i] = temp % 10;
            temp /= 10;
        }
        return digits;
    }

    private static int numDigits(final int num) {
        return (int) (Math.floor(Math.log10(num)) + 1);
    }

    /**
     * Verifies the card number's validity using Luhn's algorithm.
     *
     * @param cardNum The card number as an integer.
     * @return True if the check digit is valid, false otherwise.
     */
    private boolean verifyCheckDigit(final int cardNum) {
        String cardNumber = String.valueOf(cardNum);
        int sum = 0;
        boolean alternate = false;

        // Loop through the card number's digits in reverse order
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n;
            try {
                n = Integer.parseInt(cardNumber.substring(i, i + 1));
            } catch (NumberFormatException e) {
                return false; // Non-numeric character found
            }

            // Double every second digit
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9; // Correct the digit sum if greater than 9
                }
            }
            sum += n; // Accumulate the sum
            alternate = !alternate; // Toggle the alternate flag
        }

        return (sum % 10 == 0); // Check if the total modulo 10 is zero
    }
}
