package ru.z3r0ing;

public class Account {
    private long balance;

    public Account(long initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(long amount) {
        balance += amount;
    }

    public void withdraw(long amount) {
        balance -= amount;
    }

    public long getBalance() {
        return balance;
    }
}
