package DB_Apps_Introduction.Exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class E04_AddMinion {

    private static DBTools DB_TOOLS = new DBTools("root", "Root", "minions_db");
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));


    public static void main(String[] args) throws SQLException, IOException {

        Exercise4();

    }

    private static void Exercise4() throws IOException, SQLException {

        //Minion: Robert 14 Berlin -> Вход
        String[] minionsToken = READER.readLine().split("\\s+");
        String minionsName = minionsToken[1];
        int ageMinions = Integer.parseInt(minionsToken[2]);
        String townName = minionsToken[3];

        //Villain: Gru -> Вход
        String villainName = READER.readLine().split("\\s+")[1];


        // Search Town, if it not exist -> create
        int townId = findTownId(townName);
        if (townId == 0) {
            townId = createTown(townName);
            System.out.printf("Town %s was added to the database.%n", townName);
        }

        // Create Minions and Find Villain, if he not exist -> create Villain
        int minionId = createMinion(minionsName, ageMinions, townId);
        int villainID = findVillainByName(villainName);
        if (villainID == 0) {
            villainID = createVillain(villainName);
            System.out.printf("Villain %s was added to the database.%n", villainName);
        }

        populateMinionsVillains(minionId, villainID);
        System.out.printf("Successfully added %s to be minion of %s", minionsName, villainName);

    }






     // METHODS
    private static void populateMinionsVillains(int minionId, int villainID) throws SQLException {
        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("INSERT INTO minions_villains VALUE (?,?)");
        preparedStatement.setInt(1,minionId);
        preparedStatement.setInt(2  ,villainID);
        preparedStatement.executeUpdate();


    }

    private static int createVillain(String villainName) throws SQLException {
        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("INSERT INTO villains(name, evilness_factor) VALUE (?,'evil')");
        preparedStatement.setString(1, villainName);
        preparedStatement.executeUpdate();

        preparedStatement = DB_TOOLS.getConnection().prepareStatement("SELECT id FROM villains WHERE name =? ");
        preparedStatement.setString(1, villainName);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        return resultSet.getInt("id");
    }

    private static int findVillainByName(String villainName) throws SQLException {
        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("SELECT id FROM minions WHERE name = ?");
       preparedStatement.setString(1,villainName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        return 0;
    }


    private static int createMinion(String minionsName, int ageMinions, int townId) throws SQLException {
        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("INSERT INTO minions(name, age, town_id) VALUE (?,?,?)");
        preparedStatement.setString(1, minionsName);
        preparedStatement.setInt(2, ageMinions);
        preparedStatement.setInt(3, townId);
        preparedStatement.executeUpdate();

        preparedStatement = DB_TOOLS.getConnection().prepareStatement("SELECT id FROM minions WHERE name = ?");
        preparedStatement.setString(1, minionsName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        return resultSet.getInt("id");
    }

    private static int createTown(String townName) throws SQLException {
        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement("INSERT INTO `towns`(name) VALUE (?) ");
        preparedStatement.setString(1, townName);
        preparedStatement.execute();
        preparedStatement = DB_TOOLS.getConnection().prepareStatement("SELECT `id` FROM towns WHERE name = ? ");
        preparedStatement.setString(1, townName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }


    private static int findTownId(String townName) throws SQLException {
        PreparedStatement preparedStatement =
                DB_TOOLS.getConnection().prepareStatement("SELECT `id` FROM `towns` WHERE `name` = ? ");
        preparedStatement.setString(1, townName);
        PreparedStatement preparedStatement1 = preparedStatement;
        ResultSet resultSet = preparedStatement1.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
        return 0;
    }
}