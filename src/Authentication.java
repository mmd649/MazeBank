import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Authentication extends Database{

    private int id;
    private String _username;


    /*
        This method is responsible for authenticating users. If they managed to enter the correct login details, this method would
        return TRUE granting them access to another menu for customers Where they will be able to withdraw, deposit and view their transaction history.
     */
    public boolean isLoggedIn(Connection dbConnection, String username, String password){

        boolean authorised = false;

        try{

            //Create a statement object called loginQuery.
            Statement loginQuery = dbConnection.createStatement();
            //Since when a query is used, a result will be returned and in order to make use of it a result set is needed.
            ResultSet rs = loginQuery.executeQuery("SELECT id, _password FROM accounts WHERE _username='" + username + "';");

            //If there is something in the result set 'rs'
            if(rs.next()){

                //And if the password stored in the database matches the password entered by the user, set authorised to true.
                if(rs.getString("_password").trim().equals(password)){

                    this._username = username;
                    this.id = rs.getInt("id");
                    authorised = true;
                }
            }
        }
        catch(Exception e){
            System.out.println("Error Occurred: " + e);
        }

        return authorised;
    }

    public int getId() {
        return id;
    }

    public String get_username() {
        return _username;
    }
}
