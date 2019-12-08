import java.sql.*;

public class Database {

    /*
        Enter your database password to grant access to your mysql server.
     */

    private final String username = "root";
    private final String password = "";
    private final String PATH = "jdbc:mysql://127.0.0.1:3306/mazebank";

    private Connection dbConnection;

    /*
        I have disabled this constructor as I have noticed that whenever I use the method getSqlConnection(),
        a constructor is initialized. This is due to the fact that Java does not support pass by reference unlike c++.
        This could create unnecessary object in the heap.
     */

//    public Database(){
//        try{
//
//            this.sqlConnection = DriverManager.getConnection(PATH, username, password);
//            System.out.println("Connection Established");
//
//        }catch (Exception e){
//            System.out.println("Error encountered: " + e);
//        }
//    }


    //Connect to a database
    public void establishConnection(){
        try{
            //To connect to a database, the path, username and password has to be specified.
            this.dbConnection = DriverManager.getConnection(PATH, username, password);
            //System.out.println("Connection Established");

        }catch (Exception e){
            System.out.println("Error encountered: " + e);
        }
    }

    //Its important to close the connection before terminating the program.
    public void closeConnection(){
        try{

            dbConnection.close();
            System.out.println("SQL connection successfully closed.");

        } catch (Exception e){

            System.out.println("Error occurred: " + e);

        }
    }

    public Connection getSqlConnection() {
        return dbConnection;
    }
}