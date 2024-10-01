// Abstract parent exception class
public abstract class NoSuchException extends Exception {
    public NoSuchException(String message) {
        super(message);
    }
}

// Custom exception for non-existent banks
class NoSuchBankException extends NoSuchException {
    public NoSuchBankException(String message) {
        super(message);
    }
}

// Custom exception for non-existent accounts
class NoSuchAccountException extends NoSuchException {
    public NoSuchAccountException(String message) {
        super(message);
    }
}
