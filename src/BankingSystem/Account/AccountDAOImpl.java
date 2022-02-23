package BankingSystem.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {
    private final String GET_ALL_ACCOUNTS = "SELECT * FROM card";
    private final String GET_ACCOUNT = "SELECT * FROM card WHERE number = ?";
    private final String ADD_ACCOUNT = "INSERT INTO card (number, pin, balance) VALUES (?, ?, ?)";
    private final String DEPOSIT_INCOME = "UPDATE card SET balance = ? WHERE number = ?";
    private final String DELETE_ACCOUNT = "DELETE FROM card WHERE number = ?";
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
                    System.out.println("Balance is = " + resultSet.getLong("balance"));
                    account = new Account(resultSet.getString("number"), resultSet.getString("pin"), resultSet.getLong("balance"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public void depositIncome(Account account, long income) {
        try(Connection connection = connect(); PreparedStatement preparedStatement = connection.prepareStatement(DEPOSIT_INCOME)){
            int newBalance = (int)(account.getBalance() + income);
            preparedStatement.setInt(1,newBalance);
            preparedStatement.setString(2, account.getCardNo());
            preparedStatement.executeUpdate();
            System.out.println("Income was added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeAccount(Account account) {
        try(Connection connection = connect(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ACCOUNT)) {
            preparedStatement.setString(1, account.getCardNo());
            preparedStatement.executeUpdate();
            System.out.println("The account has been closed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void transfer(Account account, Account cardToTransferTo, long amount) {
        try(Connection connection = connect()) {
            connection.setAutoCommit(false);
            try(PreparedStatement deductMoney = connection.prepareStatement(DEPOSIT_INCOME);
                PreparedStatement addMoney = connection.prepareStatement(DEPOSIT_INCOME)){
                int newBalance = (int)(account.getBalance() - amount);
                deductMoney.setInt(1, newBalance);
                deductMoney.setString(2,account.getCardNo());
                deductMoney.executeUpdate();

                int balanceAfterDeposit = (int)(cardToTransferTo.getBalance() + amount);
                addMoney.setInt(1,balanceAfterDeposit);
                addMoney.setString(2,cardToTransferTo.getCardNo());
                addMoney.executeUpdate();

                connection.commit();
            } catch (SQLException e){
                e.printStackTrace();
                System.out.println("Error! Rolling back transaction!");
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
