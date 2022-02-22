package BankingSystem.Account;

public class Account {
    private String cardNo;
    private String pin;
    private long balance;

    public Account(String cardNo, String pin) {
        this.cardNo = cardNo;
        this.pin = pin;
        this.balance = 0L;
    }

    public Account(String cardNo, String pin, Long balance){
        this.cardNo = cardNo;
        this.pin = pin;
        this.balance = balance;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getPin() {
        return pin;
    }

    public long getBalance() {
        return balance;
    }

    public boolean checkPIN(String pin) {
        if(pin.equals(getPin())){
            return true;
        }
        return false;
    }
}
