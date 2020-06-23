import java.util.HashMap;

class ATM {

    private final double OVERDRAFT_FEE = 5;
    private double atmBalance = 10000;
    private HashMap<Long, Account> accounts = new HashMap<>();
    private boolean isAuthorized;
    private long authorizedAccountId;

    void addAccount(long account_id, Account account) {
        accounts.put(account_id, account);
    }

    /**
     * Return all accounts that are on the ATM (set accounts)
     *
     * @return return all accounts on ATM
     */
    HashMap<Long, Account> getAccounts() {
        return this.accounts;
    }

    boolean checkAccountExistence(long account_id) {
        return accounts.containsKey(account_id);
    }

    void authorize(long account_id, int pin) {
        if (accounts.get(account_id).getAccountPin() == pin) {
            setAuthorized(true);
            setAuthorizedAccountId(account_id);
        } else {
            setAuthorized(false);
        }
    }

    double getATMOverdraftFee() {
        return this.OVERDRAFT_FEE;
    }

    void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    boolean isAuthorized() {
        return this.isAuthorized;
    }

    double getATMBalance() {
        return this.atmBalance;
    }

    void updateATMBalance(double amount) {
        atmBalance = atmBalance + amount;
    }

    void setAuthorizedAccountId(long account_id) {
        this.authorizedAccountId = account_id;
    }

    long getAuthorizedAccountId() {
        return this.authorizedAccountId;
    }
}