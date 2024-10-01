import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Password");


        // create DB

        Properties credentials = new Properties();
        credentials.setProperty("user", "root");
        credentials.setProperty("password", "Root");
        DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/soft_uni", credentials);

    }
}
