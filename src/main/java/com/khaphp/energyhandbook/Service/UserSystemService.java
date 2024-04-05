package com.khaphp.energyhandbook.Service;

import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Dto.usersystem.UserSystemDTOcreate;

public interface UserSystemService {

    ResponseObject<Object> getAll();
    ResponseObject<Object> getDetail(String id);
    ResponseObject<Object> create(UserSystemDTOcreate object, String role);
    ResponseObject<Object> update(Object object);
    ResponseObject<Object> delete(String id);
}
