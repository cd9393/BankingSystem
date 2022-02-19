package BankingSystem.Bank;

import java.util.Random;

public class AccountCreator {

    public AccountCreator(){

    }

    public Account generateAccount() {
        String cardNo = generateCardNo();
        String pin = generatePIN();

        return  new Account(cardNo,pin);
    }

    private String generateCardNo(){
        StringBuilder cardNo = new StringBuilder();
        cardNo.append("400000");
        cardNo.append(generateAccountNumber());
        cardNo.append(generateCheckSum());
        return cardNo.toString();
    }

    private String generatePIN() {
        StringBuilder pin = new StringBuilder();
        pin.append(generateCheckSum());
        pin.append(generateCheckSum());
        pin.append(generateCheckSum());
        pin.append(generateCheckSum());
        return pin.toString();
    }

    private Long generateAccountNumber() {
        long min = 100000000L;
        long max = 999999999L;
        long accountNumber = min + (long) (Math.random() * (max - min));
        return accountNumber;
    }

    private int generateCheckSum() {
        int max = 10;
        Random random = new Random();
        return random.nextInt(max);
    }
}
