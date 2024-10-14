package orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnector {

    private static Connection connection;
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/%s";



    //create constructor
    public MyConnector() {
        
    }


    // Create method
    public static void createConnection(String userName, String password, String dbName) throws SQLException {

        Properties properties = new Properties();
        properties.setProperty("user", userName);
        properties.setProperty("password", password);

        connection = DriverManager.getConnection
                (String.format(CONNECTION_STRING, dbName), properties);

    }


    // getter connection
    public static Connection getConnection() {
        return connection;
    }
}
