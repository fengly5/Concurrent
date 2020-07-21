package com.frode.Concurrent.util.pressuretest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ConcurrentPressure {

    @Autowired
    RestTemplate restTemplate;

    public Integer execte(String url, List<Long> tookTime) {

        GlobalStatus globalStatus = new GlobalStatus();
        System.out.println(Thread.currentThread());
        globalStatus.setStartTime(System.currentTimeMillis());

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        globalStatus.setEndTime(System.currentTimeMillis());
        long t = globalStatus.getTimeDifference();
        System.out.println(Thread.currentThread() + "花费的时间为：" + t);
        tookTime.add(t);
        return 1;
    }

    class GlobalStatus {
        private long startTime;
        private long endTime;

        public long getTimeDifference() {
            return this.endTime - this.startTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }
    }

}
