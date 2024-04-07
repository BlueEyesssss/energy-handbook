package com.khaphp.energyhandbook.Controller;

import com.khaphp.energyhandbook.Dto.FoodEncylopedia.FoodEncylopediaDTOcreate;
import com.khaphp.energyhandbook.Dto.FoodEncylopedia.FoodEncylopediaDTOupdate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOcreate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOupdate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Service.FoodEncylopediaService;
import com.khaphp.energyhandbook.Service.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/food-encyclopedia")
//@SecurityRequirement(name = "EnergyHandbook")
public class FoodEncylopediaController {
    @Autowired
    private FoodEncylopediaService foodEncylopediaService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "10") int pageSize,
                                    @RequestParam(defaultValue = "1") int pageIndex){
        ResponseObject responseObject = foodEncylopediaService.getAll(pageSize, pageIndex);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }
    @GetMapping("/detail")
    public ResponseEntity<?> getObject(String id){
        ResponseObject responseObject = foodEncylopediaService.getDetail(id);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PostMapping
    public ResponseEntity<?> createObject(@RequestBody @Valid FoodEncylopediaDTOcreate object){
        ResponseObject responseObject = foodEncylopediaService.create(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping(
            path = "/img",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateImg(@RequestParam("id") String id,
                                       @RequestParam("file") MultipartFile file){
        ResponseObject responseObject = foodEncylopediaService.updateImage(id, file);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping
    public ResponseEntity<?> updateObject(@RequestBody @Valid FoodEncylopediaDTOupdate object){
        ResponseObject responseObject = foodEncylopediaService.update(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteObject(String id){
        ResponseObject responseObject = foodEncylopediaService.delete(id);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }
}
