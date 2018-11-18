package Core.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{
    public static Connection connect()
    {
        Connection connection = null;
        try
        {
            String connectionString = "jdbc:sqlite:database.db";
            connection = DriverManager.getConnection(connectionString);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
