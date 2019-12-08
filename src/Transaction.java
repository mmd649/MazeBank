import com.mysql.cj.protocol.Resultset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Transaction {

    //Takes in 3 arguments. The database connection, the username of the user and the amount to be deposited
    public void Deposit(Connection dbConnection, String _username, double transaction){

        try{

            //Set up the statement to be executed through JDBC
            Statement sqlQuery = dbConnection.createStatement();
            ResultSet rs = sqlQuery.executeQuery("SELECT id, _username, balance FROM accounts WHERE _username = '" + _username + "'");

            if(rs.next()){

                //Record the id of the user so that when the transaction is recoded in the transaction history, the users id will be recorded there.
                int account_id = rs.getInt("id");
                double currentBalance = rs.getDouble("balance");

                //If the username matches the username recorded in the database. Update the balance.
                if(rs.getString("_username").trim().equals(_username)){

                    double newBalance = currentBalance + transaction;

                    PreparedStatement sqlUpdate = dbConnection.prepareStatement("UPDATE accounts SET balance = '" + newBalance + "' WHERE id =" + account_id + ";");
                    sqlUpdate.executeUpdate();

                    //record the transaction so that when the user selects the transaction history, they will be able to view the history.
                    recordTransaction(dbConnection, account_id, transaction);

                    System.out.println("The money has been deposited to your account.");

                }
            }
        }
        catch(Exception e){

        }
    }

    //Takes in 3 arguments. The database connection, the username of the user and the amount to be withdraw
    public void Withdraw(Connection dbConnection, String _username, double transaction){

        try{

            Statement sqlQuery = dbConnection.createStatement();
            ResultSet rs = sqlQuery.executeQuery("SELECT id, _username, balance FROM accounts WHERE _username = '" + _username + "'");

            if(rs.next()){

                //Record the id of the user so that when the transaction is recoded in the transaction history, the users id will be recorded there.
                int account_id = rs.getInt("id");
                double currentBalance = rs.getDouble("balance");

                if(rs.getString("_username").trim().equals(_username)){

                    double newBalance = currentBalance - transaction;

                    PreparedStatement sqlUpdate = dbConnection.prepareStatement("UPDATE accounts SET balance = '" + newBalance + "' WHERE id =" + account_id + ";");
                    sqlUpdate.executeUpdate();

                    //Record the transaction so that when the user selects the transaction history, they will be able to view the history.
                    //The transaction has to be multiplied by -1 so that the values becomes negative because this is a withdrawal.
                    recordTransaction(dbConnection, account_id, (transaction * -1));

                    System.out.println("The money has been withdrew from your account.");

                }
            }
        }
        catch(Exception e){

        }

    }

    //Record the transaction made by the user. Has 3 arguments which are; connection, user id and the amount to be recorded.
    public void recordTransaction(Connection dbConnection, int id, double transaction){

        try{

            //Prepare the sql statement and then record the changes.
            PreparedStatement updateTransaction = dbConnection.prepareStatement("INSERT INTo transaction_history (id, _transaction) VALUES ('" + id + "', '" + transaction +  "')");
            updateTransaction.executeUpdate();

        }
        catch(Exception e){

        }

    }

    //Returns the transaction history of the user. Has 2 arguments; database connection  and the username.
    public ArrayList<Double> getTransactionHistory(Connection dbConnection, String username){

        //create an array list to store the user's transaction
        ArrayList<Double> transactionHistory = new ArrayList<Double>();
        Authentication userLogin = new Authentication();

        try{
            int id = 0;
            Statement idQuery = dbConnection.createStatement();
            ResultSet rs = idQuery.executeQuery("SELECT id FROM accounts WHERE _username='" + username + "';");

            if(rs.next()){
                
                id = rs.getInt("id");
                Statement transactionQuery = dbConnection.createStatement();
                ResultSet transactionList = transactionQuery.executeQuery("SELECT _transaction FROM transaction_history WHERE id='" + id + "'");

                while(transactionList.next()){
                    transactionHistory.add(transactionList.getDouble("_transaction"));
                }

            }
        }
        catch (Exception e){
            System.out.println("Error occurred: " + e);
        }
        return transactionHistory;
    }
}
