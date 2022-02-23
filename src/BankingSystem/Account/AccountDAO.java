package BankingSystem.Account;

import java.util.List;

public interface AccountDAO {
    public List<Account> getAllAccounts();
    public void addAccount(Account account);
    public Account getAccount(String cardNo);
    public void depositIncome(Account account, long income);
    public void closeAccount(Account account);
    public void transfer(Account account, Account accountToTransferTo, long amount);


}
