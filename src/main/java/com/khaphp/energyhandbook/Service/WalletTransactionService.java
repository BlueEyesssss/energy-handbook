package com.khaphp.energyhandbook.Service;

import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Dto.Wallet.WalletDTOcreate;
import com.khaphp.energyhandbook.Dto.Wallet.WalletDTOupdate;
import com.khaphp.energyhandbook.Dto.WalletTransaction.WalletTransactionDTOcreate;
import com.khaphp.energyhandbook.Entity.WalletTransaction;

public interface WalletTransactionService {
    ResponseObject<Object> getAll(int pageSize, int pageIndex);
    ResponseObject<Object> getDetail(String id);
    ResponseObject<Object> create(WalletTransactionDTOcreate object);
}
