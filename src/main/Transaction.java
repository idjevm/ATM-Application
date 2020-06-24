package main;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Transaction class to create a time stamp on a transaction
 *
 * @author Josue Villanueva
 */
public class Transaction {

    private Timestamp timestamp;
    private double transactionAmount;
    private double accountBalance;

    /**
     * Transaction constructor to create a new transaction obj
     *
     * @param timestamp         is the timestamp when the transaction occurred
     * @param transactionAmount is the amount of the transaction
     * @param accountBalance    is the updated balance of the account
     */
    public Transaction(Timestamp timestamp, double transactionAmount, double accountBalance) {
        this.timestamp = timestamp;
        this.transactionAmount = transactionAmount;
        this.accountBalance = accountBalance;
    }

    /**
     * @return the formatted transaction string <date> <time> <transaction_amount> and <balance>
     */
    public String getTransaction() {
        return String.format("%s %.2f %.2f", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(timestamp), transactionAmount,
                accountBalance);
    }
}