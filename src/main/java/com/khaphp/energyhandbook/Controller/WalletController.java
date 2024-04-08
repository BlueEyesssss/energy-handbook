package com.khaphp.energyhandbook.Controller;

import com.khaphp.energyhandbook.Dto.News.NewsDTOcreate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOupdate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Dto.Wallet.WalletDTOupdate;
import com.khaphp.energyhandbook.Service.NewsService;
import com.khaphp.energyhandbook.Service.UserSystemService;
import com.khaphp.energyhandbook.Service.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/wallet")
//@SecurityRequirement(name = "EnergyHandbook")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @Autowired
    private UserSystemService userSystemService;

    @GetMapping("/detail")
    public ResponseEntity<?> detailObject(String customerId){
        ResponseObject responseObject = walletService.getDerail(customerId, userSystemService);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping("/customer-balance")
    public ResponseEntity<?> updateObjectBalance(@RequestBody @Valid WalletDTOupdate object){
        ResponseObject responseObject = walletService.updateBalance(object, userSystemService);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }
}
