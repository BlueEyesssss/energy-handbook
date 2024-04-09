package com.khaphp.energyhandbook.Service.Impl;

import com.khaphp.energyhandbook.Dto.FoodEncylopedia.FoodEncylopediaDTOcreate;
import com.khaphp.energyhandbook.Dto.FoodEncylopedia.FoodEncylopediaDTOupdate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOcreate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOupdate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Entity.FoodEncylopedia;
import com.khaphp.energyhandbook.Entity.News;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.Repository.FoodEncylopediaRepository;
import com.khaphp.energyhandbook.Repository.NewsRepository;
import com.khaphp.energyhandbook.Service.FileStore;
import com.khaphp.energyhandbook.Service.FoodEncylopediaService;
import com.khaphp.energyhandbook.Service.NewsService;
import com.khaphp.energyhandbook.Service.UserSystemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class FoodEncylopediaServiceImpl implements FoodEncylopediaService {
    @Autowired
    private FoodEncylopediaRepository foodEncylopediaRepository;

    @Autowired
    private UserSystemService userSystemService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileStore fileStore;

    @Value("${aws.s3.link_bucket}")
    private String linkBucket;

    @Value("${logo.energy_handbook.name}")
    private String logoName;
    @Override
    public ResponseObject<Object> getAll(int pageSize, int pageIndex) {
        Page<FoodEncylopedia> objListPage = null;
        List<FoodEncylopedia> objList = null;
        int totalPage = 0;
        //paging
        if(pageSize > 0 && pageIndex > 0){
            objListPage = foodEncylopediaRepository.findAll(PageRequest.of(pageIndex - 1, pageSize));  //vì current page ở code nó start = 0, hay bên ngoài la 2pga đầu tiên hay 1
            if(objListPage != null){
                totalPage = objListPage.getTotalPages();
                objList = objListPage.getContent();
            }
        }else{ //get all
            objList = foodEncylopediaRepository.findAll();
            pageIndex = 1;
        }
        objList.forEach(object -> object.setImg(linkBucket + object.getImg()));
        return ResponseObject.builder()
                .code(200).message("Success")
                .pageSize(objList.size()).pageIndex(pageIndex).totalPage(totalPage)
                .data(objList)
                .build();
    }

    @Override
    public ResponseObject<Object> getDetail(String id) {
        try{
            FoodEncylopedia object = foodEncylopediaRepository.findById(id).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            object.setImg(linkBucket + object.getImg());
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
    public ResponseObject<Object> create(FoodEncylopediaDTOcreate object) {
        try{
            UserSystem userSystem = (UserSystem) userSystemService.getDetail(object.getEmployeeId()).getData();
            if(userSystem == null){
                throw new Exception("user not found");
            }
            FoodEncylopedia object1 = modelMapper.map(object, FoodEncylopedia.class);
            object1.setUpdateDate(new Date(System.currentTimeMillis()));
            object1.setEmployee(userSystem);
            object1.setImg(logoName);
            foodEncylopediaRepository.save(object1);
            return ResponseObject.builder()
                    .code(200)
                    .message("Success")
                    .data(object1)
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> update(FoodEncylopediaDTOupdate object) {
        try{
            FoodEncylopedia object1 = foodEncylopediaRepository.findById(object.getId()).orElse(null);
            if(object1 == null) {
                throw new Exception("object not found");
            }
            object1.setName(object.getName());
            object1.setCalo(object.getCalo());
            object1.setUnit(object.getUnit());
            object1.setUpdateDate(new Date(System.currentTimeMillis()));
            foodEncylopediaRepository.save(object1);
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
    public ResponseObject<Object> updateImage(String id, MultipartFile file) {
        try{
            FoodEncylopedia object = foodEncylopediaRepository.findById(id).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            if(!object.getImg().equals(logoName)){
                fileStore.deleteImage(object.getImg());
            }

            //upload new img
            object.setImg(fileStore.uploadImg(file));
            foodEncylopediaRepository.save(object);
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
            FoodEncylopedia object = foodEncylopediaRepository.findById(id).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            if(!object.getImg().equals(logoName)){
                fileStore.deleteImage(object.getImg());
            }
            foodEncylopediaRepository.delete(object);
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
