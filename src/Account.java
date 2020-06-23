import java.sql.Timestamp;
import java.util.ArrayList;

class Account {
    private long account_id;
    private int pin;
    private double balance;
    private ArrayList<Transaction> history = new ArrayList<>();
    private boolean isOverdrawn = false;

    // Account constructor
    Account(long account_id, int pin, double balance) {
        this.account_id = account_id;
        this.pin = pin;
        this.balance = balance;
    }

    // deposit into an authorized account
    void deposit(double amount, ATM atm) {
        balance = balance + amount;
        atm.updateATMBalance(amount);
        addTransactionHistory(Util.getTimeStamp(), amount, balance);
    }

    // withdraw money from an authorized account
    void withdraw(double amount, ATM atm) {
        if (amount > balance) {
            balance = balance - atm.getATMOverdraftFee() - amount;
            isOverdrawn = true;
        } else {
            balance = balance - amount;
        }
        atm.updateATMBalance(-amount);
        addTransactionHistory(Util.getTimeStamp(), -amount, balance);
    }

    // get account id
    long getAccountId() {
        return this.account_id;
    }

    // get account pin number
    int getAccountPin() {
        return this.pin;
    }

    // get account balance
    double getBalance() {
        return this.balance;
    }

    // get transaction history from account
    ArrayList<Transaction> getHistory() {
        return this.history;
    }

    // add a new transaction history to the account
    void addTransactionHistory(Timestamp timestamp, double amount, double balance) {
        Transaction transaction = new Transaction(timestamp, amount, balance);
        history.add(transaction);
    }

    // get isOverdraw for the account
    boolean isOverdrawn() {
        return this.isOverdrawn;
    }
}