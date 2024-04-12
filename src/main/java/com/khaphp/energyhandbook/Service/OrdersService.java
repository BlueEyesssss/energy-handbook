package com.khaphp.energyhandbook.Service;

import com.khaphp.energyhandbook.Dto.News.NewsDTOcreate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOupdate;
import com.khaphp.energyhandbook.Dto.Order.OrderDTOcreate;
import com.khaphp.energyhandbook.Dto.Order.OrderDTOupdate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

public interface OrdersService {

    ResponseObject<Object> getAll(int pageSize, int pageIndex);
    ResponseObject<Object> getDetail(String id);
    ResponseObject<Object> create(OrderDTOcreate object) throws Exception;
    ResponseObject<Object> updateStatus(OrderDTOupdate object);
    ResponseObject<Object> updateShipperTakeOrder(String orderId, String shipperId);
    ResponseObject<Object> finishDeilivery(String orderId, String shipperId);
    ResponseObject<Object> cancelOrder(String orderId, String customerId, String role);
    ResponseObject<Object> delete(String id);
}
