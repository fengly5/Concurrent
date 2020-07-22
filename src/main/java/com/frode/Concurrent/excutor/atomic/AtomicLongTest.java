package com.frode.Concurrent.excutor.atomic;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongTest {

    static AtomicLong count =
            new AtomicLong(0);

    static int bar = 0;

    static void add10K() {
        int idx = 0;
        while (idx++ < 10000) {
            bar++;
            count.getAndIncrement();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> AtomicLongTest.add10K()).start();
        new Thread(() -> AtomicLongTest.add10K()).start();
        new Thread(() -> AtomicLongTest.add10K()).start();

        Thread.sleep(2000);
        System.out.println("count:" + count);
        System.out.println("bar:" + bar);
    }
}
