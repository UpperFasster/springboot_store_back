//package com.UpperFasster.Magazine.store.controllers;
//
//import com.UpperFasster.Magazine.service.RedisService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Set;
//
//@RestController
//@RequestMapping("/redis")
//public class HelpController {
//    private final RedisService redisService;
//
//    @Autowired
//    public HelpController(RedisService redisService) {
//        this.redisService = redisService;
//    }
//
//    @PostMapping("/set")
//    public String setValue(@RequestParam String key, @RequestParam String value, @RequestParam int second) {
//        redisService.setValue(key, value, second);
//        return "Value set successfully";
//    }
//
//    @GetMapping("/get")
//    public String getValue(@RequestParam String key) {
//        String value = redisService.getValue(key);
//        return value != null ? value : "Key not found";
//    }
//
//    @DeleteMapping("/del")
//    public String delValue(@RequestParam String key) {
//        redisService.delValue(key);
//        return String.format("Key %s was deleted", key);
//    }
//
//    @GetMapping("/getAll")
//    public Set<String> getAll() {
//        return redisService.getAll();
//    }
//
//}
