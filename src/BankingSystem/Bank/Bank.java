package BankingSystem.Bank;

import BankingSystem.Account.AccountDAOImpl;
import BankingSystem.Account.Account;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Account> accounts;
    private AccountDAOImpl accountDAO;
    private AccountCreator accountCreator;

    public Bank (String fileName) {
        this.accounts = new ArrayList<>();
        this.accountCreator = new AccountCreator();
        this.accountDAO = new AccountDAOImpl(fileName);
    }

    public Account createAccount() {
        Account account = accountCreator.generateAccount();
        accounts.add(account);
        accountDAO.addAccount(account);
        return account;
    }

    public void getAllAccount() {
        this.accounts = accountDAO.getAllAccounts();
    }

    public Account findAccount(Account accountToFind) {
        Account foundAccount = accountDAO.getAccount(accountToFind.getCardNo());

        return foundAccount;
    }

    public boolean hasAccount(Account accountToFind){
        boolean found = false;

        if(findAccount(accountToFind) != null) {
            found = true;
        }
        return found;
    }
}
