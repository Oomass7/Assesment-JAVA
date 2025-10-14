package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class Config {

    public static Connection getConnection() throws SQLException {
        try {
            Properties prop = new Properties();
            System.out.println("Intentando cargar database.properties...");
            InputStream stream = Config.class.getResourceAsStream("/config/database.properties");
            if (stream == null) {
                System.err.println("No se encontró el archivo database.properties en el classpath.");
                throw new RuntimeException("Archivo database.properties no encontrado");
            }
            prop.load(stream);

            String url = prop.getProperty("DB_URL");
            String user = prop.getProperty("DB_USER");
            String password = prop.getProperty("DB_PASSWORD");

            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.err.println("Error real: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error conecting to database");
        }
    }
}
