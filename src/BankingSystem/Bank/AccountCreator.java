package BankingSystem.Bank;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AccountCreator {

    private Map<Long,String> generatedAccount;

    public AccountCreator(){
        this.generatedAccount = new HashMap<>();
    }

    public Account generateAccount() {
        String cardNo = generateCardNo();
        String pin = generatePIN();

        return  new Account(cardNo,pin);
    }

    private String generateCardNo(){
        StringBuilder cardNo = new StringBuilder();
        cardNo.append("400000");
        cardNo.append(createUniqueAccount());
        int checkSum = generateCheckSum(cardNo.toString());
        cardNo.append(checkSum);
        return cardNo.toString();
    }

    private String generatePIN() {
        StringBuilder pin = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            pin.append(generateDigit());
        }
        return pin.toString();
    }

    private long createUniqueAccount(){
        long accountNumber = generateAccountNumber();
        while(generatedAccount.containsKey(accountNumber)) {
            accountNumber = generateAccountNumber();
        }
        generatedAccount.put(accountNumber,"added");
        return accountNumber;
    }

    private Long generateAccountNumber() {
        long min = 100000000L;
        long max = 999999999L;
        long accountNumber = min + (long) (Math.random() * (max - min));
        return accountNumber;
    }

    private int generateCheckSum(String cardNo) {
        int control = findControlNumber(cardNo);
        System.out.println("control = " + control);
        return (10 - control % 10)  % 10;
    }

    private int generateDigit() {
        int max = 10;
        Random random = new Random();
        return random.nextInt(max);
    }

    private int findControlNumber(String cardNo){
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
        return sum;
    }
}

