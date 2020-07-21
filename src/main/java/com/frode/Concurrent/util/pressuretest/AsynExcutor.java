package com.frode.Concurrent.util.pressuretest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@Service
public class AsynExcutor {

    @Autowired
    ConcurrentPressure concurrentPressure;

    public List<Long> execute(String url, int nums, int total, List<Long> tookTime) throws InterruptedException, ExecutionException {
        long t1 = System.currentTimeMillis();
        System.out.println("开始时间：" + t1);
        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(nums);
        // 创建CompletionService
        CompletionService cs = new ExecutorCompletionService<>(executor);
        // 用于保存Future对象
        List<Future<Integer>> futures = new ArrayList<>(total);
        //提交异步任务，并保存future到futures
        try {
            // 只要有一个成功返回，则break
            for (int i = 0; i < total; ++i) {
                futures.add(cs.submit(() -> concurrentPressure.execte(url, tookTime)));
            }
        } finally {
        }

        Integer r = 0;
        try {
            for (int i = 0; i < total; ++i) {
                r = futures.get(i).get();
            }
        } finally {
        }
        Collections.sort(tookTime);
        statisticsNinetyFivePercentTime(tookTime, total);
        System.out.println(tookTime);
        long t2 = System.currentTimeMillis();
        System.out.println("结束时间：" + t2);
        System.out.println(nums + "并发量，请求" + total + "次" + "花费时间：" + (t2 - t1));
        // 返回结果
        return tookTime;
    }

    private void statisticsNinetyFivePercentTime(List<Long> tookTime, int total) {
        total = (int) (total * 0.95);
        long totalTime = 0;
        for (int i = 0; i < total; i++) {
            totalTime += tookTime.get(i);
        }
        System.out.println("前百分之95所花费的平均时间为：" + totalTime / total);
    }

}
