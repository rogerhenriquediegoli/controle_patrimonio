package src.repository;

import java.sql.Statement;
import java.sql.Connection;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.nio.charset.StandardCharsets;

public class DbSession {

    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static final String CONNECTION_PARAMS = "?useUnicode=true"
            + "&characterEncoding=UTF-8"
            + "&connectionCollation=utf8mb4_unicode_ci"
            + "&serverTimezone=America/Sao_Paulo";

    private static final String SERVER_PATH = "jdbc:mysql://localhost:3306/" + CONNECTION_PARAMS;
    private static final String DB_PATH = "jdbc:mysql://localhost:3306/controle_patrimonio" + CONNECTION_PARAMS;

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
            return DriverManager.getConnection(DB_PATH, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Boolean initializeDatabaseEnvironment() {
        try (Connection ignored = DriverManager.getConnection(DB_PATH, USER, PASSWORD)) {
            return true;
        } catch (SQLException e) {

            if (e.getErrorCode() != 1049) {
                e.printStackTrace();
                return false;
            }
        }

        try (Connection connection = startServerSession();
                Statement statement = connection.createStatement()) {

            InputStream is = DbSession.class.getClassLoader().getResourceAsStream("DB_SCRIPT.sql");

            if (is == null) {
                throw new RuntimeException("DB_SCRIPT.sql não encontrado no classpath");
            }

            String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            String[] commands = sql.split(";");

            for (String command : commands) {
                command = command.trim();

                if (!command.isEmpty()) {
                    statement.execute(command);
                }
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}