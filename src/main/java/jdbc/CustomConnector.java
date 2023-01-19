package jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class CustomConnector {

    public Connection getConnection(String url) throws ClassNotFoundException, IOException, SQLException {

        InputStream input  = new FileInputStream("path/to/app.properties");
        Properties properties = new Properties();
        properties.load(input);
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url,properties.getProperty("postgres.name"),properties.getProperty("postgres.password"));
    }

    public Connection getConnection(String url, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url,user,password);
    }
}
