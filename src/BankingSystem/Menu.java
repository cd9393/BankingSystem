package BankingSystem;

import BankingSystem.Bank.*;

import java.util.Scanner;

public class Menu {

    private Scanner scanner;
    private Bank bank;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.bank = new Bank();
    }

    public void run() {
        boolean isRunning = true;

        while(isRunning) {
            System.out.println("1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    createAnAccount();
                    break;
                case 2:
                    loginToAccount();
                    break;
                case 0:
                    exit();
                    break;
                default:
                    System.out.println("Invalid selection!");
                    break;
            }
        }
    }

    private void createAnAccount() {
        Account account = bank.createAccount();
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(account.getCardNo());
        System.out.println(account.getPin());
    }

    private void loginToAccount() {
        Account account = getAccountDetails();

        if(bank.hasAccount(account)) {
            Account foundAccount = bank.findAccount(account);
            if(foundAccount != null && foundAccount.checkPIN(account.getPin())){
                displayAccount(bank.findAccount(account));
            }
        } else {
            System.out.println("Wrong card number or PIN!");
        }
    }

    private Account getAccountDetails() {
        System.out.println("Enter your card number:");
        String cardNo = scanner.nextLine();
        System.out.println("Enter your PIN:");
        String pin = scanner.nextLine();
        return new Account(cardNo,pin);
    }

    private void displayAccount(Account account) {
        System.out.println("You have successfully logged in!");
        System.out.println();
        boolean isRunning = true;
        while(isRunning){
            System.out.println("1. Balance\n" +
                    "2. Log out\n" +
                    "0. Exit");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Balance: " + account.getBalance());
                    break;
                case 2:
                    System.out.println("You have successfully logged out!");
                    System.out.println();
                    isRunning = false;
                    break;
                case 0:
                    exit();
                    break;
                default:
                    System.out.println("Invalid selection! Please try again");
                    break;
            }
        }
    }

    private void exit() {
        System.out.println("Bye!");
        System.exit(8);
    }

}
