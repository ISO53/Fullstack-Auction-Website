package com.iso.bidding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "http://localhost:8080")
@Controller
@RequestMapping("/session")
public class SpringSessionController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addSession(@RequestParam("sesInf") String sessionInformation) {
        String hash = UUID.randomUUID().toString();
        redisTemplate.opsForHash().put(hash, "sessionInfo", sessionInformation);
        redisTemplate.expire(hash, 60, TimeUnit.DAYS);

        Map<String, String> data = new HashMap<>();
        data.put("hash", hash);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/get/{hash}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> getSession(@PathVariable("hash") String hash) {
        String storedInformation = redisTemplate.opsForHash().get(hash, "sessionInfo").toString();
        String[] information = storedInformation.split("#_#", 2);

        Map<String, String> data = new HashMap<>();
        data.put("email", information[0]);
        data.put("password", information[1]);

        return new ResponseEntity<>(data, HttpStatus.FOUND);
    }
}
