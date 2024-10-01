import java.util.InputMismatchException;

public class ATM {
    private BankMapping bankMapping;
    private AccountMapping accountMapping;
    private InputScanner inputScanner;

    public ATM() {
        bankMapping = new BankMapping();
        accountMapping = new AccountMapping();
        inputScanner = new InputScanner();
        loadInitialData();
    }

    private void loadInitialData() {
        // Initialize banks
        bankMapping.addBank(34, "American Express");
        bankMapping.addBank(55, "Diners Club");
        bankMapping.addBank(65, "Discover Card");
        bankMapping.addBank(40, "Visa");
        bankMapping.addBank(51, "Mastercard");

        // Initialize accounts
        accountMapping.addAccount(12345, 1650.32);
        accountMapping.addAccount(58903, 40900.8);
        accountMapping.addAccount(23785, 0.0);
        accountMapping.addAccount(12389, -900.45);
        accountMapping.addAccount(34896, 200.54);
    }

    public void start() {
        System.out.println("ATM is now on.");
        while (true) {
            try {
                System.out.print("Input Card Number: ");
                String cardNumber = inputScanner.nextLine();

                // Validate card number length
                if (cardNumber.length() != 8) {
                    throw new IllegalArgumentException("Card number must be length 8");
                }

                // Validate Luhn's algorithm
                if (!CreditCard.validateLuhn(cardNumber)) {
                    char checkDigit = cardNumber.charAt(7);
                    throw new IllegalArgumentException("Check digit, " + checkDigit + ", for card with number: " + cardNumber + " is invalid");
                }

                // Parse bank ID and account ID from card number
                int bankId = Integer.parseInt(cardNumber.substring(0, 2));
                int accountId = Integer.parseInt(cardNumber.substring(2, 7));

                // Retrieve bank name and account balance
                String bankName = bankMapping.getBankName(bankId);
                double balance = accountMapping.getAccountBalance(accountId);

                while (true) {
                    System.out.println(bankName + "| Account Balance: " + balance);
                    System.out.print("Enter desired action: deposit, withdraw, display, eject, exit\n");
                    String action = inputScanner.nextLine().toLowerCase();

                    switch (action) {
                        case "deposit":
                            System.out.print("Enter amount: ");
                            double depositAmount = inputScanner.nextDoubleLine();
                            if (depositAmount < 0) {
                                System.out.println("Deposit amount cannot be negative.");
                                break;
                            }
                            balance += depositAmount;
                            accountMapping.updateBalance(accountId, balance);
                            break;
                        case "withdraw":
                            System.out.print("Enter amount: ");
                            double withdrawAmount = inputScanner.nextDoubleLine();
                            if (withdrawAmount < 0) {
                                System.out.println("Withdraw amount cannot be negative.");
                                break;
                            }
                            if (withdrawAmount > balance) {
                                System.out.println("Insufficient funds.");
                                break;
                            }
                            balance -= withdrawAmount;
                            accountMapping.updateBalance(accountId, balance);
                            break;
                        case "display":
                            System.out.println(bankName + "| Account Balance: " + balance);
                            break;
                        case "eject":
                            System.out.println("Card ejected.");
                            throw new EjectCardException(); // Custom exception to eject card
                        case "exit":
                            System.out.println("Exiting ATM.");
                            System.exit(0);
                        default:
                            System.out.println("I do not recognize the command: " + action + ", please try again.");
                    }
                }
            } catch (NoSuchBankException | NoSuchAccountException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Invalid input type. Please enter numeric values where required.");
                inputScanner.nextLine(); // Clear the invalid input
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (EjectCardException e) {
                // Continue to prompt for card number
            }
        }
    }

    // Custom exception to handle card ejection
    private static class EjectCardException extends Exception {}
}
