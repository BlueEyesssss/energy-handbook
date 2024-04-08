package com.khaphp.energyhandbook.Service.Impl;

import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Dto.WalletTransaction.WalletTransactionDTOcreate;
import com.khaphp.energyhandbook.Entity.News;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.Entity.WalletTransaction;
import com.khaphp.energyhandbook.Repository.WalletTransactionRepository;
import com.khaphp.energyhandbook.Service.UserSystemService;
import com.khaphp.energyhandbook.Service.WalletService;
import com.khaphp.energyhandbook.Service.WalletTransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WalletTransactionServiceImpl implements WalletTransactionService {
    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Autowired
    private UserSystemService userSystemService;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ResponseObject<Object> getAll(int pageSize, int pageIndex) {
        Page<WalletTransaction> objListPage = null;
        List<WalletTransaction> objList = null;
        int totalPage = 0;
        //paging
        if(pageSize > 0 && pageIndex > 0){
            objListPage = walletTransactionRepository.findAll(PageRequest.of(pageIndex - 1, pageSize));  //vì current page ở code nó start = 0, hay bên ngoài la 2pga đầu tiên hay 1
            if(objListPage != null){
                totalPage = objListPage.getTotalPages();
                objList = objListPage.getContent();
            }
        }else{ //get all
            objList = walletTransactionRepository.findAll();
            pageIndex = 1;
        }
        objList.forEach(item -> {
            if(item.getDescription().contains("Nap tien vao vi Energy Handbook")){
                item.setDescription("Nạp tiền vào ví Energy Handbook");
            }
        });
        return ResponseObject.builder()
                .code(200).message("Success")
                .pageSize(objList.size()).pageIndex(pageIndex).totalPage(totalPage)
                .data(objList)
                .build();
    }

    @Override
    public ResponseObject<Object> getDetail(String id) {
        try{
            WalletTransaction object = walletTransactionRepository.findById(id).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            return ResponseObject.builder()
                    .code(200)
                    .message("Found")
                    .data(object)
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: "+ e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> create(WalletTransactionDTOcreate object) {
        try{
            UserSystem userSystem = (UserSystem) userSystemService.getDetail(object.getCustomerId()).getData();
            if(userSystem == null){
                throw new Exception("user not found");
            }
            WalletTransaction walletTransaction = modelMapper.map(object, WalletTransaction.class);
//            walletTransaction.setCreateDate(new Date(System.currentTimeMillis()));
            walletTransaction.setWallet(userSystem.getWallet());
            walletTransactionRepository.save(walletTransaction);
            return ResponseObject.builder()
                    .code(200)
                    .message("Success")
                    .data(walletTransaction)
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: " + e.getMessage())
                    .build();
        }
    }
}
