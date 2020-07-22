package com.frode.Concurrent.excutor.atomic;

import java.util.concurrent.atomic.LongAdder;

public class LongAdderTest {
    static LongAdder adder = new LongAdder();

    static void addValue() {
        int idx = 0;
        while (idx++ < 1000){
            adder.add(2);
        }
        System.out.println(adder.sum());
    }

    public static void main(String[] args) {
        new Thread(() -> {
            LongAdderTest.addValue();
        }).start();
        new Thread(() -> {
            LongAdderTest.addValue();
        }).start();
        new Thread(() -> {
            LongAdderTest.addValue();
        }).start();
    }
}
