import java.util.Date;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

class Util {

    static Timestamp getTimeStamp() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }

    //updates the timer countdown for logging out after 2 mins without interaction
    static Timer updateTimer(Timer timer, ATM atm) {
        timer.cancel();
        timer = new Timer();
        timer.schedule(new ATMTask(atm), 10000L);
        return timer;
    }

    //TimerTask logs out the user by setting the authorized variable to false
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