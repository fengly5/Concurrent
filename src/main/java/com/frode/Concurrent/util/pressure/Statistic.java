package com.frode.Concurrent.util.pressure;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class Statistic {

    public void statisticsNinetyFivePercentTime(List<Long> tookTime, int total) {
        System.out.println("排序前：" + tookTime);
        Collections.sort(tookTime);
        System.out.println("排序后：" + tookTime);
        total = (int) (total * 0.95);
        long totalTime = 0;
        for (int i = 0; i < total; i++) {
            totalTime += tookTime.get(i);
        }
        System.out.println("前百分之95所花费的平均时间为：" + totalTime / total);
    }

    public void statisticsTotalTime(List<Long> tookTime, int total) {
        Collections.sort(tookTime);
        long totalTime = 0;
        for (int i = 0; i < total; i++) {
            totalTime += tookTime.get(i);
        }
        System.out.println("响应平均时间为：" + totalTime / total);
    }
}
