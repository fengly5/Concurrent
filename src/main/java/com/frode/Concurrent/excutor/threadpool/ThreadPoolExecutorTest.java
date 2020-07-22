package com.frode.Concurrent.excutor.threadpool;

import java.util.LinkedList;
import java.util.concurrent.*;

public class ThreadPoolExecutorTest {
    //当创建不同的线程数的时候，打印的结果不一样，可以观察出线程的循环利用效果
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    LinkedList<Future<String>> futures = new LinkedList<>();

    public void execute(int total) throws ExecutionException, InterruptedException {
        for (int idt = 0; idt < total; idt++) {
            Future future = executorService.submit(new TaskCallabel(idt));
            futures.add(future);
        }
        for (int idt = 0; idt < total; idt++) {
            System.out.println(futures.get(idt).get());
        }
    }

    class TaskCallabel implements Callable {
        int idt = 0;

        public TaskCallabel(int idt) {
            this.idt = idt;
        }

        @Override
        public Object call() throws Exception {
            if (idt == 4 || idt == 9) {
                System.out.println(idt + "正在休息－－－－－－－");
                Thread.sleep(2000);
            }
            //要这里处理异常
            System.out.println("打印－－－－idt:" + idt);
            return "执行的线程" + Thread.currentThread().getName() + "返回结果：" + idt;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutorTest threadPoolExecutorTest = new ThreadPoolExecutorTest();
        threadPoolExecutorTest.execute(10);
    }
}
