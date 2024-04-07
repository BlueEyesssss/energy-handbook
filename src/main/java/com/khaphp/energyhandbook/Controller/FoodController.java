package com.khaphp.energyhandbook.Controller;

import com.khaphp.energyhandbook.Dto.Food.FoodDTOcreate;
import com.khaphp.energyhandbook.Dto.Food.FoodDTOupdate;
import com.khaphp.energyhandbook.Dto.Food.UpdateStatusFood;
import com.khaphp.energyhandbook.Dto.News.NewsDTOcreate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOupdate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Service.FoodService;
import com.khaphp.energyhandbook.Service.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/food")
//@SecurityRequirement(name = "EnergyHandbook")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "10") int pageSize,
                                    @RequestParam(defaultValue = "1") int pageIndex){
        ResponseObject responseObject = foodService.getAll(pageSize, pageIndex);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }
    @GetMapping("/detail")
    public ResponseEntity<?> getObject(String id){
        ResponseObject responseObject = foodService.getDetail(id);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PostMapping
    public ResponseEntity<?> createObject(@RequestBody @Valid FoodDTOcreate object){
        ResponseObject responseObject = foodService.create(object);
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
        ResponseObject responseObject = foodService.updateImage(id, file);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping
    public ResponseEntity<?> updateObject(@RequestBody @Valid FoodDTOupdate object){
        ResponseObject responseObject = foodService.update(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping("/status")
    public ResponseEntity<?> updateObjectStatus(@RequestBody @Valid UpdateStatusFood object){
        ResponseObject responseObject = foodService.updateStatus(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteObject(String id){
        ResponseObject responseObject = foodService.delete(id);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }
}
