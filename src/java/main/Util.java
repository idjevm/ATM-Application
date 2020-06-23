package java.main;

import java.util.Date;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Josue Villanueva
 * Util class used to add some utilities to be used throughout the other objects and Main
 */
class Util {

    /**
     * Static method to get a timestamp
     * @return a new timestamp
     */
    static Timestamp getTimeStamp() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }

    /**
     * Updates the timer countdown for logging out after 2 mins without interaction
     * @param timer is the current timer
     * @param atm is the AMT object
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
}