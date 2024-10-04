package DB_Apps_Introduction.Exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;

public class E07_PrintAllMinionNames {

    private static DBTools DB_TOOLS = new DBTools("root", "Root", "minions_db");
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws SQLException, IOException {

        Exercise7();

    }


    // Methods
    private static void Exercise7() throws IOException, SQLException {

        ResultSet resultSet = DB_TOOLS.getConnection()
                .createStatement().executeQuery("SELECT name FROM minions");

        ArrayDeque<String> stack = new ArrayDeque<>(); //Stack LIFO

        while (resultSet.next()) {
            stack.add(resultSet.getString(1));
        }

        while (!stack.isEmpty()) {
            System.out.println(stack.removeFirst());
            if (!stack.isEmpty()) {
                System.out.println(stack.removeLast());
            }
        }
    }
}
