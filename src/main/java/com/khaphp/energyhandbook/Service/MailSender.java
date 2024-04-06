package com.khaphp.energyhandbook.Service;

import com.khaphp.energyhandbook.Dto.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface MailSender {
    public ResponseObject<?> sendOTP(String toEmail);
}
