package ru.z3r0ing;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountConcurrencyTest {
    @Test
    void concurrentDepositsShouldPreserveEveryUpdate() throws Exception {
        int threadCount = 10;
        int depositsPerThread = 100_000;
        Account account = new Account(0);
        CountDownLatch start = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        try {
            List<Future<?>> tasks = new ArrayList<>();
            for (int i = 0; i < threadCount; i++) {
                tasks.add(executor.submit(() -> {
                    start.await();
                    for (int deposit = 0; deposit < depositsPerThread; deposit++) {
                        account.deposit(1);
                    }
                    return null;
                }));
            }

            start.countDown();
            for (Future<?> task : tasks) {
                task.get();
            }

            assertEquals((long) threadCount * depositsPerThread, account.getBalance());
        } finally {
            executor.shutdownNow();
        }
    }
}
