package BankingSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private final String url;

    public Database(String fileName) {
        this.url = "jdbc:sqlite:" + fileName;
        createDB();
    }

    private void createDB() {
    try(Connection connection = DriverManager.getConnection(url)) {
        if(connection.isValid(100)){
            System.out.println("Database created");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    public void createCardTable()  {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS card (id INTEGER PRIMARY_KEY auto_increment, number TEXT, pin TEXT, balance INTEGER DEFAULT 0)";
        try(Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCreate);
            System.out.println("Card table has been created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 }
