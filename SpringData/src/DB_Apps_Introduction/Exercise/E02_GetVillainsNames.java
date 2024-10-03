package DB_Apps_Introduction.Exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class E02_GetVillainsNames {

    private static final String URL = "jdbc:mysql://localhost:3306/minions_db";


    public static void main(String[] args) throws SQLException {

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "Root");


        Connection connection =
                DriverManager.getConnection(URL, properties);

        String sql = "SELECT v.name, COUNT(mv.minion_id)  AS 'count' " +
                " FROM villains AS v " +
                "JOIN minions_db.minions_villains " +
                "            AS mv on v.id = mv.villain_id " +
                "GROUP BY v.name " +
                "HAVING count > 15 " +
                "ORDER BY count DESC; ";

        ResultSet resultSet = connection.createStatement().executeQuery(sql);

        // output
        while (resultSet.next()) {

            System.out.printf("%s %d", resultSet.getString(1),
                            resultSet.getInt(2))
                           .append(System.lineSeparator());


        }
    }
}
