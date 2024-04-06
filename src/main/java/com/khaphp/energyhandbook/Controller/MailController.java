package com.khaphp.energyhandbook.Controller;

import com.khaphp.energyhandbook.Service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailSender mailSender;

    @GetMapping("/send-otp")
    public ResponseEntity<?> sendOTP(@RequestParam String toEmail) {
        return ResponseEntity.ok(mailSender.sendOTP(toEmail));
    }
}
