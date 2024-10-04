package DB_Apps_Introduction.Exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class E03_GetMinion_Names {

    private static DBTools DB_TOOLS = new DBTools("root", "Root", "minions_db");
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));


    public static void main(String[] args) throws SQLException, IOException {

        Exercise3();

    }

    private static void Exercise3() throws IOException, SQLException {
        int villainId = Integer.parseInt(READER.readLine());

        String sql = "SELECT `name` FROM  `villains` WHERE id = ? ";
        PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, villainId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            System.out.printf("No villain with ID %d exists in database.", villainId);
            return;
        }

        String villainName = resultSet.getString("name");
        System.out.println("Villain: " + villainName);

        sql = " SELECT m.`name`,m.`age` FROM minions As m " +
                "JOIN minions_db.minions_villains mv on m.id = mv.minion_id " +
                "WHERE villain_id = ? ";

        preparedStatement = DB_TOOLS.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, villainId);
        resultSet = preparedStatement.executeQuery();

        int index = 0;
        while (resultSet.next()) {
            System.out.printf("%d. %s %d%n", ++index,
                    resultSet.getString("name"),
                    resultSet.getInt("age"));
        }


        preparedStatement = DB_TOOLS.getConnection().prepareStatement(sql);
    }


    private static void Exercise2() throws SQLException {
        String sql = "SELECT v.name, COUNT(mv.minion_id)  AS 'count' " +
                " FROM villains AS v " +
                "JOIN minions_db.minions_villains " +
                "            AS mv on v.id = mv.villain_id " +
                "GROUP BY v.name " +
                "HAVING count > 15 " +
                "ORDER BY count DESC; ";

        ResultSet resultSet = DB_TOOLS.getConnection().createStatement().executeQuery(sql);

        // output
        while (resultSet.next()) {

            System.out.printf("%s %d", resultSet.getString(1),
                            resultSet.getInt(2))
                    .append(System.lineSeparator());


        }
    }
}
