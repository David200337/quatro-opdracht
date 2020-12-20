package db;
import java.sql.*;

public abstract class Database {
    private String connectionUrl;
    protected Connection connection;
    protected Statement statement;
    protected ResultSet resultSet;

    public Database (String connectionUrl) {
        this.connectionUrl = connectionUrl;
        this.connection = null;
        this.statement = null;
        this.resultSet = null;
    }

    // Connect to database

    public void connect() throws Exception {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionUrl);
        } catch (Exception e) {
            throw e;
        }
    }
}
