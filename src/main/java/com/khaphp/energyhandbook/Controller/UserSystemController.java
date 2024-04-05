package com.khaphp.energyhandbook.Controller;

import com.khaphp.energyhandbook.Constant.Role;
import com.khaphp.energyhandbook.Dto.usersystem.UserSystemDTOcreate;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.Repository.UserSystemRepository;
import com.khaphp.energyhandbook.Service.UserSystemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer")
public class UserSystemController {
    @Autowired
    private UserSystemService userSystemService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(userSystemService.getAll());
    }
    @GetMapping("/detail")
    public ResponseEntity<?> getObject(String id){
        return ResponseEntity.ok(userSystemService.getDetail(id));
    }

    @PostMapping("/customer")
    public ResponseEntity<?> createObject(@RequestBody @Valid UserSystemDTOcreate object){
        return ResponseEntity.ok(userSystemService.create(object, Role.CUSTOMER.toString()));
    }

    @PutMapping
    public ResponseEntity<?> updateObject(@RequestBody UserSystemDTOcreate object){
        return ResponseEntity.ok(userSystemService.getAll());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteObject(String id){
        return ResponseEntity.ok(userSystemService.delete(id));
    }
}
