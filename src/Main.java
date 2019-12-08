import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    //A global variable called s which will be used to accept user input.
    private final static Scanner s = new Scanner(System.in);
    private static boolean isLoggedIn = false;

    public static void main(String[] args) {

        String _username, _password;
        boolean quit = false;

        //Create a database object.
        Database mbDatabase = new Database();
        mbDatabase.establishConnection();
        Connection dbConnection = mbDatabase.getSqlConnection();

        //Create a login object.
        Login login = new Login();

        //Create authentication object
        Authentication authenticate = new Authentication();



        do{

            System.out.println("||==========MazeBank Menu==========||");
            System.out.println("||1 - Login                        ||");
            System.out.println("||2 - Create an Account            ||");
            System.out.println("||3 - Reset Password               ||");
            System.out.println("||Q - Quit                         ||");
            System.out.println("||=================================||");

            String userInput = s.nextLine().toUpperCase();

            switch(userInput){

                //Login
                case "1":{

                    String username, password;
                    System.out.print("\nUsername: ");
                    username = s.nextLine();
                    System.out.print("\nPassword: ");
                    password = s.nextLine();

                    if(authenticate.isLoggedIn(dbConnection, username, password)){
                        System.out.println("\nWelcome " + username + " to MazeBank!\n");
                        loggedIn(dbConnection, username);
                    }
                    else {
                        System.out.println("\nYou have entered a wrong Username or Password. Please try again.\n");
                    }
                    break;
                }

                //Create an Account
                case "2":{

                    String firstName, lastName, accountType;
                    double balance;

                    System.out.print("Username: ");
                    _username = s.nextLine();
                    System.out.print("\nPassword: ");
                    _password = s.nextLine();

                    System.out.print("\nWhat type of account would you like?[Standard | Savings ] ");
                    accountType = s.nextLine();
                    System.out.print("\nWhat is your first name?: ");
                    firstName = s.nextLine();
                    System.out.print("\nWhat is your last name?: ");
                    lastName = s.nextLine();

                    Account newUser = new Account(firstName, lastName, accountType, 0.0);
                    login.registerAccount(dbConnection, _username, _password, newUser);

                    break;
                }

                //Reset Password
                case "3":{
                    System.out.print("\nPlease enter your username: ");
                    _username = s.nextLine();
                    System.out.print("\nPlease enter your new password: ");
                    _password = s.nextLine();

                    login.resetPassword(dbConnection, _username, _password);

                    break;
                }

                default:{
                    System.out.println("You have entered an invalid input. Please try again.");
                    break;
                }

                //Quit
                case "Q":{
                    quit = true;
                }

            }

        }while(!quit);
        mbDatabase.closeConnection();
        System.out.println("Program Terminated");
    }

    /*
        A separate do while loop which also utilises a switch case statements.
        This is to improve readability as it may become quite hard to understand nested switch case statements.
     */
    static void loggedIn(Connection dbConnection, String username){
        boolean logout = false;

        Transaction transaction = new Transaction();

        do{

            System.out.println("||==========MazeBank Menu==========||");
            System.out.println("||1 - Check Balance                ||");
            System.out.println("||2 - Withdraw                     ||");
            System.out.println("||3 - Deposit                      ||");
            System.out.println("||4 - Transaction History          ||");
            System.out.println("||L - Logout                       ||");
            System.out.println("||=================================||");

            String userInput = s.nextLine().toUpperCase();

            switch(userInput){

                case "1":{
                    break;
                }

                case "2":{
                    System.out.println("How much do you want to withdraw: ");
                    Double toWithdraw = Double.parseDouble(s.nextLine());
                    transaction.Withdraw(dbConnection, username, toWithdraw);
                    break;
                }

                case "3":{
                    System.out.println("How much do you want to deposit?: ");
                    Double toDeposit = Double.parseDouble(s.nextLine());

                    transaction.Deposit(dbConnection, username, toDeposit);

                    break;
                }

                case "4":{

                    //Create a transaction Object.
                    ArrayList<Double> transactionHistory = new Transaction().getTransactionHistory(dbConnection, username);

                    System.out.println("\n\n");
                    System.out.println("==========Transaction History==========");
                    for(int x = 0; x < transactionHistory.size(); x++){
                        System.out.println("||" + x + ". " + transactionHistory.get(x));
                    }
                    System.out.println("=======================================");
                    break;
                }

                default:{
                    System.out.println("You have entered an invalid input. Please try again.");
                    break;
                }

                case "L":{
                    logout = true;
                }

            }

        }while(!logout);
        System.out.println("Account Logout Successful");
    }

}
