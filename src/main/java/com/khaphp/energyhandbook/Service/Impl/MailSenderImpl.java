package com.khaphp.energyhandbook.Service.Impl;

import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Service.MailSender;
import com.khaphp.energyhandbook.Service.RedisService;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class MailSenderImpl implements MailSender {
    @Autowired
    private Environment env;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisService redisService;

    @Override
    public ResponseObject<?> sendOTP(String toEmail) {
        try {
            //create otp
            Random random = new Random();
            int otp = random.nextInt(900000) + 100000;

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setSubject("OTP for Energy Handbook");
            helper.setFrom("Energy-Handbook");
            helper.setTo(toEmail);

            boolean html = true;
            helper.setText("<b>Dear Customer</b>," +
                    "<br><br>Your register OTP is: <b>" + otp + "</b>" +
                    "<br><br>Thank you for using Energy Handbook." +
                    "<br>Best regards," +
                    "<br><b>Energy Handbook</b>" +
                    "<br><img src='" + env.getProperty("logo.energy_handbook.url") + "'/>", html);
            mailSender.send(message);

            redisService.saveOTPToCacheRedis(toEmail, otp);
            log.info("Send otp of " + toEmail + " : " + otp);

            return ResponseObject.builder()
                    .code(200)
                    .message("send OTP email success")
                    .build();

        } catch (Exception ex) {
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: " + ex.getMessage())
                    .build();
        }
    }
}
