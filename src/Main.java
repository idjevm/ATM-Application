import java.util.*;
import java.sql.Timestamp;

class Main {

    public static void main(String[] args) {
        final ATM atm = new ATM();
        final String AUTHORIZE = "authorize";
        final String WITHDRAW = "withdraw";
        final String DEPOSIT = "deposit";
        final String BALANCE = "balance";
        final String HISTORY = "history";
        final String LOGOUT = "logout";

        long account_id = 0;

        // set up accounts at this atm
        Account acc1 = new Account(1, 1, 10.24);
        atm.addAccount(1, acc1);
        Account acc2 = new Account(1434597300L, 4557, 90000.55);
        atm.addAccount(1434597300L, acc2);
        Account acc3 = new Account(7089382418L, 0075, 0.00);
        atm.addAccount(7089382418L, acc3);
        Account acc4 = new Account(2001377812L, 5950, 60.00);
        atm.addAccount(2001377812L, acc4);

        Scanner scanner = new Scanner(System.in); // Create a Scanner object
        System.out.println("Welcome to JV's ATM!");
        System.out.println("---------------------------------------------------");
        System.out.println("You can use the following valid arguments: \n");
        System.out.println("Please loggin with:             AUTHORIZE");
        System.out.println("For withdrawing money type:     WITHDRAW");
        System.out.println("For deposing money type:        DEPOSIT");
        System.out.println("For checking your balance type: BALANCE");
        System.out.println("For checking your history type: HISTORY");
        System.out.println("Please log out with:            LOGOUT");
        System.out.println("---------------------------------------------------");

        while (true) {
            String[] userInput = scanner.nextLine().trim().split("\\s+");
            String command = userInput[0];
            switch (command) {
                case AUTHORIZE:
                    if (userInput.length < 3) {
                        printErrorMessage(ErrorTypes.ARGS);
                        break;
                    }
                    account_id = Long.parseLong(userInput[1]);
                    int pin = Integer.parseInt(userInput[2]);

                    if (!atm.checkAccountExistence(account_id)) {
                        printErrorMessage(ErrorTypes.NONEXISTENT);
                        break;
                    }

                    authorize(account_id, pin, atm);
                    break;

                case WITHDRAW:
                    if (userInput.length < 2) {
                        printErrorMessage(ErrorTypes.ARGS);
                        break;
                    }
                    if (!atm.isAuthorized()) {
                        printErrorMessage(ErrorTypes.AUTH);
                    } else {
                        double withdrawAmount = Double.parseDouble(userInput[1]);
                        withdraw(account_id, withdrawAmount, atm);
                    }
                    break;

                case DEPOSIT:
                    if (userInput.length < 2) {
                        printErrorMessage(ErrorTypes.ARGS);
                        break;
                    }
                    if (!atm.isAuthorized()) {
                        printErrorMessage(ErrorTypes.AUTH);
                        break;
                    }
                        double depositAmount = Double.parseDouble(userInput[1]);
                        atm.getAccounts().get(account_id).deposit(depositAmount, atm);
                        System.out.println(String.format("Current balance: %.2f", atm.getAccounts().get(account_id).getBalance()));
                    break;

                case BALANCE:
                    if (!atm.isAuthorized()) {
                        printErrorMessage(ErrorTypes.AUTH);
                    }
                    printAccountBalance(atm, account_id);
                    break;

                case HISTORY:
                    if (!atm.isAuthorized()) {
                        printErrorMessage(ErrorTypes.AUTH);
                    }
                    printTransactionHistory(atm, account_id);
                    break;

                case LOGOUT:
                    if (atm.isAuthorized()) {
                        System.out.println("Account " + account_id + " logged out.");
                        atm.setAuthorized(false);
                    } else {
                        System.out.println("No account is currently authorized.");
                    }
                    break;
                default:
                    printErrorMessage(ErrorTypes.UNKNOWN_ARG);
            }
        }

    }

    // TODO: implement a method to handle the command? Create ENUM for commands

    // TODO: implement a method to handle user wrong input

    // print the account balance
    private static void printAccountBalance(ATM atm, long account_id) {
        System.out.println("Current balance: " + atm.getAccounts().get(account_id).getBalance());

    }

    // withdraw from account
    private static void withdraw(long account_id, double withdrawAmount, ATM atm) {
        if (atm.getATMBalance() == 0) {
            System.out.println("Unable to process your withdrawal at this time.");
        } else if (atm.getAccounts().get(account_id).isOverdrawn()) {
            System.out.println("Your account is overdrawn! You may not make withdrawals at this time.");
        }

        if (withdrawAmount % 20 == 0) {
            Timestamp timestamp = Util.getTimeStamp();
            atm.getAccounts().get(account_id).withdraw(withdrawAmount, atm);

            double account_balance = atm.getAccounts().get(account_id).getBalance();

            if (atm.getAccounts().get(account_id).isOverdrawn()) {
                System.out.println("Amount dispensed: " + withdrawAmount + "\n" + "You have been charged an overdraft fee of "
                        + atm.getATMOverdraftFee() + "$.  Current balance: " + account_balance);
            } else if (withdrawAmount > atm.getATMBalance()) {

                System.out.println("Unable to dispense full amount requested at this time.");
            } else {
                System.out.println("Amount dispensed: " + withdrawAmount + "\n" + "Current balance "
                        + account_balance);
            }
            atm.getAccounts().get(account_id).addTransactionHistory(timestamp, withdrawAmount, account_balance);
        } else {
            System.out.println("Please withdraw an other amount (suggestion: 20$, 40$,...).");
        }
    }

    // Authorize an account
    private static void authorize(long account_id, int pin, ATM atm) {
        atm.authorize(account_id, pin);

        if (atm.isAuthorized()) {
            System.out.println(account_id + " successfully authorized.");
        } else {
            printErrorMessage(ErrorTypes.AUTH_FAILED);
        }
    }

    // print transaction history
    private static void printTransactionHistory(ATM atm, long account_id) {
        ArrayList<Transaction> history = atm.getAccounts().get(account_id).getHistory();
        if (history.isEmpty()) {
            System.out.println("No history found.");
        } else {
            for (int i = history.size() - 1; i >= 0; i--) {
                System.out.println(history.get(i).getTransaction());
            }
        }
    }
    // Enum for Error types
    public enum ErrorTypes {
        AUTH("Authorization is required."),
        AUTH_FAILED("Authorization failed."),
        ARGS("Invalid number of arguments."),
        NONEXISTENT("The account does not exist."),
        UNKNOWN_ARG("You have entered an invalid command.");

        private final String description;

        ErrorTypes(String description) {
            this.description = description;
        }

        public String getErrorMessage(ErrorTypes errorType) {
            return String.format("%s Error: %s Please try again!", errorType, description);
        }
    }

    // print errors
    private static void printErrorMessage(ErrorTypes errorType) {
        switch (errorType) {
            case AUTH:
                System.out.println(ErrorTypes.AUTH.getErrorMessage(errorType));
                break;
            case AUTH_FAILED:
                System.out.println(ErrorTypes.AUTH_FAILED.getErrorMessage(errorType));
                break;
            case ARGS:
                System.out.println(ErrorTypes.ARGS.getErrorMessage(errorType));
                break;
            case NONEXISTENT:
                System.out.println(ErrorTypes.NONEXISTENT.getErrorMessage(errorType));
                break;
            case UNKNOWN_ARG:
                System.out.println(ErrorTypes.UNKNOWN_ARG.getErrorMessage(errorType));
                break;
            default:
                System.out.println("UNKNOWN ERROR");
        }
    }
}