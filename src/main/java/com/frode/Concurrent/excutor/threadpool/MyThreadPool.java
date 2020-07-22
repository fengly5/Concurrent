package com.frode.Concurrent.excutor.threadpool;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyThreadPool {

    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue();

    ArrayList<WorkerThread> threads = new ArrayList<>();

    public MyThreadPool(int poolSize, BlockingQueue<Runnable> workQueue) {
        for (int idx = 0; idx < poolSize; idx++) {
            WorkerThread work = new WorkerThread();
            work.start();
            threads.add(work);
        }
    }

    public void execute(Runnable runnable) throws InterruptedException {
        workQueue.put(runnable);
    }

    class WorkerThread extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                Runnable task = workQueue.take();
                task.run();
            }
        }
    }
}
