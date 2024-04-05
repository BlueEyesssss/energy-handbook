package com.khaphp.energyhandbook.controller;

import com.khaphp.energyhandbook.entity.UserSystem;
import com.khaphp.energyhandbook.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(customerRepository.findAll());
    }
    @GetMapping("/detail")
    public UserSystem getCustomer(UUID uuid){
        return customerRepository.findById(uuid.toString()).orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}
