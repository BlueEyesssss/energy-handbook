package com.khaphp.energyhandbook.Service.Impl;

import com.khaphp.energyhandbook.Constant.Role;
import com.khaphp.energyhandbook.Constant.Status;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Dto.usersystem.UserSystemDTOcreate;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.Repository.UserSystemRepository;
import com.khaphp.energyhandbook.Service.UserSystemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserSystemServiceImpl implements UserSystemService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserSystemRepository userRepository;
    @Override
    public ResponseObject<Object> getAll() {
        return ResponseObject.builder()
                .code(200)
                .message("Success")
                .data(userRepository.findAll())
                .build();
    }

    @Override
    public ResponseObject<Object> getDetail(String id) {
        try{
            UserSystem userSystem = userRepository.findById(id).orElse(null);
            if(userSystem == null) {
                return ResponseObject.builder()
                        .code(404)
                        .message("Not Found")
                        .data(null)
                        .build();
            }
            return ResponseObject.builder()
                    .code(200)
                    .message("Found")
                    .data(userSystem)
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception")
                    .data(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> create(UserSystemDTOcreate object, String role) {
        try{
            //check unique
            if(userRepository.existsByUsername(object.getUsername())) {
                throw new Exception("username exists");
            }
            if(userRepository.existsByEmail(object.getEmail())) {
                throw new Exception("email exists");
            }

            UserSystem userSystem = modelMapper.map(object, UserSystem.class);
            userSystem.setStatus(Status.ACTIVE.toString());
            userSystem.setRole(Role.CUSTOMER.toString());
            userSystem.setPremium(false);
            userSystem.setBirthday(new Date(object.getBirthdayL() * 1000));
            if(role.equals(Role.CUSTOMER.toString())) {
                userSystem.setRole(Role.CUSTOMER.toString());
            }else if(role.equals(Role.EMPLOYEE.toString())) {
                userSystem.setRole(Role.EMPLOYEE.toString());
            }else if(role.equals(Role.SHIPPER.toString())) {
                userSystem.setRole(Role.SHIPPER.toString());
            }
            userRepository.save(userSystem);
            return ResponseObject.builder()
                    .code(200)
                    .message("Success")
                    .data(userSystem)
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> update(Object object) {
        return null;
    }

    @Override
    public ResponseObject<Object> delete(String id) {
        try{
            UserSystem userSystem = userRepository.findById(id).orElse(null);
            if(userSystem == null) {
                return ResponseObject.builder()
                        .code(404)
                        .message("Not Found")
                        .data(null)
                        .build();
            }
            userRepository.delete(userSystem);
            return ResponseObject.builder()
                    .code(200)
                    .message("Success")
                    .data(null)
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception")
                    .data(e.getMessage())
                    .build();
        }
    }
}
