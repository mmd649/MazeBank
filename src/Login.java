import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Login {

    /*
        registerAccount has been created for the sole purpose of registering new account and then saving it to the database.
     */
    public void registerAccount(Connection sqlConnection, String username, String password, Account user){

        //checkIfUsernameExist is a function that returns a boolean depending on whether or not the username chosen when registering is already recorded in the database.
        //This is to ensure that there will be no duplication.
        if(checkIfUsernameExist(sqlConnection, username)){

            try{

                //Create the sql code and then execute it so that changes will be saved in the database.
                PreparedStatement newAccount = sqlConnection.prepareStatement("INSERT INTO accounts (_username, _password, account_type, first_name, last_name) VALUES ('" + username + "', '" + password + "', '" + user.getAccountType() + "', '" + user.getFirstName() + "', '" + user.getLastName() + "')");
                newAccount.executeUpdate();

                System.out.println("\nAccount Successfully created.\n");

            }
            catch(Exception e){
                System.out.println("Error occurred: " + e);
            }
        }
    }

    public void resetPassword(Connection sqlConnection, String username, String password){

        try{

            //Create the sql code and then execute it so that changes will be saved in the database.
            Statement loginQuery = sqlConnection.createStatement();
            ResultSet rs = loginQuery.executeQuery("SELECT id, _username FROM accounts WHERE _username='" + username + "';");

            //If there is some result
            if(rs.next()){

                int account_id = rs.getInt("id");

                //if the username entered by the user matches the one saved in the database, change the password to the new password provided by the user.
                if(rs.getString("_username").trim().equals(username)){

                    //Save the changes to the database so that the user can login using the new password they have set it to.
                    PreparedStatement sqlUpdate = sqlConnection.prepareStatement("UPDATE accounts SET _password = '" + password + "' WHERE id =" + account_id + ";");
                    sqlUpdate.executeUpdate();
                    //Print out a simple message so that the user knows that the changes has been made.
                    System.out.println("\nPassword has been changed.\n");

                }
            }

            else {
                System.out.println("\nYou have entered an incorrect username. Please try again.\n");
            }
        }
        catch (Exception e){

        }
    }

    /*
        Function will return a boolean to distinguish if the username chosen is available or not.
     */

    public boolean checkIfUsernameExist(Connection sqlConnection, String username){

        boolean isAvailable = true;

        try{
            //Create an array list which will hold username found in the database.
            ArrayList<String> usernameList = new ArrayList<String>();

            //create a statement and execute it to check if the username exist within the database.
            Statement usernameQuery = sqlConnection.createStatement();
            ResultSet rs = usernameQuery.executeQuery("SELECT _username FROM accounts");

            //extracting the results from the query. Since result will return multiple values so a loop is essential.
            while(rs.next()){
                //simply add the result to the array list created beforehand which will be use
                usernameList.add(rs.getString("_username"));

            }

            if(usernameList.contains(username)){
                System.out.println("The username you have chosen is already in used. Please choose another.");
                isAvailable = false;
            }

        }
        catch (Exception e){
            System.out.println("Error occurred: " + e);
        }

        return  isAvailable;

    }
}
