package java.tests;

import java.main.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The test class ATMTest java.tests whether the methods return the correct values
 * @author Josue Villanueva
 */

class TransactionTest {

    private Transaction transaction;
    private Date date = new Date();
    private Timestamp timestamp = new Timestamp(date.getTime());
    @BeforeEach
    void setUp() {
        transaction = new Transaction(timestamp, 10, 20);
    }

    @Test
    void getTransaction() {
        assertEquals(transaction.getTransaction(), String.format("%s %.2f %.2f", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(timestamp), 10.00, 20.00));
    }
}