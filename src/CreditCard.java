public class CreditCard {
    private int bankId;
    private int accountId;
    private int checkDigit;

    public CreditCard(int bankId, int accountId, int checkDigit) {
        this.bankId = bankId;
        this.accountId = accountId;
        this.checkDigit = checkDigit;
    }

    /**
     * Validates the card number using Luhn's algorithm.
     * @param cardNumber The full card number as a string.
     * @return True if valid, false otherwise.
     */
    public static boolean validateLuhn(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n;
            try {
                n = Integer.parseInt(cardNumber.substring(i, i + 1));
            } catch (NumberFormatException e) {
                return false; // Non-numeric character found
            }
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    public int getBankId() {
        return bankId;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getCheckDigit() {
        return checkDigit;
    }
}
