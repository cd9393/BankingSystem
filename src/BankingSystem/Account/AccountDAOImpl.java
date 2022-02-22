package BankingSystem.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {
    private final String GET_ALL_ACCOUNTS = "SELECT * FROM card";
    private final String GET_ACCOUNT = "SELECT * FROM card WHERE number = ?";
    private final String ADD_ACCOUNT = "INSERT INTO card (number, pin, balance) VALUES (?, ?, ?)";
    private final String url;

    public AccountDAOImpl(String fileName) {
        this.url = "jdbc:sqlite:" + fileName;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = connect(); PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ACCOUNTS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Account account = new Account(resultSet.getString("number"), resultSet.getString("pin"), resultSet.getLong("balance"));
                    accounts.add(account);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public void addAccount(Account account) {
        try (Connection connection = connect(); PreparedStatement preparedStatement = connection.prepareStatement(ADD_ACCOUNT)) {
            preparedStatement.setString(1, account.getCardNo());
            preparedStatement.setString(2, account.getPin());
            preparedStatement.setInt(3, (int) account.getBalance());
            preparedStatement.executeUpdate();
            System.out.println("Account was successfully added");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account getAccount(String cardNo) {
        Account account = null;
        try (Connection connection = connect(); PreparedStatement preparedStatement = connection.prepareStatement(GET_ACCOUNT)) {
            preparedStatement.setString(1, cardNo);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    account = new Account(resultSet.getString("number"), resultSet.getString("pin"), resultSet.getLong("balance"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    private Connection connect() {
        Connection connection = null;
        try  {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
