package com.khaphp.energyhandbook.Service.Impl;

import com.khaphp.energyhandbook.Dto.FoodTutorial.FoodTutorialDTOcreate;
import com.khaphp.energyhandbook.Dto.FoodTutorial.FoodTutorialDTOcreateItem;
import com.khaphp.energyhandbook.Dto.FoodTutorial.FoodTutorialDTOupdate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Entity.CookingRecipe;
import com.khaphp.energyhandbook.Entity.FoodTutorial;
import com.khaphp.energyhandbook.Repository.FoodTutorialRepository;
import com.khaphp.energyhandbook.Service.CookingRecipeService;
import com.khaphp.energyhandbook.Service.FileStore;
import com.khaphp.energyhandbook.Service.FoodTutorialService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class FoodTutorialServiceImpl implements FoodTutorialService {
    public static final String NUMBER_ORDER_IN_FOOTTUTORIAL = "numberOrder";
    @Autowired
    private FoodTutorialRepository foodTutorialRepository;

    @Autowired
    private CookingRecipeService cookingRecipeService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileStore fileStore;

    @Value("${aws.s3.link_bucket}")
    private String linkBucket;

    @Value("${logo.energy_handbook.name}")
    private String logoName;
    @Override
    public ResponseObject<Object> getAll(int pageSize, int pageIndex, String cookingRecipeId) {
        Page<FoodTutorial> objListPage = null;
        List<FoodTutorial> objList = null;
        int totalPage = 0;
        //paging
        if(pageSize > 0 && pageIndex > 0){
            if(cookingRecipeId.equals("")){  //lấy hết
                objListPage = foodTutorialRepository.findAll(PageRequest.of(pageIndex - 1, pageSize));  //vì current page ở code nó start = 0, hay bên ngoài la 2pga đầu tiên hay 1
            }else{ //có filter theo customer
                objListPage = foodTutorialRepository.findAllByCookingRecipeId(cookingRecipeId, PageRequest.of(pageIndex - 1, pageSize).withSort(Sort.by(NUMBER_ORDER_IN_FOOTTUTORIAL)));
            }
            if(objListPage != null){
                totalPage = objListPage.getTotalPages();
                objList = objListPage.getContent();
            }
        }else{ //get all
            objList = foodTutorialRepository.findAll();
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
            FoodTutorial object = foodTutorialRepository.findById(id).orElse(null);
            if(object == null){
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
    public ResponseObject<Object> create(FoodTutorialDTOcreate object) {
        try{
            CookingRecipe cookingRecipe = (CookingRecipe) cookingRecipeService.getDetail(object.getCookingRecipeId()).getData();
            if(cookingRecipe == null){
                throw new Exception("CookingRecipe not found");
            }
            //vì getdetail có gắn link bucket vào r ên phải cắt ra
            cookingRecipe.setProductImg(cookingRecipe.getProductImg().substring(linkBucket.length()));
            cookingRecipe.setUpdateDate(new Date(System.currentTimeMillis()));
            for (FoodTutorialDTOcreateItem item: object.getItems()) {
                FoodTutorial recipeIngredients = modelMapper.map(item, FoodTutorial.class);
                recipeIngredients.setCookingRecipe(cookingRecipe);
                recipeIngredients.setImg(logoName);
                foodTutorialRepository.save(recipeIngredients);
            }
            return ResponseObject.builder()
                    .code(200)
                    .message("Success")
                    .data(foodTutorialRepository.findAllByCookingRecipeId(object.getCookingRecipeId(), null).getContent())
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> update(FoodTutorialDTOupdate object) {
        try{
            FoodTutorial object1 = foodTutorialRepository.findById(object.getId()).orElse(null);
            if(object1 == null) {
                throw new Exception("object not found");
            }
            object1.setNumberOrder(object.getNumberOrder());
            object1.setDescription(object.getDescription());
            //update date lại cho cooking recipe
            object1.getCookingRecipe().setUpdateDate(new Date(System.currentTimeMillis()));
            foodTutorialRepository.save(object1);
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
            FoodTutorial object = foodTutorialRepository.findById(id).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            if(!object.getImg().equals(logoName)){
                fileStore.deleteImage(object.getImg());
            }
            //upload new img
            object.setImg(fileStore.uploadImg(file));
            //update lại time cho cooking recipe của nó
            object.getCookingRecipe().setUpdateDate(new Date(System.currentTimeMillis()));
            foodTutorialRepository.save(object);
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
            FoodTutorial object = foodTutorialRepository.findById(id).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            if(!object.getImg().equals(logoName)){
                fileStore.deleteImage(object.getImg());
            }

            foodTutorialRepository.delete(object);
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
