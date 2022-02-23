package BankingSystem;

import BankingSystem.Account.Account;
import BankingSystem.Bank.*;

import java.util.Scanner;

public class Menu {


    private Scanner scanner;
    private Bank bank;

    public Menu(Bank bank) {
        this.scanner = new Scanner(System.in);
        this.bank = bank;
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

        if(bank.hasAccount(account.getCardNo())) {
            Account foundAccount = bank.findAccount(account.getCardNo());
            if(foundAccount == null) {
                System.out.println("No found account!");
            }
            if(foundAccount != null && foundAccount.checkPIN(account.getPin())){
                displayAccount(foundAccount.getCardNo());
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

    private void displayAccount(String cardNo) {
        System.out.println("You have successfully logged in!");
        System.out.println();
        Account account;
        boolean isRunning = true;
        while(isRunning){
            account = bank.findAccount(cardNo);
            System.out.println("1. Balance\n" +
                    "2. Add income\n" +
                    "3. Do transfer\n" +
                    "4. Close account\n" +
                    "5. Log out\n" +
                    "0. Exit");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Balance: " + account.getBalance());
                    break;
                case 2:
                    addIncome(account);
                    break;
                case 3:
                    transferMoney(account);
                    break;
                case 4:
                    bank.closeAccount(account);
                    break;
                case 5:
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

    private void transferMoney(Account account) {
        System.out.println("Enter card number:");
        String cardNoToTransferTo = scanner.nextLine();
        if(!bank.passesLuhnsAlgo(cardNoToTransferTo)){
            System.out.println("Probably you made a mistake in the card number. \n" +
                    "Please try again!");
        } else {
            if (bank.hasAccount(cardNoToTransferTo)) {
                long amount = getAmountToTransfer();
                if (amount <= account.getBalance()) {
                    bank.transferFunds(account, bank.findAccount(cardNoToTransferTo), amount);
                } else {
                    System.out.println("Not enough money!");
                    System.out.println();
                }
            } else {
                System.out.println("Such a card does not exist.");
                System.out.println();
            }
        }
    }

    private long getAmountToTransfer(){
        System.out.println("Enter how much money you want to transfer");
        return Long.parseLong(scanner.nextLine());
    }

    private void addIncome(Account account){
        System.out.println("Enter income:");
        long amount = Long.parseLong(scanner.nextLine());
        bank.addIncome(account,amount);
    }

    private void exit() {
        System.out.println("Bye!");
        System.exit(8);
    }

}
