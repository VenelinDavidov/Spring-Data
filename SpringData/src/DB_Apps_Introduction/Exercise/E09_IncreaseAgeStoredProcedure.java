package DB_Apps_Introduction.Exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class E09_IncreaseAgeStoredProcedure {

    private static DBTools DB_TOOLS = new DBTools("root", "Root", "minions_db");
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));



    public static void main(String[] args) throws SQLException, IOException {

        Exercise9();

    }


    //Method
    private static void Exercise9() throws IOException, SQLException {

        CallableStatement callableStatement = DB_TOOLS.getConnection().prepareCall("CALL usp_get_older(?)");
        callableStatement.setInt(1, Integer.parseInt(READER.readLine()));
        ResultSet resultSet = callableStatement.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + " " + resultSet.getInt(2));
        }

    }
}

