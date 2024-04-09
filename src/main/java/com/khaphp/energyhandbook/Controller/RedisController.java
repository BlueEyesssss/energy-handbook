package com.khaphp.energyhandbook.Controller;

import com.khaphp.energyhandbook.Service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisService redisService;

    @GetMapping("/check")
    public ResponseEntity<?> checkOTP(String gmail, int otp) {
        return ResponseEntity.ok(redisService.checkOTP(gmail, otp));
    }
//    @PostMapping("/send-otp")
//    public ResponseEntity<?> saveOTPToCacheRedis(@RequestBody SendOTPParam param) {
//        return ResponseEntity.ok(redisService.saveOTPToCacheRedis(param.getGmail(), param.getOtp()));
//    }
}
