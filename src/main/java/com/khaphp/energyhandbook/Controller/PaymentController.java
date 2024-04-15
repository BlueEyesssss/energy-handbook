package com.khaphp.energyhandbook.Controller;

import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Service.PaymentService;
import com.khaphp.energyhandbook.Service.UserSystemService;
import com.khaphp.energyhandbook.Util.VnPayHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserSystemService userSystemService;

    @Autowired
    private VnPayHelper vnPayHelper;

    @GetMapping
    public ResponseEntity<?> getAll(HttpServletRequest req,
                                    @RequestParam @Min(10000) int amount,
                                    @RequestParam String customerId){
        ResponseObject responseObject = paymentService.createPayment(req, amount, customerId, userSystemService, false, "");
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @GetMapping("/payment_result")
    public ResponseEntity<?> transaction(   //hàm bắt kq giao dịch (transaction - success, fail, hủy thanh toán, ...) về từ VNPAY
                                            @RequestParam(value = "vnp_Amount") String vnp_Amount,
                                            @RequestParam(value = "vnp_BankCode") String vnp_BankCode,
                                            @RequestParam(value = "vnp_OrderInfo") String vnp_OrderInfo,
                                            @RequestParam(value = "vnp_PayDate") String vnp_PayDate,
                                            @RequestParam(value = "vnp_ResponseCode") String vnp_ResponseCode) {
        ResponseObject<?> rs = paymentService.resultTransaction(vnp_Amount, vnp_BankCode, vnp_OrderInfo, vnp_PayDate, vnp_ResponseCode);
        if(rs.getCode() == 200){
            return ResponseEntity.ok(rs);
        }else {
            return ResponseEntity.badRequest().body(rs);
        }
//      HttpHeaders headers = new HttpHeaders();
//        if (rs.getCode() == 200) {
//            rs.setCode(200);
//            if(rs.getMessage().contains("guest")){
//                headers.add(HttpHeaders.LOCATION, vnPayHelper.vnp_RedirectResultGuestBooking_success);
//            }else{
//                headers.add(HttpHeaders.LOCATION, VnPayHelper.vnp_RedirectResult + "?status=success");
//            }
            //redirect qua URl khác
//            return new ResponseEntity<>(headers, HttpStatus.FOUND);
//        }else{
//            rs.setCode(400);
//            if(rs.getMessage().contains("guest")){
//                headers.add(HttpHeaders.LOCATION, VnPayHelper.vnp_RedirectResultGuestBooking_fail);
//            }else{
//                headers.add(HttpHeaders.LOCATION, VnPayHelper.vnp_RedirectResult + "?status=fail");
//            }
//            return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
