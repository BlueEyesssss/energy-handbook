package com.khaphp.energyhandbook.Service.Impl;

import com.khaphp.energyhandbook.Constant.Role;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Dto.Wallet.WalletDTOcreate;
import com.khaphp.energyhandbook.Dto.Wallet.WalletDTOupdate;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.Entity.Wallet;
import com.khaphp.energyhandbook.Repository.WalletRepository;
import com.khaphp.energyhandbook.Service.UserSystemService;
import com.khaphp.energyhandbook.Service.WalletService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseObject<Object> getDerail(String customerId, UserSystemService userSystemService) {
        try {
            UserSystem customer = (UserSystem) userSystemService.getDetail(customerId).getData();
            if(customer == null){
                throw new Exception("customer not found");
            }
            return ResponseObject.builder()
                    .code(200).message("Success")
                    .data(customer.getWallet())
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400).message("Exception: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> create(WalletDTOcreate object, UserSystemService userSystemService) {
        try {
            UserSystem customer = (UserSystem) userSystemService.getDetail(object.getCustomerId()).getData();
            if(customer == null){
                throw new Exception("customer not found");
            }
            Wallet wallet = new Wallet();
            wallet.setBalance(0);
            wallet.setCustomer(customer);
            walletRepository.save(wallet);
            return ResponseObject.builder()
                    .code(200).message("Success")
                    .data(wallet)
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400).message("Exception: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> updateBalance(WalletDTOupdate object, UserSystemService userSystemService) {
        try {
            UserSystem customer = (UserSystem) userSystemService.getDetail(object.getCustomerId()).getData();
            if(customer == null || !customer.getRole().equals(Role.CUSTOMER)){
                throw new Exception("customer not found");
            }
            customer.getWallet().setBalance(customer.getWallet().getBalance() + object.getBalance());
            walletRepository.save(customer.getWallet());
            return ResponseObject.builder()
                    .code(200).message("Success")
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400).message("Exception: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> delete(String id) {
        try {
            if(!walletRepository.existsById(id)){
                throw new Exception("object not found");
            }
            walletRepository.deleteById(id);
            return ResponseObject.builder()
                    .code(200).message("Success")
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400).message("Exception: " + e.getMessage())
                    .build();
        }
    }
}
