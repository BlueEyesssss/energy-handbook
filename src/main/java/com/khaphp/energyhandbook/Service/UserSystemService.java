package com.khaphp.energyhandbook.Service;

import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Dto.usersystem.*;
import org.springframework.web.multipart.MultipartFile;

public interface UserSystemService {

    ResponseObject<Object> getAll();
    ResponseObject<Object> getDetail(String id);
    ResponseObject<Object> create(UserSystemDTOcreate object, String role);
    ResponseObject<Object> update(UserSystemDTOUpdate object);
    ResponseObject<Object> updateImage(String id, MultipartFile file);
    ResponseObject<Object> delete(String id);
    ResponseObject<Object> login(LoginParam param);
    ResponseObject<Object> changePassword(ChangePwdParam param);
    ResponseObject<Object> updateStatus(UpdateStatusParam param);
    ResponseObject<Object> updateEmail(UpdateEmailParam param);
}
