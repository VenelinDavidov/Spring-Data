package DB_Apps_Introduction.Exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBTools {

    private final String url = "jdbc:mysql://localhost:3306/";

    private Connection connection;


    public DBTools(String username, String password, String tableName) {
        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);


        try {
            connection = DriverManager.getConnection(url + tableName, properties);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // getters
    public Connection getConnection() {
        return connection;
    }
}
