package java.tests;

import java.main.ATM;
import java.main.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * The test class AccountTest tests whether the methods return the correct values
 * @author Josue Villanueva
 */

/* TODO: Finalize Unit java.tests -> Not familiar enough with unit testing so will leave this for now
         Will add more slowly later
 */

class AccountTest {
    private ATM atm;
    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(10, 123, 100);
        atm.addAccount(10, account);
    }

    @Test
    void deposit() {

    }

    @Test
    void withdraw() {
    }

    @Test
    void getAccountId() {
    }

    @Test
    void getAccountPin() {
    }

    @Test
    void getAccountBalance() {
        Assertions.assertEquals(account.getAccountBalance(), 100);
    }

    @Test
    void getHistory() {
    }

    @Test
    void addTransactionHistory() {
    }

    @Test
    void isOverdrawn() {
    }

    @Test
    void testDeposit() {
    }

    @Test
    void testWithdraw() {
    }

    @Test
    void testGetAccountId() {
    }

    @Test
    void testGetAccountPin() {
    }

    @Test
    void testGetAccountBalance() {
    }

    @Test
    void testGetHistory() {
    }

    @Test
    void testAddTransactionHistory() {
    }

    @Test
    void testIsOverdrawn() {
    }
}
