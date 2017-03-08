package ps5;

/**
 * Created by eiros_000 on 8/3/2017.
 */
public class SynchronizedAccount {
    private int balance;
    private Object depositLock = new Object();
    private Object withdrawLock = new Object();

    public SynchronizedAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(int amount) {
        synchronized (depositLock) {
            balance += amount;
        }
        System.out.println("$" + amount + " has been deposited");
    }

    public boolean withdraw(int amount) {
        synchronized (withdrawLock) {
            if (amount <= balance) {
                balance -= amount;
                System.out.println("$" + amount + " has been withdrawn");
                return true;
            } else {
                return false;
            }
        }
    }

    public void checkBalance() {
        System.out.println("Current balance is " + balance);
    }

    public static void main(String[] args) {

    }
}
