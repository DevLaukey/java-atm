import java.util.HashMap;

public class AccountMapping {
    private HashMap<Integer, Double> accountMap;

    public AccountMapping() {
        accountMap = new HashMap<>();
    }

    public void addAccount(int id, double balance) {
        accountMap.put(id, balance);
    }

    public double getAccountBalance(int id) throws NoSuchAccountException {
        if (!accountMap.containsKey(id)) {
            throw new NoSuchAccountException("No Such Account With Id: " + id);
        }
        return accountMap.get(id);
    }

    public void updateBalance(int id, double newBalance) {
        accountMap.put(id, newBalance);
    }
}
