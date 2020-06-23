package main;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Josue Villanueva
 * Util class used to add some utilities to be used throughout the other objects and Main
 */
class Util {

    /**
     * Static method to get a timestamp
     *
     * @return a new timestamp
     */
    static Timestamp getTimeStamp() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }

    /**
     * Updates the timer countdown for logging out after 2 mins without interaction
     *
     * @param timer is the current timer
     * @param atm   is the AMT object
     * @return a newly created timer when an action is executed
     */
    static Timer updateTimer(Timer timer, ATM atm) {
        timer.cancel();
        timer = new Timer();
        timer.schedule(new ATMTask(atm), 10000L);
        return timer;
    }

    /**
     * ATMTask that inherits TimerTask to be able to build a new ATM constructor
     * Logs out the user by setting the authorized state to false
     * NOTE: only will be executed if there are no interactions for 2 mins
     */
    static class ATMTask extends TimerTask {
        ATM atm;

        ATMTask(ATM atm) {
            this.atm = atm;
        }

        public void run() {
            atm.setAuthorized(false);
            System.out.println("You have been locked out automatically.");
        }
    }

    private static final String FILE_HEADER = "ACCOUNT_ID,PIN,BALANCE";
    private static final String DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String FILE_PATH_NAME = "/Users/idjevm/Desktop/GitHub/takeofftechnologies/src/data/accounts.csv";

    // method to write to file once the user enters logout command
    static void writeAccountsToCSV(ATM atm) {
        try {
            FileWriter fw = new FileWriter(FILE_PATH_NAME);
            fw.append(FILE_HEADER);
            fw.append(NEW_LINE_SEPARATOR);
            atm.getAccounts().forEach((account_id, account) -> {
                try {
                    fw.append(String.format("%o%s%o%s%.2f", account.getAccountId(), DELIMITER,
                            account.getAccountPin(), DELIMITER, account.getAccountBalance()));
                    fw.append(NEW_LINE_SEPARATOR);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // method to load csv file into memory
    static void loadAccountsFromCSV(ATM atm) {
        try {
            //parsing a CSV file into Scanner class constructor
            Scanner sc = new Scanner(new File(FILE_PATH_NAME));

            // reads the first line from csv that contains the headers so the scanner will after this read next ln
            String headers = sc.nextLine();

            while (sc.hasNext())  //returns a boolean value -> true as long as there is a new line
            {
                String[] accountCSVStr = sc.next().trim().split(DELIMITER);
                long account_id = Long.parseLong(accountCSVStr[0]);
                int account_pin = Integer.parseInt(accountCSVStr[1]);
                double account_balance = Double.parseDouble(accountCSVStr[2]);
                boolean isOverdrawn = account_balance < 0;
                atm.addAccount(account_id, new Account(account_id, account_pin, account_balance, isOverdrawn));
            }
            sc.close();  //closes the scanner
        } catch (Exception e) {
            e.getMessage();
        }
    }
}