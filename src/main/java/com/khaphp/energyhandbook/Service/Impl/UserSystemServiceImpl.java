package com.khaphp.energyhandbook.Service.Impl;

import com.khaphp.energyhandbook.Constant.Role;
import com.khaphp.energyhandbook.Constant.Status;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Dto.usersystem.*;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.Repository.UserSystemRepository;
import com.khaphp.energyhandbook.Service.FileStore;
import com.khaphp.energyhandbook.Service.UserSystemService;
import com.khaphp.energyhandbook.Util.Security.JwtHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;

@Service
public class UserSystemServiceImpl implements UserSystemService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileStore fileStore;

    @Value("${aws.s3.link_bucket}")
    private String linkBucket;

    @Autowired
    private UserSystemRepository userRepository;

    @Autowired
    private JwtHelper jwtHelper;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public ResponseObject<Object> getAll(int pageSize, int pageIndex) {
        Page<UserSystem> objListPage = null;
        List<UserSystem> objList = null;
        int totalPage = 0;
        //paging
        if(pageSize > 0 && pageIndex > 0){
            objListPage = userRepository.findAll(PageRequest.of(pageIndex - 1, pageSize));  //vì current page ở code nó start = 0, hay bên ngoài la 2pga đầu tiên hay 1
            if(objListPage != null){
                totalPage = objListPage.getTotalPages();
                objList = objListPage.getContent();
            }
        }else{ //get all
            objList = userRepository.findAll();
            pageIndex = 1;
        }
        objList.forEach(userSystem -> userSystem.setImgUrl(linkBucket + userSystem.getImgUrl()));
        return ResponseObject.builder()
                .code(200).message("Success")
                .pageSize(objList.size()).pageIndex(pageIndex).totalPage(totalPage)
                .data(objList)
                .build();
    }

    @Override
    public ResponseObject<Object> getDetail(String id) {
        try{
            UserSystem userSystem = userRepository.findById(id).orElse(null);
            if(userSystem == null) {
                throw new Exception("user not found");
            }
            try {
                if(userSystem.getImgUrl() != null){
                    userSystem.setImgUrl(linkBucket + userSystem.getImgUrl());
                }
            }catch (Exception e){

            }

//            userSystem.setImgUrl(linkBucket + userSystem.getImgUrl());
            return ResponseObject.builder()
                    .code(200)
                    .message("Found")
                    .data(userSystem)
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: "+ e.getMessage())
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
            userSystem.setRole(role);
            userSystem.setPremium(false);
            userSystem.setBirthday(new Date(object.getBirthdayL() * 1000));
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
    public ResponseObject<Object> update(UserSystemDTOUpdate object) {
        try{
            UserSystem userSystem = userRepository.findById(object.getId()).orElse(null);
            if(userSystem == null) {
                throw new Exception("user not found");
            }
            userSystem.setName(object.getName());
            userSystem.setBirthday(new Date(object.getBirthdayL() * 1000));
            userSystem.setGender(object.getGender());
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
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject<Object> updateImage(String id, MultipartFile file) {
        try{
            //find user
            UserSystem userSystem = userRepository.findById(id).orElse(null);
            if(userSystem == null) {
                throw new Exception("user not found");
            }
            try {
                if(!userSystem.getImgUrl().equals("")){
                    fileStore.deleteImage(userSystem.getImgUrl());
                }
            }catch (Exception e){
                // img is null =>continue
            }
            userSystem.setImgUrl(fileStore.uploadImg(file));
            userRepository.save(userSystem);
            return ResponseObject.builder()
                    .code(200)
                    .message("Success")
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> delete(String id) {
        try{
            UserSystem userSystem = userRepository.findById(id).orElse(null);
            if(userSystem == null) {
                throw new Exception("user not found");
            }
            try{
                if(!userSystem.getImgUrl().equals("")){
                    fileStore.deleteImage(userSystem.getImgUrl());
                }
            }catch (Exception e){
                // img is null =>continue
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
                    .message("Exception: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> login(LoginParam param) {
        try{
            UserSystem userSystem = userRepository.findByUsername(param.getUsername()).orElse(null);
            if(userSystem == null) {
                throw new Exception("user not found");
            }
            if(userSystem.getStatus().equals(Status.DEACTIVE.toString())) {
                throw new Exception("Your account was Ban");
            }
            return ResponseObject.builder()
                    .code(200)
                    .message("Success")
                    .data(jwtHelper.generateToken(userSystem.getUsername(), Map.of("role", userSystem.getRole())))
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> changePassword(ChangePwdParam param) {
        try{
            UserSystem userSystem = userRepository.findById(param.getId()).orElse(null);
            if(userSystem == null) {
                throw new Exception("user not found");
            }
            if(!userSystem.getPassword().equals(param.getPassword())) {
                throw new Exception("Wrong old password");
            }
            userSystem.setPassword(param.getNewPassword());
            userRepository.save(userSystem);
            return ResponseObject.builder()
                    .code(200)
                    .message("Success")
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> updateStatus(UpdateStatusParam param) {
        try{
            UserSystem userSystem = userRepository.findById(param.getId()).orElse(null);
            if(userSystem == null) {
                throw new Exception("user not found");
            }
            userSystem.setStatus(param.getStatus());
            userRepository.save(userSystem);
            return ResponseObject.builder()
                    .code(200)
                    .message("Success")
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> updateEmail(UpdateEmailParam param) {
        try{
            UserSystem userSystem = userRepository.findById(param.getId()).orElse(null);
            if(userSystem == null) {
                throw new Exception("user not found");
            }
            userSystem.setEmail(param.getEmail());
            userRepository.save(userSystem);
            return ResponseObject.builder()
                    .code(200)
                    .message("Success")
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> updatePassword(NewPwdParam param) {
        try{
            UserSystem userSystem = userRepository.findById(param.getId()).orElse(null);
            if(userSystem == null) {
                throw new Exception("user not found");
            }
            userSystem.setPassword(param.getNewPassword());
            userRepository.save(userSystem);
            return ResponseObject.builder()
                    .code(200)
                    .message("Success")
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: " + e.getMessage())
                    .build();
        }
    }
}
