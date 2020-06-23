package java.main;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * This is the Account class used by ATM to process account related transactions
 * @author Josue Villanueva (idjevm)
 */
public class Account {
    private long account_id;
    private int account_pin;
    private double account_balance;
    private ArrayList<Transaction> history = new ArrayList<>();
    private boolean isOverdrawn = false;

    /**
     *
     * * Class constructor to create Account objects
     * @param account_id is the account_id
     * @param account_pin is the account pin
     * @param account_balance is the initial balance of the account
     */
    public Account(long account_id, int account_pin, double account_balance) {
        this.account_id = account_id;
        this.account_pin = account_pin;
        this.account_balance = account_balance;
    }

    /**
     * Deposits a given amount into the authorized account on the ATM
     * @param amount is the amount to deposit
     * @param atm is the java.main.ATM object
     */
    public void deposit(double amount, ATM atm) {
        account_balance = account_balance + amount;
        atm.updateATMBalance(amount);
        addTransactionHistory(Util.getTimeStamp(), amount, account_balance);
    }

    /**
     * Withdraws a given amount from the authorized account on the ATM
     * @param amount is the amount to be withdrawn
     * @param atm is the java.main.ATM object
     */
    public void withdraw(double amount, ATM atm) {
        if (amount > account_balance) {
            account_balance = account_balance - atm.getATMOverdraftFee() - amount;
            isOverdrawn = true;
        } else {
            account_balance = account_balance - amount;
        }
        atm.updateATMBalance(-amount);
        addTransactionHistory(Util.getTimeStamp(), -amount, account_balance);
    }

    /**
     * Gets the account id from account
     * @return the account_id from account
     */
    long getAccountId() {
        return this.account_id;
    }

    /**
     * Gets the pin number from account
     * @return the account pin number
     */
    int getAccountPin() {
        return this.account_pin;
    }

    /**
     * Get the balance of the account
     * @return the account balance
     */
    public double getAccountBalance() {
        return this.account_balance;
    }

    /**
     * Get account history from Account
     * @return the account history ArrayList
     */
    ArrayList<Transaction> getHistory() {
        return this.history;
    }

    /**
     *  Adds a new transaction  to the history arraylist
     * @param timestamp is the timestamp that the transaction occurred
     * @param amount is the amount of the transaction
     * @param balance is the updated balance of the account
     */
    void addTransactionHistory(Timestamp timestamp, double amount, double balance) {
        Transaction transaction = new Transaction(timestamp, amount, balance);
        history.add(transaction);
    }

    /**
     *  Returns the overdrawn state of the account
     * @return the overdrawn state from account
     */
    boolean isOverdrawn() {
        return this.isOverdrawn;
    }
}