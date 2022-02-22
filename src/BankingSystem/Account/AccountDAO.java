package BankingSystem.Account;

import java.util.List;

public interface AccountDAO {
    public List<Account> getAllAccounts();
    public void addAccount(Account account);
    public Account getAccount(String cardNo);

}
