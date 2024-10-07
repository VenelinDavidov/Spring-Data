package DB_Apps_Introduction.Exercise;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class E04_AddMinions_02 {

    private static DBTools DB_TOOLS = new DBTools("root", "Root", "minions_db");
    private static final Scanner scanner = new Scanner(System.in);
    private static final Connection connection = DB_TOOLS.getConnection();

    public static void main(String[] args) throws SQLException, IOException {

        getExercise4();

    }

    private static void getExercise4() throws SQLException {

        String[] minionsInfo = scanner.nextLine().split(" ");
        String minionsName = minionsInfo[1];
        int ageMinions = Integer.parseInt(minionsInfo[2]);
        String townName = minionsInfo[3];


        String villainName = scanner.nextLine().split(" ")[1];

        int townID = getOrInsertTown(townName, connection);
        int villainId = getOrInsertVillain(connection, villainName);

        PreparedStatement insertMinion =
                connection.prepareStatement("INSERT INTO minions(name, age, town_id) VALUES(?,?,?)");
        insertMinion.setString(1, minionsName);
        insertMinion.setInt(2, ageMinions);
        insertMinion.setInt(3, townID);

        insertMinion.executeUpdate();

        PreparedStatement getLastMinion = connection.prepareStatement("SELECT id FROM minions ORDER BY id DESC ");
        ResultSet resultLastMinion = getLastMinion.executeQuery();
        resultLastMinion.next();
        int lastMinionId = resultLastMinion.getInt("id");


        PreparedStatement insertMinionsVillains = connection.prepareStatement("INSERT INTO minions_villains VALUES (?,?)");
        insertMinionsVillains.setInt(1, lastMinionId);
        insertMinionsVillains.setInt(2, villainId);
        insertMinionsVillains.executeUpdate();


        System.out.printf("Successfully added %s to be minion of %s%n", minionsName, villainName);
    }


    private static int getOrInsertVillain(Connection connection, String villainName) throws SQLException {
        PreparedStatement selectVillain = connection.prepareStatement("SELECT id FROM villains WHERE name = ?");
        selectVillain.setString(1, villainName);
        ResultSet resultVillainSet = selectVillain.executeQuery();

        int villainId = 0;
        if (resultVillainSet.next()) {

            PreparedStatement insertVillain = connection.prepareStatement
                    ("INSERT  INTO  villains (name, evilness_factor) VALUES (?,?)");
            insertVillain.setString(1, villainName);
            insertVillain.setString(2, "evil");

            insertVillain.executeUpdate();

            ResultSet newResultSetVillain = selectVillain.executeQuery();
            newResultSetVillain.next();
            villainId = newResultSetVillain.getInt("id");
            System.out.printf("Villain %s was added to the database.%n", villainName);
        } else {

            villainId = resultVillainSet.getInt("id");
        }
        return villainId;
    }


    private static int getOrInsertTown(String townName, Connection connection) throws SQLException {
        PreparedStatement selectTown = connection.prepareStatement("SELECT id FROM towns WHERE name = ?");
        selectTown.setString(1, townName);
        ResultSet resultTownSet = selectTown.executeQuery();

        int townId = 0;
        if (!resultTownSet.next()) {
            PreparedStatement insertTown = connection.prepareStatement("INSERT INTO towns(name) VALUE (?)");
            insertTown.setString(1, townName);
            insertTown.executeUpdate();

            ResultSet newTownSet = selectTown.executeQuery();
            newTownSet.next();
            townId = newTownSet.getInt("id");
            System.out.printf("Town %s was added to the database.%n", townName);
        } else {
            townId = resultTownSet.getInt("id");
        }
        return townId;
    }
}
