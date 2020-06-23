import java.util.*;

class ATM{

    private final int OVERDRAFT_FEE = 5;
    private double atmBalance = 10000;
    private HashMap<Long, Account> accounts = new HashMap<>();
    private boolean isAuthorized;
    private String lastActivityTimestamp;



    void addAccount(long account_id, Account account){
        accounts.put(account_id, account);
    }

    HashMap<Long, Account> getAccounts(){
        return this.accounts;
    }

    boolean checkAccountExistence(long account_id){
        return accounts.containsKey(account_id);
    }

    void authorize(long account_id, int pin){
        if(accounts.get(account_id).getAccountPin() == pin) {
            setAuthorized(true);
            setLastActivityTimestamp(Util.getTimeStamp().toString());
        } else {
            setAuthorized(false);
        }
    }

    int getATMOverdraftFee() {
        return this.OVERDRAFT_FEE;
    }

    void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    boolean isAuthorized(){
        return this.isAuthorized;
    }

    double getATMBalance(){
        return this.atmBalance;
    }

    void updateATMBalance(double amount){
        atmBalance = atmBalance + amount;
    }

    void setLastActivityTimestamp(String lastActivityTimestamp) {
        this.lastActivityTimestamp = lastActivityTimestamp;
    }

    // TODO: use this to keep track of the last activity timestamp
    String getLastActivityTimestamp(){
        return this.lastActivityTimestamp;
    }
}