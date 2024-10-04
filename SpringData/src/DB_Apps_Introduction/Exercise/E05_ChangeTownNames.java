package DB_Apps_Introduction.Exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class E05_ChangeTownNames {
    private static DBTools DB_TOOLS = new DBTools("root", "Root", "minions_db");
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws SQLException, IOException {

        Exercise5();

    }


    // METHODS
    private static void Exercise5() throws IOException, SQLException {

        String country = READER.readLine();

        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("UPDATE towns SET name = UPPER(name) WHERE country = ?");
        preparedStatement.setString(1, country);
        int i = preparedStatement.executeUpdate();


        if (i == 0) {
            System.out.printf("No town names were affected");
        } else {
            System.out.printf("%d town names were affected.%n", i);
            preparedStatement = DB_TOOLS.getConnection().prepareStatement("SELECT name FROM towns WHERE country = ?");
            preparedStatement.setString(1, country);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<String> names = new ArrayList<>(); // List of String on towns in country

            while (resultSet.next()) {
                names.add(resultSet.getString("name"));
            }
            System.out.printf("[%s]", String.join(", ", names));
        }
    }
}
