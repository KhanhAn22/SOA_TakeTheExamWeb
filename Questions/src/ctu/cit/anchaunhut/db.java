package ctu.cit.anchaunhut;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class db {

    private static final String URL = "jdbc:mysql://localhost:3306/soa_quiz";
    private static final String USERNAME = "nhut1";
    private static final String PASSWORD = "";
    private Connection connection;
    
    public Connection getConnection() throws ClassNotFoundException {

    	 try {
    	    	Class.forName("com.mysql.jdbc.Driver");  
             // Initialize the connection
             connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        
         } catch (SQLException e) {
             System.err.println("Failed to connect to database: " + e.getMessage());
         }
    	 return connection;
    }
    
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Failed to close database connection: " + e.getMessage());
            }
        }
    }
    
    
    
    
}
