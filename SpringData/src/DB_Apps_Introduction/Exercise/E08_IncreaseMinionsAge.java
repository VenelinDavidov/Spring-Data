package DB_Apps_Introduction.Exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Arrays;

public class E08_IncreaseMinionsAge {

    private static DBTools DB_TOOLS = new DBTools("root", "Root", "minions_db");
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws SQLException, IOException {

        Exercise8();

    }


    private static void Exercise8() throws IOException, SQLException {

        // 2 1 4 -> Input
        int[] minionIds = Arrays.stream(READER.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        Arrays.stream(minionIds).forEach(id -> {
            String sql = "UPDATE minions " +
                         "SET name = LOWER(name)," +
                         " age = age + 1 WHERE id = ? ";
            try {
                PreparedStatement preparedStatement = DB_TOOLS.getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        ResultSet resultSet = DB_TOOLS.getConnection().createStatement().executeQuery("SELECT name,age FROM minions");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + " " + resultSet.getInt(2));
        }
    }
}



