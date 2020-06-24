package main;

import java.util.HashMap;

/**
 * @author Josue Villanueva
 * ATM class to execute transactions with accounts
 */
public class ATM {

    private final double OVERDRAFT_FEE = 5;
    private double atmBalance = 10000;
    private HashMap<Long, Account> accounts = new HashMap<>();
    private boolean isAuthorized;
    private long authorizedAccountId;

    /**
     * adds a new account the the accounts object with the given account_id and account object
     *
     * @param account_id is the account_id to be added
     * @param account    is the Account object to be added
     */
    public void addAccount(long account_id, Account account) {
        accounts.put(account_id, account);
    }

    /**
     * Return all accounts that are on the ATM (set accounts)
     *
     * @return return all accounts on ATM
     */
    public HashMap<Long, Account> getAccounts() {
        return this.accounts;
    }

    /**
     * Checks if an account with a given id exists
     *
     * @param account_id is the account_id to be checked for
     * @return whether the account exists or not
     */
    public boolean checkAccountExistence(long account_id) {
        return accounts.containsKey(account_id);
    }

    /**
     * Authorizes an account with given account_id and pin
     *
     * @param account_id is the account_id to be authorized
     * @param pin        is the account pin number
     */
    void authorize(long account_id, int pin) {
        if (accounts.get(account_id).getAccountPin() == pin) {
            setAuthorized(true);
            setAuthorizedAccountId(account_id);
        } else {
            setAuthorized(false);
        }
    }

    /**
     * @return the ATM overdraft fee constant
     */
    public double getATMOverdraftFee() {
        return this.OVERDRAFT_FEE;
    }

    /**
     * @return the authorized state of the account
     */
    boolean isAuthorized() {
        return this.isAuthorized;
    }

    /**
     * Sets the state of the account to authorized
     *
     * @param isAuthorized is the current state of the account
     */
    void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    /**
     * @return the ATM balance
     */
    double getATMBalance() {
        return this.atmBalance;
    }

    /**
     * Updates the balance of the ATM by adding or subtracting
     * from the balance
     *
     * @param amount is the transaction amount
     */
    void updateATMBalance(double amount) {
        atmBalance = atmBalance + amount;
    }

    /**
     * @return the account_id of the current authorized account
     */
    long getAuthorizedAccountId() {
        return this.authorizedAccountId;
    }

    /**
     * @param account_id is the account_id that was authorized successfully
     */
    void setAuthorizedAccountId(long account_id) {
        this.authorizedAccountId = account_id;
    }
}