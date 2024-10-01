import java.util.HashMap;

public class BankMapping {
    private HashMap<Integer, String> bankMap;

    public BankMapping() {
        bankMap = new HashMap<>();
    }

    public void addBank(int id, String name) {
        bankMap.put(id, name);
    }

    public String getBankName(int id) throws NoSuchBankException {
        if (!bankMap.containsKey(id)) {
            throw new NoSuchBankException("No Such Bank With Id: " + id);
        }
        return bankMap.get(id);
    }
}
