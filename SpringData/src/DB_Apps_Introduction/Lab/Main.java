package DB_Apps_Introduction.Lab;

import java.sql.*;
import java.util.Properties;


public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/soft_uni";


    public static void main(String[] args) throws SQLException {


        // create DB

        Properties credentials = new Properties();
        credentials.setProperty("user", "root");
        credentials.setProperty("password", "Root");

        Connection connection = DriverManager.getConnection(URL, credentials);

        // Execute query
        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT * FROM employees LIMIT 10");

        ResultSet resultSet = preparedStatement.executeQuery();


        // Print Result
        while (resultSet.next()) {

            long id = resultSet.getLong("employee_id");
            String first_name = resultSet.getString("first_name");
            double salary = resultSet.getDouble("salary");
            Timestamp hireDate = resultSet.getTimestamp("hire_date");


            System.out.printf("%d - %s %.2f %s%n", id, first_name, salary, hireDate);


        }
        connection.close();
    }
}


