package com.khaphp.energyhandbook.Service;

import com.khaphp.energyhandbook.Dto.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    ResponseObject<?> createPayment(HttpServletRequest req, int amount_param, String customerId, UserSystemService userSystemService);
    ResponseObject<?> resultTransaction(String vnp_Amount, String vnp_BankCode, String vnp_OrderInfo, String vnp_PayDate, String vnp_ResponseCode);
}
