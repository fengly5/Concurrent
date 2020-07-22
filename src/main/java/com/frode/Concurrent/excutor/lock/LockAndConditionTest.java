package com.frode.Concurrent.excutor.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockAndConditionTest {

    private final Lock rtl = new ReentrantLock();

    int value;

    private final Condition notEmpty = rtl.newCondition();

    private final Condition notFull = rtl.newCondition();

    public void addValue() {
        rtl.lock();
        try {
            value += 1;
        } finally {
            rtl.unlock();
        }
    }

    private void enq() throws InterruptedException {
        rtl.lock();
        try {
            while (value > 10) {
                notFull.await();
            }
            //入队操作
            notEmpty.signalAll();
        } finally {
            rtl.unlock();
        }

    }

    private void deq() throws InterruptedException {
        rtl.lock();
        try {
            while (value < 1) {
                notEmpty.await();
            }
            //出队操作
            notFull.signalAll();
        } finally {
            rtl.unlock();
        }
    }
}
