package com.khaphp.energyhandbook.Service.Impl;

import com.khaphp.energyhandbook.Dto.Notification.NotificationDTOcreate;
import com.khaphp.energyhandbook.Dto.Notification.NotificationDTOupdate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Entity.News;
import com.khaphp.energyhandbook.Entity.Notification;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.Repository.NotificationRepository;
import com.khaphp.energyhandbook.Repository.UserSystemRepository;
import com.khaphp.energyhandbook.Service.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserSystemRepository userSystemRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ResponseObject<Object> getAll(int pageSize, int pageIndex, String userId) {
        Page<Notification> objListPage = null;
        List<Notification> objList = null;
        int totalPage = 0;
        //paging
        if(pageSize > 0 && pageIndex > 0){
            if(!userId.equals("")){
                objListPage = notificationRepository.findByUserId(userId, PageRequest.of(pageIndex - 1, pageSize));
            }else{
                objListPage = notificationRepository.findAll(PageRequest.of(pageIndex - 1, pageSize));  //vì current page ở code nó start = 0, hay bên ngoài la 2pga đầu tiên hay 1
            }
            if(objListPage != null){
                totalPage = objListPage.getTotalPages();
                objList = objListPage.getContent();
            }
        }else{ //get all
            objList = notificationRepository.findAll();
            pageIndex = 1;
        }
        return ResponseObject.builder()
                .code(200).message("Success")
                .pageSize(objList.size()).pageIndex(pageIndex).totalPage(totalPage)
                .data(objList)
                .build();
    }

    @Override
    public ResponseObject<Object> getDetail(String id) {
        try{
            Notification object = notificationRepository.findById(id).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            return ResponseObject.builder()
                    .code(200)
                    .message("Found")
                    .data(object)
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: "+ e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> create(NotificationDTOcreate object) {
        try{
            UserSystem userSystem = userSystemRepository.findById(object.getUserId()).orElse(null);
            if(userSystem == null){
                throw new Exception("user not found");
            }
            Notification notification = modelMapper.map(object, Notification.class);
            notification.setUser(userSystem);
            notification.setCreateDate(new Date(System.currentTimeMillis()));
            notification.setSeen(false);
            notificationRepository.save(notification);
            return ResponseObject.builder()
                    .code(200)
                    .message("Success")
                    .data(notification)
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> update(NotificationDTOupdate object) {
        try{
            Notification object1 = notificationRepository.findById(object.getId()).orElse(null);
            if(object1 == null) {
                throw new Exception("object not found");
            }
            object1.setTitle(object.getTitle());
            object1.setDescription(object.getDescription());
            notificationRepository.save(object1);
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
    public ResponseObject<Object> updateSeen(String notIid) {
        try{
            Notification object1 = notificationRepository.findById(notIid).orElse(null);
            if(object1 == null) {
                throw new Exception("object not found");
            }
            object1.setSeen(true);
            notificationRepository.save(object1);
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
            Notification object = notificationRepository.findById(id).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            notificationRepository.delete(object);
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
