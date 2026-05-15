package src.repository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbSession {

    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String SERVER_PATH = "jdbc:mysql://localhost:3306/";
    private static final String DB_SCRIPT_PATH = "src/repository/DB_SCRIPT.sql";

    public static Connection startServerSession() {
        try {
            return DriverManager.getConnection(SERVER_PATH, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection startDbSession() {
        try {
            return DriverManager.getConnection(SERVER_PATH + "controle_patrimonio", USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Boolean initializeDatabaseEnvironment() {
        try (Connection connection = startServerSession();
                Statement statement = connection.createStatement()) {

            String sql = Files.readString(Paths.get(DB_SCRIPT_PATH));

            String[] commands = sql.split(";");
            for (String command : commands) {
                command = command.trim();

                if (!command.isEmpty()) {
                    statement.execute(command);
                }
            }

            connection.close();
            return startServerSession() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}