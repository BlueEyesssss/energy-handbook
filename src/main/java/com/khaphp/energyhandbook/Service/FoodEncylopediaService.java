package com.khaphp.energyhandbook.Service;

import com.khaphp.energyhandbook.Dto.FoodEncylopedia.FoodEncylopediaDTOcreate;
import com.khaphp.energyhandbook.Dto.FoodEncylopedia.FoodEncylopediaDTOupdate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOcreate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOupdate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

public interface FoodEncylopediaService {

    ResponseObject<Object> getAll();
    ResponseObject<Object> getDetail(String id);
    ResponseObject<Object> create(FoodEncylopediaDTOcreate object);
    ResponseObject<Object> update(FoodEncylopediaDTOupdate object);
    ResponseObject<Object> updateImage(String id, MultipartFile file);
    ResponseObject<Object> delete(String id);
}
