package com.khaphp.energyhandbook.Service;

import com.khaphp.energyhandbook.Dto.News.NewsDTOcreate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOupdate;
import com.khaphp.energyhandbook.Dto.Notification.NotificationDTOcreate;
import com.khaphp.energyhandbook.Dto.Notification.NotificationDTOupdate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

public interface NotificationService {

    ResponseObject<Object> getAll(int pageSize, int pageIndex, String userId);
    ResponseObject<Object> getDetail(String id);
    ResponseObject<Object> create(NotificationDTOcreate object);
    ResponseObject<Object> update(NotificationDTOupdate object);
    ResponseObject<Object> updateSeen(String NotIid);
    ResponseObject<Object> delete(String id);
}
