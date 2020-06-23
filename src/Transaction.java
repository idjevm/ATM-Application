import java.sql.Timestamp;
import java.text.SimpleDateFormat;

class Transaction{
    private Timestamp timestamp;
    private double amount;
    private double balance;

    // Creating a timestamp when a transaction happens
    Transaction(Timestamp timestamp, double amount, double balance){
        this.timestamp = timestamp;
        this.amount = amount;
        this.balance = balance;
    }

    // gets a string for each individual transaction
    String getTransaction(){
        return String.format("%s %.2f %.2f", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(timestamp), amount, balance);
    }
}