package DB_Apps_Introduction.Exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class E06_RemoveVillain {

    private static DBTools DB_TOOLS = new DBTools("root", "Root", "minions_db");
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws SQLException, IOException {

        Exercise6();

    }

    // Methods
    private static void Exercise6() throws IOException, SQLException {

        int villainId = Integer.parseInt(READER.readLine()); // Input -> 1

        String villainName = findVillainById(villainId);   // Method

        if (villainName.isEmpty()) {
            System.out.println("No such villain was found");
        } else {
            int deletedMinionsCount = releaseMinions(villainId);
            int deleteVillainCount = deleteVillain(villainId);

            System.out.printf("%s was delete%n%d minions released", villainName, deletedMinionsCount);
        }

    }

    private static int deleteVillain(int villainId) throws SQLException {
        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("DELETE FROM villains WHERE id = ?");
        preparedStatement.setInt(1, villainId);
        return preparedStatement.executeUpdate(); // връща броя на изтрити злодей

    }

    private static int releaseMinions(int villainId) throws SQLException {
        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("DELETE FROM minions_villains WHERE villain_id = ?");
        preparedStatement.setInt(1, villainId);
        return preparedStatement.executeUpdate(); // връща броя на изтрити миньони
    }


    private static String findVillainById(int villainId) throws SQLException {
        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("SELECT name FROM villains WHERE id = ?");
        preparedStatement.setInt(1, villainId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("name");
        }
        return "";
    }

}
