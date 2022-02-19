package BankingSystem.Bank;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Account> accounts;
    private AccountCreator accountCreator;

    public Bank () {
        this.accounts = new ArrayList<>();
        this.accountCreator = new AccountCreator();
    }

    public Account createAccount() {
        Account account = accountCreator.generateAccount();
        accounts.add(account);
        return account;
    }

    public Account findAccount(Account accountToFind) {
        for(Account account : accounts) {
            if(accountToFind.getCardNo().equals(account.getCardNo())) {
                return account;
            }
        }
        return null;
    }

    public boolean hasAccount(Account accountToFind){
        boolean found = false;

        for(Account account : accounts) {
            if(accountToFind.getCardNo().equals(account.getCardNo())) {
                found = true;
                break;
            }
        }
        return found;
    }
}
