package com.khaphp.energyhandbook.Controller;

import com.khaphp.energyhandbook.Dto.News.NewsDTOcreate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOupdate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Dto.WalletTransaction.WalletTransactionDTOcreate;
import com.khaphp.energyhandbook.Entity.WalletTransaction;
import com.khaphp.energyhandbook.Service.NewsService;
import com.khaphp.energyhandbook.Service.WalletTransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/wallet-transaction")
//@SecurityRequirement(name = "EnergyHandbook")
public class WalletTransactionController {
    @Autowired
    private WalletTransactionService walletTransactionService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "10") int pageSize,
                                    @RequestParam(defaultValue = "1") int pageIndex){
        ResponseObject responseObject = walletTransactionService.getAll(pageSize, pageIndex);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }
    @GetMapping("/detail")
    public ResponseEntity<?> getObject(String id){
        ResponseObject responseObject = walletTransactionService.getDetail(id);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

//    @PostMapping
//    public ResponseEntity<?> createObject(@RequestBody @Valid WalletTransactionDTOcreate object){
//        ResponseObject responseObject = walletTransactionService.create(object);
//        if(responseObject.getCode() == 200){
//            return ResponseEntity.ok(responseObject);
//        }
//        return ResponseEntity.badRequest().body(responseObject);
//    }
}
