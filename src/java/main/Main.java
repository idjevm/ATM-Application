package java.main;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

/**
 * Simple ATM terminal application
 *
 * @author Josue Villanueva
 */
class Main {

    public static void main(String[] args) {

        Timer timer = new Timer();

        // set up initial accounts at this atm
        Account test1 = new Account(1, 1, 10.24);
        Account test2 = new Account(123, 123, 200);
        Account acc1 = new Account(1434597300L, 4557, 90000.55);
        Account acc2 = new Account(7089382418L, 0075, 0.00);
        Account acc3 = new Account(2001377812L, 5950, 60.00);

        // Adding accounts to the atm with anonymous subclass
        final ATM atm = new ATM() {
            {
                addAccount(1, test1);
                addAccount(123, test2);
                addAccount(1434597300L, acc1);
                addAccount(7089382418L, acc2);
                addAccount(2001377812L, acc3);
            }
        };

        Scanner scanner = new Scanner(System.in); // Create a Scanner object
        System.out.println("Welcome to JV's ATM!");
        printMenu();

        while (true) {
            String[] userInput = scanner.nextLine().trim().split("\\s+");
            String commandStr = userInput[0];
            switch (CommandsEnum.getEnum(commandStr)) {
                case AUTHORIZE:
                    if (atm.isAuthorized()) {
                        timer = Util.updateTimer(timer, atm);
                        printErrorMessage(ErrorTypes.ALREADY_AUTHORIZED);
                        break;
                    } else if (userInput.length < 3) {
                        printErrorMessage(ErrorTypes.ARGS);
                        break;
                    }

                    try {
                        long account_id = Long.parseLong(userInput[1]);
                        int pin = Integer.parseInt(userInput[2]);

                        if (!atm.checkAccountExistence(account_id)) {
                            printErrorMessage(ErrorTypes.NONEXISTENT);
                            break;
                        }
                        if (authorizeSuccess(account_id, pin, atm)) {
                            timer = Util.updateTimer(timer, atm);
                            System.out.println(account_id + " successfully authorized.");
                        } else {
                            printErrorMessage(ErrorTypes.AUTH_FAILED);
                        }

                    } catch (Exception e) {
                        printErrorMessage(ErrorTypes.INVALID_INPUT);
                    }
                    break;

                case WITHDRAW:
                    if (userInput.length < 2) {
                        if (atm.isAuthorized()) {
                            timer = Util.updateTimer(timer, atm);
                        }
                        printErrorMessage(ErrorTypes.ARGS);
                        break;
                    }
                    if (!atm.isAuthorized()) {
                        printErrorMessage(ErrorTypes.AUTH);
                    } else {
                        timer = Util.updateTimer(timer, atm);
                        try {
                            double withdrawAmount = Double.parseDouble(userInput[1]);
                            withdraw(withdrawAmount, atm);
                        } catch (Exception e) {
                            printErrorMessage(ErrorTypes.INVALID_INPUT);
                        }
                    }
                    break;

                case DEPOSIT:
                    if (userInput.length < 2) {
                        if (atm.isAuthorized()) {
                            timer = Util.updateTimer(timer, atm);
                        }
                        printErrorMessage(ErrorTypes.ARGS);
                        break;
                    }
                    if (!atm.isAuthorized()) {
                        printErrorMessage(ErrorTypes.AUTH);
                        break;
                    }
                    timer = Util.updateTimer(timer, atm);
                    try {
                        double depositAmount = Double.parseDouble(userInput[1]);
                        atm.getAccounts().get(atm.getAuthorizedAccountId()).deposit(depositAmount, atm);
                        atm.updateATMBalance(depositAmount);
                        System.out.println(formatAccountBalanceStr(getAccountBalance(atm)));
                    } catch (Exception e) {
                        printErrorMessage(ErrorTypes.INVALID_INPUT);
                    }
                    break;

                case BALANCE:
                    if (!atm.isAuthorized()) {
                        printErrorMessage(ErrorTypes.AUTH);
                        break;
                    }
                    timer = Util.updateTimer(timer, atm);
                    System.out.println(formatAccountBalanceStr(getAccountBalance(atm)));
                    break;

                case HISTORY:
                    if (!atm.isAuthorized()) {
                        printErrorMessage(ErrorTypes.AUTH);
                        break;
                    }
                    timer = Util.updateTimer(timer, atm);
                    printTransactionHistory(atm);
                    break;

                case LOGOUT:
                    if (!atm.isAuthorized()) {
                        System.out.println("No account is currently authorized.");
                    }
                    printLogoutMessage(atm);
                    break;
                case HELP:
                    timer = Util.updateTimer(timer, atm);
                    printMenu();
                    break;
                case END:
                    System.out.println("Thank you so much for using JV's ATM! Exiting now, Goodbye");
                    System.exit(0);
                default:
                    if (atm.isAuthorized()) {
                        timer = Util.updateTimer(timer, atm);
                    }
                    printErrorMessage(ErrorTypes.UNKNOWN_ARG);
            }
        }
    }

    private static void printMenu() {
        System.out.println("-------------------------------------------------------------");
        System.out.println("You can use the following commands with <arguments>: \n");
        System.out.println("To login into your account use:  authorize <account_id> <pin>");
        System.out.println("To withdraw money use:          withdraw <amount>");
        System.out.println("To deposit money use:           deposit <amount>");
        System.out.println("To check your balance use:      balance");
        System.out.println("To checking your history use:   history");
        System.out.println("To logout of your account use:  logout");
        System.out.println("To print this menu:             help");
        System.out.println("-------------------------------------------------------------");
    }

    // ENUM for commands, will return unknown if requested enum does not exist
    public enum CommandsEnum {

        AUTHORIZE("authorize"), WITHDRAW("withdraw"), DEPOSIT("deposit"),
        BALANCE("balance"), HISTORY("history"), LOGOUT("logout"),
        HELP("help"), END("end"), UNKNOWN_COMMAND("unknown_command");

        private String value;

        CommandsEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.getValue();
        }

        // Creating this method to return UNKNOWN if requested enum is not present
        public static CommandsEnum getEnum(String value) {
            for (CommandsEnum enumCommand : values())
                if (enumCommand.getValue().equalsIgnoreCase(value))
                    return enumCommand;
            return UNKNOWN_COMMAND;
        }
    }

    // format the account balance string
    private static String formatAccountBalanceStr(double account_balance) {
        return String.format("Current balance: $%.2f", account_balance);
    }

    // get account balance
    private static double getAccountBalance(ATM atm) {
        return atm.getAccounts().get(atm.getAuthorizedAccountId()).getAccountBalance();
    }

    // withdraw from account
    private static void withdraw(double withdrawAmount, ATM atm) {
        if (atm.getATMBalance() == 0) {
            System.out.println("Unable to process your withdrawal at this time.");
        }
        if (!atm.getAccounts().get(atm.getAuthorizedAccountId()).isOverdrawn()) {
            if (withdrawAmount > atm.getATMBalance()) {
                System.out.println("Unable to dispense full amount requested at this time.");
                withdraw(atm.getATMBalance(), atm);
            } else if (withdrawAmount % 20 == 0) {
                atm.getAccounts().get(atm.getAuthorizedAccountId()).withdraw(withdrawAmount, atm);

                double account_balance = getAccountBalance(atm);

                if (atm.getAccounts().get(atm.getAuthorizedAccountId()).isOverdrawn()) {
                    System.out.println(String.format(
                            "Amount dispensed: $%.2f \nYou have been charged an overdraft fee of $%.2f.  %s",
                            withdrawAmount, atm.getATMOverdraftFee(), formatAccountBalanceStr(account_balance)));
                } else {
                    System.out.println(String.format("Amount dispensed: $%.2f  %s", withdrawAmount,
                            formatAccountBalanceStr(account_balance)));
                }
            } else {
                System.out.println("Please withdraw an other amount (suggestion: 20$, 40$,...).");
            }
        } else {
            System.out.println("Your account is overdrawn! You may not make withdrawals at this time.");
        }
    }

    // Authorize an account
    private static boolean authorizeSuccess(long account_id, int pin, ATM atm) {
        atm.authorize(account_id, pin);
        return atm.isAuthorized();
    }

    // Account logout
    private static void printLogoutMessage(ATM atm) {
        System.out.println("Account " + atm.getAuthorizedAccountId() + " logged out successfully.");
        atm.setAuthorized(false);
    }

    // print transaction history
    private static void printTransactionHistory(ATM atm) {
        ArrayList<Transaction> history = atm.getAccounts().get(atm.getAuthorizedAccountId()).getHistory();
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
        AUTH("Authorization is required."), AUTH_FAILED("Authorization failed."),
        ARGS("Invalid number of arguments."), NONEXISTENT("The account does not exist."),
        UNKNOWN_ARG("You have entered an invalid command."), ALREADY_AUTHORIZED("Your account is already authorized."),
        INVALID_INPUT("Your input is not valid.");

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
            case ALREADY_AUTHORIZED:
                System.out.println(ErrorTypes.ALREADY_AUTHORIZED.getErrorMessage(errorType));
                break;
            case INVALID_INPUT:
                System.out.println(ErrorTypes.INVALID_INPUT.getErrorMessage(errorType));
                break;
            default:
                System.out.println("UNKNOWN ERROR");
        }
    }
}