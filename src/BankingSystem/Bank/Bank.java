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

    public Account findAccount(String cardNoToFind) {
        Account foundAccount = accountDAO.getAccount(cardNoToFind);

        return foundAccount;
    }

    public boolean hasAccount(String cardNoToFind){
        boolean found = false;

        if(findAccount(cardNoToFind) != null) {
            found = true;
        }
        return found;
    }

    public void closeAccount(Account account){
        accountDAO.closeAccount(account);
    }

    public void addIncome(Account account, long amount) {
        accountDAO.depositIncome(account,amount);
    }

    public void transferFunds(Account accountToWithdraw, Account accountToDeposit, long amount) {
        accountDAO.transfer(accountToWithdraw,accountToDeposit,amount);
    }

    public boolean passesLuhnsAlgo(String cardNo){
        int sum = 0;
        int digit;
        for(int i = 0; i < cardNo.length(); i++) {
            digit = Character.getNumericValue(cardNo.charAt(i));
            if (i % 2 == 0) {
                digit *= 2;
                if(digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }
        if(sum % 10 == 0) {
            return true;
        } else {
            return false;
        }
    }
}
