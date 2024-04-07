package com.khaphp.energyhandbook.Service;

import com.khaphp.energyhandbook.Dto.Food.FoodDTOcreate;
import com.khaphp.energyhandbook.Dto.Food.FoodDTOupdate;
import com.khaphp.energyhandbook.Dto.Food.UpdateStatusFood;
import com.khaphp.energyhandbook.Dto.News.NewsDTOcreate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOupdate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

public interface FoodService {

    ResponseObject<Object> getAll(int pageSize, int pageIndex);
    ResponseObject<Object> getDetail(String id);
    ResponseObject<Object> create(FoodDTOcreate object);
    ResponseObject<Object> update(FoodDTOupdate object);
    ResponseObject<Object> updateImage(String id, MultipartFile file);
    ResponseObject<Object> updateStatus(UpdateStatusFood object);
    ResponseObject<Object> delete(String id);
}
