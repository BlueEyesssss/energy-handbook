package com.khaphp.energyhandbook.Controller;

import com.khaphp.energyhandbook.Dto.News.NewsDTOcreate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOupdate;
import com.khaphp.energyhandbook.Dto.Order.OrderDTOcreate;
import com.khaphp.energyhandbook.Dto.Order.OrderDTOupdate;
import com.khaphp.energyhandbook.Dto.Order.ParamCancelOrder;
import com.khaphp.energyhandbook.Dto.Order.ParamOrderShipperId;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Service.NewsService;
import com.khaphp.energyhandbook.Service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/order")
//@SecurityRequirement(name = "EnergyHandbook")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "10") int pageSize,
                                    @RequestParam(defaultValue = "1") int pageIndex){
        ResponseObject responseObject = ordersService.getAll(pageSize, pageIndex);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }
    @GetMapping("/detail")
    public ResponseEntity<?> getObject(String id){
        ResponseObject responseObject = ordersService.getDetail(id);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PostMapping
    @Operation(summary = "COD (guest, cus), WALLET (GUEST)",
    description = "nếu guest thì khỏi truyền customerId, còn nếu cus thìkho3oi3 truyền name, phone, address guest về")
    public ResponseEntity<?> createObject(@RequestBody @Valid OrderDTOcreate object) throws Exception {
        try{
            ResponseObject responseObject = ordersService.create(object);
            if(responseObject.getCode() == 200){
                return ResponseEntity.ok(responseObject);
            }
            return ResponseEntity.badRequest().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .code(400).message(e.getMessage())
                    .build());
        }
    }

    @PutMapping("/status")
    public ResponseEntity<?> updateObjectStatus(@RequestBody @Valid OrderDTOupdate object){
        ResponseObject responseObject = ordersService.updateStatus(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping("/shipper-take")
    public ResponseEntity<?> updateShipperTakeOrder(@RequestBody @Valid ParamOrderShipperId object){
        ResponseObject responseObject = ordersService.updateShipperTakeOrder(object.getOrderId(), object.getShipperId());
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping("/finish-delivery")
    public ResponseEntity<?> finishDeilivery(@RequestBody @Valid ParamOrderShipperId object){
        ResponseObject responseObject = ordersService.finishDeilivery(object.getOrderId(), object.getShipperId());
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping("/cancel")
    @Operation(description = "nếu ai là người hủy thì điển role + id của ng đó vào thôi")
    public ResponseEntity<?> cancelOrder(@RequestBody @Valid ParamCancelOrder object){
        ResponseObject responseObject = ordersService.cancelOrder(object.getOrderId(), object.getUserId(), object.getRole());
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteObject(String id){
        try{
            ResponseObject responseObject = ordersService.delete(id);
            if(responseObject.getCode() == 200){
                return ResponseEntity.ok(responseObject);
            }
            return ResponseEntity.badRequest().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .code(400).message(e.getMessage())
                    .build());
        }
    }
}
