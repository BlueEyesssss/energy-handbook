package com.khaphp.energyhandbook.Service;

import com.khaphp.energyhandbook.Dto.News.NewsDTOcreate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOupdate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Dto.Wallet.WalletDTOcreate;
import com.khaphp.energyhandbook.Dto.Wallet.WalletDTOupdate;
import org.springframework.web.multipart.MultipartFile;

public interface WalletService {
    ResponseObject<Object> getDerail(String customerId, UserSystemService userSystemService);
    ResponseObject<Object> create(WalletDTOcreate object, UserSystemService userSystemService);
    ResponseObject<Object> updateBalance(WalletDTOupdate object, UserSystemService userSystemService);
    ResponseObject<Object> delete(String customerId);
}
