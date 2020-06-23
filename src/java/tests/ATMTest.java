package java.tests;

import java.main.ATM;
import java.main.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class ATMTest tests whether the methods return the correct values
 * @author Josue Villanueva
 */

/* TODO: Finalize Unit java.tests -> Not familiar enough with unit testing so will leave this for now
         Will add more slowly later
 */
class ATMTest {

    private ATM atm;
    @BeforeEach
    void setUp() {
        atm = new ATM();
        atm.addAccount(1, new Account(1, 4000, 20));
        atm.addAccount(2, new Account(2, 4000, 20));
    }

    @Test
    void getAccounts() {
        Assertions.assertFalse(atm.getAccounts().isEmpty());
    }

    @Test
    void checkAccountExistence() {
        Assertions.assertTrue(atm.checkAccountExistence(1));
        Assertions.assertTrue(atm.checkAccountExistence(2));
        Assertions.assertFalse(atm.checkAccountExistence(3));
    }

    @Test
    void authorize() {

    }
    @Test
    void getATMOverdraftFee() {
        Assertions.assertEquals(atm.getATMOverdraftFee(), 5);
    }

    @Test
    void setAuthorized() {
    }

    @Test
    void isAuthorized() {
    }

    @Test
    void getATMBalance() {
    }

    @Test
    void updateATMBalance() {
    }

    @Test
    void setAuthorizedAccountId() {
    }

    @Test
    void getAuthorizedAccountId() {
    }
}