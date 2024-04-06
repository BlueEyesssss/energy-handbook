package com.khaphp.energyhandbook.Controller;

import com.khaphp.energyhandbook.Dto.usersystem.LoginParam;
import com.khaphp.energyhandbook.Service.UserSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserSystemService userSystemService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginParam param) {
        return ResponseEntity.ok(userSystemService.login(param));
    }
}
