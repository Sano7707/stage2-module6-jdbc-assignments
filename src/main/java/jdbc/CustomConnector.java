package jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class CustomConnector {

    public Connection getConnection(String url)  {

        InputStream input;
        try {
            input = new FileInputStream("path/to/app.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Properties properties = new Properties();
        try {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url,properties.getProperty("postgres.name"),properties.getProperty("postgres.password"));

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Connection getConnection(String url, String user, String password)  {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
