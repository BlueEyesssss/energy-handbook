package com.khaphp.energyhandbook.Controller;

import com.khaphp.energyhandbook.Dto.Comment.CommentDTOcreate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comment")
//@SecurityRequirement(name = "EnergyHandbook")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "10") int pageSize,
                                    @RequestParam(defaultValue = "1") int pageIndex,
                                    @RequestParam(defaultValue = "") String cookingRecipeId){
        ResponseObject responseObject = commentService.getAll(pageSize, pageIndex, cookingRecipeId);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }
    @GetMapping("/child-comment")
    @Operation(description = "load cmt, giống trong tiktok ấy, vd: pagesize=5, pageindex=1, thì lúc đầu load lần dầu là 5 item, nếu load lần 2 hay (pageindex=2) thì số lượng trả về là 10 item (5 của lần dầu + 5 của lần sau), tương tự nếu lần 3 là 15, 4 là 20, ...")
    public ResponseEntity<?> getObject(@Parameter(description = "số lượng load mỗi lần tăng lên bao nhiêu") @RequestParam(defaultValue = "5") int pageSize,
                                       @Parameter(description = "số lần load") @RequestParam(defaultValue = "1") int pageIndex,
                                       @RequestParam(defaultValue = "") String id){
        ResponseObject responseObject = commentService.getChildComment(id, pageSize, pageIndex);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PostMapping
    public ResponseEntity<?> createObject(@RequestBody @Valid CommentDTOcreate object){
        ResponseObject responseObject = commentService.create(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteObject(String id){
        ResponseObject responseObject = commentService.delete(id);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }
}
