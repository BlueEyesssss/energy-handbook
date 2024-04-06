package com.khaphp.energyhandbook.Service;

import com.khaphp.energyhandbook.Dto.ResponseObject;

public interface RedisService {
    public ResponseObject<?> saveOTPToCacheRedis(String key, int otp);
    public ResponseObject<?> checkOTP(String key, int otp);
}
