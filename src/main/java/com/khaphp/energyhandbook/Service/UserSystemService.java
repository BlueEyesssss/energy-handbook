package com.khaphp.energyhandbook.Service;

import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Dto.usersystem.LoginParam;
import com.khaphp.energyhandbook.Dto.usersystem.UserSystemDTOcreate;
import org.springframework.web.multipart.MultipartFile;

public interface UserSystemService {

    ResponseObject<Object> getAll();
    ResponseObject<Object> getDetail(String id);
    ResponseObject<Object> create(UserSystemDTOcreate object, String role);
    ResponseObject<Object> update(Object object);
    ResponseObject<Object> updateImage(String id, MultipartFile file);
    ResponseObject<Object> delete(String id);
    ResponseObject<Object> login(LoginParam param);
}
