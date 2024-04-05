package com.khaphp.energyhandbook.Service.Impl;

import com.khaphp.energyhandbook.Constant.Role;
import com.khaphp.energyhandbook.Constant.Status;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Dto.usersystem.UserSystemDTOcreate;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.Repository.UserSystemRepository;
import com.khaphp.energyhandbook.Service.FileStore;
import com.khaphp.energyhandbook.Service.UserSystemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
                throw new Exception("user not found");
            }
            userSystem.setImgUrl(linkBucket + userSystem.getImgUrl());
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
    public ResponseObject<Object> update(Object object) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject<Object> updateImage(String id, MultipartFile file) {
        String pathFileName = String.format("%s", UUID.randomUUID() + "_"+ file.getOriginalFilename());     //format: [UUID]_[name_img_from_local]
        //vd: 808930ba-f2e7-46b2-9110-451ce7b0a28a_face1.jpg --> nó lưu thằng trong folder buket của mình luôn
        //nếu format: A/[UUID]_[name_img_from_local] hay A/808930ba-f2e7-46b2-9110-451ce7b0a28a_face1.jpg
        // --> thì nó sẽ tạo thếm folder mới tên là A trong folder Bucket của mình và luu ảnh trong folder A đó
        // dựa vào đây có thể tạo nhiều folder cho từng user để có thể dễ quản lý img, file của họ
        try{
            //find user
            UserSystem userSystem = userRepository.findById(id).orElse(null);
            if(userSystem == null) {
                throw new Exception("user not found");
            }

            //check if the file is empty
            if (file.isEmpty()) {
                throw new IllegalStateException("Cannot upload empty file");
            }
            //check size <10Mb, 1MB = 1 048 576 bytes
            if(file.getSize() >= 10 * 1048576){
                throw new IllegalStateException("IMG size must smaller than 10MB");
            }
            //Check if the file is an image
            if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                    IMAGE_BMP.getMimeType(),
                    IMAGE_GIF.getMimeType(),
                    IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
                throw new IllegalStateException("FIle uploaded is not an image");
            }
            //get file metadata
            Map<String, String> metadata = new HashMap<>();
            metadata.put("Content-Type", file.getContentType());
            metadata.put("Content-Length", String.valueOf(file.getSize()));

            try {
                fileStore.upload(pathFileName, Optional.of(metadata), file.getInputStream());
            } catch (IOException e) {
                throw new IllegalStateException("Failed to upload file: " +e.getMessage());
            }

            try{
                userSystem.setImgUrl(pathFileName);
                userRepository.save(userSystem);
            }catch (Exception e){
                fileStore.deleteImage(pathFileName);
            }
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
            if(!userSystem.getImgUrl().equals("")){
                fileStore.deleteImage(userSystem.getImgUrl());
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
}
