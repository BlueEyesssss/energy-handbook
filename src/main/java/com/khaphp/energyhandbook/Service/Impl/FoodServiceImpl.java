package com.khaphp.energyhandbook.Service.Impl;

import com.khaphp.energyhandbook.Constant.Status;
import com.khaphp.energyhandbook.Dto.Food.FoodDTOcreate;
import com.khaphp.energyhandbook.Dto.Food.FoodDTOupdate;
import com.khaphp.energyhandbook.Dto.Food.UpdateStatusFood;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Entity.Food;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.Repository.FoodRepository;
import com.khaphp.energyhandbook.Service.FileStore;
import com.khaphp.energyhandbook.Service.FoodService;
import com.khaphp.energyhandbook.Service.UserSystemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    private FoodRepository foodRepository;

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
        Page<Food> objListPage = null;
        List<Food> objList = null;
        int totalPage = 0;
        //paging
        if(pageSize > 0 && pageIndex > 0){
            objListPage = foodRepository.findAll(PageRequest.of(pageIndex - 1, pageSize));  //vì current page ở code nó start = 0, hay bên ngoài la 2pga đầu tiên hay 1
            if(objListPage != null){
                totalPage = objListPage.getTotalPages();
                objList = objListPage.getContent();
            }
        }else{ //get all
            objList = foodRepository.findAll();
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
            Food object = foodRepository.findById(id).orElse(null);
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
    public ResponseObject<Object> create(FoodDTOcreate object) {
        try{
            UserSystem userSystem = (UserSystem) userSystemService.getDetail(object.getEmployeeId()).getData();
            if(userSystem == null){
                throw new Exception("user not found");
            }
            Food object1 = modelMapper.map(object, Food.class);
            object1.setUpdateDate(new Date(System.currentTimeMillis()));
            object1.setEmployee(userSystem);
            object1.setStatus(Status.ACTIVE.toString());
            object1.setImg(logoName);
            foodRepository.save(object1);
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
    public ResponseObject<Object> update(FoodDTOupdate object) {
        try{
            Food object1 = foodRepository.findById(object.getId()).orElse(null);
            if(object1 == null) {
                throw new Exception("object not found");
            }   // name unit stoc, price, sale location
            object1.setName(object.getName());
            object1.setUnit(object.getUnit());
            object1.setStock(object.getStock());
            object1.setPrice(object.getPrice());
            object1.setLocation(object.getLocation());
            object1.setSale(object.getSale());
            object1.setUpdateDate(new Date(System.currentTimeMillis()));
            foodRepository.save(object1);
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
            Food object = foodRepository.findById(id).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            if(!object.getImg().equals(logoName)){
                fileStore.deleteImage(object.getImg());
            }
            //upload new img
            object.setImg(fileStore.uploadImg(file));
            foodRepository.save(object);
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
    public ResponseObject<Object> updateStatus(UpdateStatusFood object1) {
        try{
            Food object = foodRepository.findById(object1.getId()).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            object.setStatus(object1.getStatus());
            foodRepository.save(object);
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
            Food object = foodRepository.findById(id).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            if(!object.getImg().equals(logoName)){
                fileStore.deleteImage(object.getImg());
            }

            foodRepository.delete(object);
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
