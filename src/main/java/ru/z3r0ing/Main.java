package ru.z3r0ing;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Account account = new Account(0);
        Thread t1 = new Thread(() -> account.deposit(1));
        t1.start();
        t1.join();
        System.out.println("Balance: " + account.getBalance());
    }
}
