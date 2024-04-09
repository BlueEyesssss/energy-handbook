package com.khaphp.energyhandbook.Service.Impl;

import com.khaphp.energyhandbook.Dto.CookingRecipe.CookingRecipeDTOcreate;
import com.khaphp.energyhandbook.Dto.CookingRecipe.CookingRecipeDTOdetail;
import com.khaphp.energyhandbook.Dto.CookingRecipe.CookingRecipeDTOupdate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Dto.Usersystem.UserSystemDTOviewInOrtherEntity;
import com.khaphp.energyhandbook.Entity.CookingRecipe;
import com.khaphp.energyhandbook.Entity.News;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.Repository.CookingRecipeRepository;
import com.khaphp.energyhandbook.Repository.UserSystemRepository;
import com.khaphp.energyhandbook.Service.CookingRecipeService;
import com.khaphp.energyhandbook.Service.FileStore;
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
public class CookingRecipeServiceImpl implements CookingRecipeService {
    public static final String DEFAULT_EMPLOYEE_MAIL = "employee@energy.handbook.com";
    @Autowired
    private CookingRecipeRepository cookingRecipeRepository;

    @Autowired
    private UserSystemRepository userSystemRepository;

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
    public ResponseObject<Object> getAll(int pageSize, int pageIndex, String customerId) {
        Page<CookingRecipe> objListPage = null;
        List<CookingRecipe> objList = null;
        int totalPage = 0;
        //paging
        if(pageSize > 0 && pageIndex > 0){
            if(customerId.equals("")){  //lấy hết
                objListPage = cookingRecipeRepository.findAll(PageRequest.of(pageIndex - 1, pageSize));  //vì current page ở code nó start = 0, hay bên ngoài la 2pga đầu tiên hay 1
            }else{ //có filter theo customer
                objListPage = cookingRecipeRepository.findByCustomerId(customerId, PageRequest.of(pageIndex - 1, pageSize));
            }
            if(objListPage != null){
                totalPage = objListPage.getTotalPages();
                objList = objListPage.getContent();
            }
        }else{ //get all
            objList = cookingRecipeRepository.findAll();
            pageIndex = 1;
        }
        objList.forEach(object -> object.setProductImg(linkBucket + object.getProductImg()));
        return ResponseObject.builder()
                .code(200).message("Success")
                .pageSize(objList.size()).pageIndex(pageIndex).totalPage(totalPage)
                .data(objList)
                .build();
    }

    @Override
    public ResponseObject<Object> getDetail(String id) {
        try{
            CookingRecipe object = cookingRecipeRepository.findById(id).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            object.setProductImg(linkBucket + object.getProductImg());
            CookingRecipeDTOdetail dto = modelMapper.map(object, CookingRecipeDTOdetail.class);
            dto.setEmployeeV(UserSystemDTOviewInOrtherEntity.builder()
                    .id(object.getEmployee().getId())
                    .name(object.getEmployee().getName())
                    .imgUrl(object.getEmployee().getImgUrl()).build());
            dto.setCustomerV(UserSystemDTOviewInOrtherEntity.builder()
                    .id(object.getCustomer().getId())
                    .name(object.getCustomer().getName())
                    .imgUrl(object.getCustomer().getImgUrl()).build());
            return ResponseObject.builder()
                    .code(200)
                    .message("Found")
                    .data(dto)
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: "+ e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> create(CookingRecipeDTOcreate object) {
        try{
            UserSystem userSystem = (UserSystem) userSystemService.getDetail(object.getCustomerId()).getData();
            if(userSystem == null){
                throw new Exception("user not found");
            }
            CookingRecipe cookingRecipe = modelMapper.map(object, CookingRecipe.class);
            cookingRecipe.setUpdateDate(new Date(System.currentTimeMillis()));
            cookingRecipe.setEmployee(userSystemRepository.findByEmail(DEFAULT_EMPLOYEE_MAIL));
            cookingRecipe.setCustomer(userSystem);
            cookingRecipe.setProductImg(logoName);
            cookingRecipeRepository.save(cookingRecipe);
            return ResponseObject.builder()
                    .code(200)
                    .message("Success")
                    .data(cookingRecipe)
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> update(CookingRecipeDTOupdate object) {
        try{
            CookingRecipe object1 = cookingRecipeRepository.findById(object.getId()).orElse(null);
            if(object1 == null) {
                throw new Exception("object not found");
            }
            object1.setName(object.getName());
            object1.setLevel(object.getLevel());
            object1.setTimeCook(object.getTimeCook());
            object1.setMealServing(object.getMealServing());
            object1.setDescription(object.getDescription());
            object1.setStatus(object.getStatus());
            object1.setUpdateDate(new Date(System.currentTimeMillis()));
            cookingRecipeRepository.save(object1);
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
            CookingRecipe object = cookingRecipeRepository.findById(id).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            try{
                //delete old img
                if(!object.getProductImg().equals("")){
                    fileStore.deleteImage(object.getProductImg());
                }
            }catch (Exception e){
                // img is null =>continue
            }
            //upload new img
            object.setProductImg(fileStore.uploadImg(file));
            cookingRecipeRepository.save(object);
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
            CookingRecipe object = cookingRecipeRepository.findById(id).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            if(!object.getProductImg().equals(logoName)){
                fileStore.deleteImage(object.getProductImg());
            }
            cookingRecipeRepository.delete(object);
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
