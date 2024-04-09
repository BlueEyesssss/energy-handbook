package com.khaphp.energyhandbook.Controller;

import com.khaphp.energyhandbook.Constant.Role;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Dto.Usersystem.*;
import com.khaphp.energyhandbook.Service.UserSystemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/user-system")
//@SecurityRequirement(name = "EnergyHandbook")
public class UserSystemController {
    @Autowired
    private UserSystemService userSystemService;

    @GetMapping
//    @PreAuthorize("hasAnyRole(" +
//            "'ROLE_"+Role.ADMIN+"'," +
//            "'ROLE_"+Role.EMPLOYEE+"'" +
//            ")")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "10") int pageSize,
                                    @RequestParam(defaultValue = "1") int pageIndex){
        ResponseObject responseObject = userSystemService.getAll(pageSize, pageIndex);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }
    @GetMapping("/detail")
    public ResponseEntity<?> getObject(String id){
        ResponseObject responseObject = userSystemService.getDetail(id);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PostMapping("/customer")
    public ResponseEntity<?> createObject(@RequestBody @Valid UserSystemDTOcreate object){
        ResponseObject responseObject = userSystemService.create(object, Role.CUSTOMER.toString());
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PostMapping("/employee")
    public ResponseEntity<?> createObject2(@RequestBody @Valid UserSystemDTOcreate object){
        ResponseObject responseObject = userSystemService.create(object, Role.EMPLOYEE.toString());
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PostMapping("/shipper")
    public ResponseEntity<?> createObject3(@RequestBody @Valid UserSystemDTOcreate object){
        ResponseObject responseObject = userSystemService.create(object, Role.SHIPPER.toString());
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping
    public ResponseEntity<?> updateObject(@RequestBody @Valid UserSystemDTOUpdate object){
        ResponseObject responseObject = userSystemService.update(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping("/change-pwd")
    public ResponseEntity<?> changePwd(@RequestBody @Valid ChangePwdParam object){
        ResponseObject responseObject = userSystemService.changePassword(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping("/new-pwd")
    public ResponseEntity<?> newPwd(@RequestBody @Valid NewPwdParam object){
        ResponseObject responseObject = userSystemService.updatePassword(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping("/status")
    public ResponseEntity<?> updateStatus(@RequestBody @Valid UpdateStatusParam object){
        ResponseObject responseObject = userSystemService.updateStatus(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping("/email")
    public ResponseEntity<?> updateEmail(@RequestBody @Valid UpdateEmailParam object){
        ResponseObject responseObject = userSystemService.updateEmail(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping(
            path = "/img",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,     //này là dể nó cho phép swagger upload file
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateImage(@RequestParam("id") String id,
                                         @RequestParam("file") MultipartFile file){
        ResponseObject responseObject = userSystemService.updateImage(id, file);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteObject(String id){
        ResponseObject responseObject = userSystemService.delete(id);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }
}
