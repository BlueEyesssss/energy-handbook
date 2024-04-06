package com.khaphp.energyhandbook.Controller;

import com.khaphp.energyhandbook.Constant.Role;
import com.khaphp.energyhandbook.Dto.usersystem.*;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.Repository.UserSystemRepository;
import com.khaphp.energyhandbook.Service.UserSystemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/customer")
//@SecurityRequirement(name = "EnergyHandbook")
public class UserSystemController {
    @Autowired
    private UserSystemService userSystemService;

    @GetMapping
//    @PreAuthorize("hasAnyRole(" +
//            "'ROLE_"+Role.ADMIN+"'," +
//            "'ROLE_"+Role.EMPLOYEE+"'" +
//            ")")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(userSystemService.getAll());
    }
    @GetMapping("/detail")
    public ResponseEntity<?> getObject(String id){
        return ResponseEntity.ok(userSystemService.getDetail(id));
    }

    @PostMapping("/customer")
    public ResponseEntity<?> createObject(@RequestBody @Valid UserSystemDTOcreate object){
        return ResponseEntity.ok(userSystemService.create(object, Role.CUSTOMER.toString()));
    }

    @PostMapping("/employee")
    public ResponseEntity<?> createObject2(@RequestBody @Valid UserSystemDTOcreate object){
        return ResponseEntity.ok(userSystemService.create(object, Role.EMPLOYEE.toString()));
    }

    @PostMapping("/shipper")
    public ResponseEntity<?> createObject3(@RequestBody @Valid UserSystemDTOcreate object){
        return ResponseEntity.ok(userSystemService.create(object, Role.SHIPPER.toString()));
    }

    @PutMapping
    public ResponseEntity<?> updateObject(@RequestBody @Valid UserSystemDTOUpdate object){
        return ResponseEntity.ok(userSystemService.update(object));
    }

    @PutMapping("/change-pwd")
    public ResponseEntity<?> changePwd(@RequestBody @Valid ChangePwdParam object){
        return ResponseEntity.ok(userSystemService.changePassword(object));
    }

    @PutMapping("/new-pwd")
    public ResponseEntity<?> newPwd(@RequestBody @Valid NewPwdParam object){
        return ResponseEntity.ok(userSystemService.updatePassword(object));
    }

    @PutMapping("/status")
    public ResponseEntity<?> updateStatus(@RequestBody @Valid UpdateStatusParam object){
        return ResponseEntity.ok(userSystemService.updateStatus(object));
    }

    @PutMapping("/email")
    public ResponseEntity<?> updateEmail(@RequestBody @Valid UpdateEmailParam object){
        return ResponseEntity.ok(userSystemService.updateEmail(object));
    }

    @PutMapping(
            path = "/img",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,     //này là dể nó cho phép swagger upload file
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateImage(@RequestParam("id") String id,
                                         @RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(userSystemService.updateImage(id, file));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteObject(String id){
        return ResponseEntity.ok(userSystemService.delete(id));
    }
}
