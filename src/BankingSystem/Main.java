package BankingSystem;

import BankingSystem.Bank.Bank;

public class Main {

    public static void main(String[] args) {
        Database database = new Database(args[1]);
        database.createCardTable();
        Bank bank = new Bank(args[1]);
        Menu menu = new Menu(bank);
        menu.run();
    }
}
