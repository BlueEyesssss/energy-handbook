package com.khaphp.energyhandbook.Service;

import com.khaphp.energyhandbook.Dto.News.NewsDTOcreate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOupdate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Dto.usersystem.*;
import org.springframework.web.multipart.MultipartFile;

public interface NewsService {

    ResponseObject<Object> getAll(int pageSize, int pageIndex);
    ResponseObject<Object> getDetail(String id);
    ResponseObject<Object> create(NewsDTOcreate object);
    ResponseObject<Object> update(NewsDTOupdate object);
    ResponseObject<Object> updateImage(String id, MultipartFile file);
    ResponseObject<Object> delete(String id);
}
