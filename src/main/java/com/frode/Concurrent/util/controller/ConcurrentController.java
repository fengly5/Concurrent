package com.frode.Concurrent.util.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.frode.Concurrent.util.pressure.AsynExcutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class ConcurrentController {
    @Autowired
    AsynExcutor asynExcutor;

    @RequestMapping("/test/pressure/{nums}/{total}")
    public ResponseEntity<JSONPObject> testConcurentPresure(@PathVariable int nums, @PathVariable int total) throws InterruptedException, ExecutionException {
        String url = "http://www.tianqiapi.com/api?version=v9&appid=23035354&appsecret=8YvlPNrz";
        List<Long> tookTime = new ArrayList<>();
        asynExcutor.execute(url, nums, total,tookTime);
        return ResponseEntity.ok(null);
    }
}
