package com.khaphp.energyhandbook.Service.Impl;

import com.khaphp.energyhandbook.Constant.EmailDefault;
import com.khaphp.energyhandbook.Constant.Method;
import com.khaphp.energyhandbook.Constant.Role;
import com.khaphp.energyhandbook.Constant.StatusOrder;
import com.khaphp.energyhandbook.Dto.Food.FoodDTOviewInOrtherEntity;
import com.khaphp.energyhandbook.Dto.Notification.NotificationDTOcreate;
import com.khaphp.energyhandbook.Dto.Order.OrderDTOcreate;
import com.khaphp.energyhandbook.Dto.Order.OrderDTOupdate;
import com.khaphp.energyhandbook.Dto.Order.OrderDTOviewDetail;
import com.khaphp.energyhandbook.Dto.OrderDetail.OrderDetailDToviewInOrtherEntity;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Dto.Usersystem.UserSystemDTOviewInOrtherEntity;
import com.khaphp.energyhandbook.Dto.WalletTransaction.WalletTransactionDTOcreate;
import com.khaphp.energyhandbook.Entity.*;
import com.khaphp.energyhandbook.Entity.keys.OrderDetailKey;
import com.khaphp.energyhandbook.Dto.OrderDetail.OrderDetailDTOcreate;
import com.khaphp.energyhandbook.Repository.*;
import com.khaphp.energyhandbook.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private WalletTransactionService walletTransactionService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private PaymentOrderRepository paymentOrderRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserSystemService userSystemService;

    @Autowired
    private UserSystemRepository userSystemRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${aws.s3.link_bucket}")
    private String linkBucket;

    @Autowired
    private NotificationService notificationService;

    @Override
    public ResponseObject<Object> getAll(int pageSize, int pageIndex) {
        Page<Order> objListPage = null;
        List<Order> objList = null;
        int totalPage = 0;
        //paging
        if(pageSize > 0 && pageIndex > 0){
            objListPage = ordersRepository.findAll(PageRequest.of(pageIndex - 1, pageSize));  //vì current page ở code nó start = 0, hay bên ngoài la 2pga đầu tiên hay 1
            if(objListPage != null){
                totalPage = objListPage.getTotalPages();
                objList = objListPage.getContent();
            }
        }else{ //get all
            objList = ordersRepository.findAll();
            pageIndex = 1;
        }
        return ResponseObject.builder()
                .code(200).message("Success")
                .pageSize(objList.size()).pageIndex(pageIndex).totalPage(totalPage)
                .data(objList)
                .build();
    }

    @Override
    public ResponseObject<Object> getDetail(String id) {
        try{
            Order object = ordersRepository.findById(id).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            OrderDTOviewDetail dto = modelMapper.map(object, OrderDTOviewDetail.class);
            dto.setCustomerV(UserSystemDTOviewInOrtherEntity.builder()
                    .id(object.getCustomer().getId())
                    .name(object.getCustomer().getName())
                    .imgUrl(linkBucket + object.getCustomer().getImgUrl())
                    .build());
            dto.setEmployeeV(UserSystemDTOviewInOrtherEntity.builder()
                    .id(object.getEmployee().getId())
                    .name(object.getEmployee().getName())
                    .imgUrl(linkBucket + object.getEmployee().getImgUrl())
                    .build());
            dto.setShipperV(UserSystemDTOviewInOrtherEntity.builder()
                    .id(object.getShipper().getId())
                    .name(object.getShipper().getName())
                    .imgUrl(linkBucket + object.getShipper().getImgUrl())
                    .build());
            dto.setOrderDetailsV(new ArrayList<>());
            for (OrderDetail orderDetail : object.getOrderDetails()) {
                OrderDetailDToviewInOrtherEntity orderDetailDToviewInOrtherEntity = modelMapper.map(orderDetail, OrderDetailDToviewInOrtherEntity.class);
                FoodDTOviewInOrtherEntity foodDTOviewInOrtherEntity = modelMapper.map(orderDetail.getFood(), FoodDTOviewInOrtherEntity.class);
                foodDTOviewInOrtherEntity.setImg(linkBucket + foodDTOviewInOrtherEntity.getImg());
                orderDetailDToviewInOrtherEntity.setFoodV(foodDTOviewInOrtherEntity);
                dto.getOrderDetailsV().add(orderDetailDToviewInOrtherEntity);
            }
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
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject<Object> create(OrderDTOcreate object) throws Exception {
        try{
            //check user
            UserSystem userSystem = validateUserInOrder(object);

            //create order trc
            Order order = createOrder(userSystem, object);
            order = ordersRepository.save(order);

            //nếu là wallet thì check balance để trừ tiền
            if(object.getMethod().equals(Method.WALLET.toString())){
                checkBalanceToMinus(order, userSystem, object);
            }

            //create order detail
            for (OrderDetailDTOcreate orderDetailDTOcreate : object.getOrderDetails()) {
                OrderDetail orderDetail = createOrderDetail(order, orderDetailDTOcreate);
                //cập nhật total price cho order
                order.setTotalPrice(order.getTotalPrice() + (orderDetail.getAmount() * orderDetail.getPrice()));
            }

            //create payment order
            createPaymentOrder(order, object);

            return ResponseObject.builder()
                    .code(200)
                    .message("Success")
                    .data(order)
                    .build();
        }catch (Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject<Object> orderThirdParty(HttpServletRequest req, OrderDTOcreate object) throws Exception {
        try{
            //check user
            UserSystem userSystem = validateUserInOrder(object);

            //create order trc
            Order order = createOrder(userSystem, object);
            order = ordersRepository.save(order);

            //create order detail
            for (OrderDetailDTOcreate orderDetailDTOcreate : object.getOrderDetails()) {
                OrderDetail orderDetail = createOrderDetail(order, orderDetailDTOcreate);
                //cập nhật total price cho order
                order.setTotalPrice(order.getTotalPrice() + (orderDetail.getAmount() * orderDetail.getPrice()));
            }

            //create payment order
            createPaymentOrder(order, object);

            //gọi vn pay và nhận rs trả về để đổi status order
            ResponseObject urlRs = paymentService.createPayment(req, Math.round(order.getTotalPrice()), userSystem.getId(), userSystemService, true, order.getId());
            if(urlRs.getCode() != 200){
                throw new Exception(urlRs.getMessage());
            }

//            //chuyển hướng đến url payment của vn pay
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.LOCATION, (String) urlRs.getData());
//            return new ResponseEntity<>(headers, HttpStatus.FOUND);

            return ResponseObject.builder()
                    .code(200)
                    .message("Success")
                    .data((String) urlRs.getData())
                    .build();
        }catch (Exception e){
            throw new Exception("Exception: " + e.getMessage());
        }
    }

    private UserSystem validateUserInOrder(OrderDTOcreate object) throws Exception{
        UserSystem userSystem = null;
        if(object.getPhoneGuest() != null){
            //guest order COD
            userSystem = userSystemRepository.findByEmail(EmailDefault.CUSTOMER_EMAIL_DEFAULT);
        }else{
            //user order
            userSystem =  userSystemRepository.findById(object.getCustomerId()).orElse(null);
        }
        if(userSystem == null){
            throw new Exception("user not found");
        }
        return userSystem;
    }

    private void createPaymentOrder(Order order, OrderDTOcreate object) throws Exception{
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setOrder(order);
        paymentOrder.setMethod(object.getMethod());
        if(object.getPhoneGuest() != null){
            paymentOrder.setNameGuest(object.getNameGuest());
            paymentOrder.setPhoneGuest(object.getPhoneGuest());
            paymentOrder.setAddress(object.getAddress());
        }
        paymentOrderRepository.save(paymentOrder);
    }

    private OrderDetail createOrderDetail(Order order, OrderDetailDTOcreate orderDetailDTOcreate) throws Exception{
        OrderDetail orderDetail = modelMapper.map(orderDetailDTOcreate, OrderDetail.class);
        Food food = foodRepository.findById(orderDetailDTOcreate.getFoodId()).orElse(null);
        if(food == null){
            throw new Exception("food not found");
        }
        //check stock của food trogn kho xem còn đủ không
        if(food.getStock() < orderDetail.getAmount()){
            throw new Exception("stock not enough");
        }
        orderDetail.setId(new OrderDetailKey(food.getId(), order.getId()));
        orderDetail.setFood(food);
        orderDetail.setOrder(order);
        orderDetailRepository.save(orderDetail);

        //cập nhật lại stock cho food
        food.setStock(food.getStock() - orderDetail.getAmount());
        foodRepository.save(food);

        return orderDetail;
    }

    private void checkBalanceToMinus(Order order, UserSystem userSystem, OrderDTOcreate object) throws Exception {
        int totalPrice = 0;
        //tính totalprice trước để check tiền trừ
        for (OrderDetailDTOcreate orderDetailDTOcreate : object.getOrderDetails()) {
            totalPrice += orderDetailDTOcreate.getPrice() * orderDetailDTOcreate.getAmount();
        }
        if(userSystem.getWallet().getBalance() < totalPrice){
            throw new Exception("balance not enough");
        }else{
            //thanh toán tiền
            userSystem.getWallet().setBalance(userSystem.getWallet().getBalance() - totalPrice);
            userSystemRepository.save(userSystem);

            //create wallet transaction luôn
            ResponseObject rs = walletTransactionService.create(WalletTransactionDTOcreate.builder()
                    .customerId(object.getCustomerId())
                    .amount(-totalPrice).description("Thanh toan don hang " +order.getId())
                    .createDate(new Date(System.currentTimeMillis()))
                    .build());
            if(rs.getCode() != 200){
                throw new Exception("Wallet transaction " +rs.getMessage());
            }
        }
    }

    private Order createOrder(UserSystem userSystem, OrderDTOcreate object) throws Exception {
        Order order = new Order();
        order.setCreateDate(new Date(System.currentTimeMillis()));
        order.setCustomer(userSystem);
        order.setEmployee(userSystemRepository.findByEmail(EmailDefault.EMPLOYEE_EMAIL_DEFAULT));
        order.setShipper(userSystemRepository.findByEmail(EmailDefault.SHIPPER_EMAIL_DEFAULT));
        if(     object.getMethod().equals(Method.COD.toString()) ||
                object.getMethod().equals(Method.THIRDPARTY.toString())){   //vì COD, THIRDPARTY mới cần check duyệt, còn cái kia là thanh toán đơn luôn rồi
            order.setStatus(StatusOrder.PENDING.toString());
        }else{
            order.setStatus(StatusOrder.ACCEPT.toString());
        }
        order.setUpdateDate(null);
        order.setDeliveryTime(null);
        return order;
    }

    @Override
    public ResponseObject<Object> updateStatus(OrderDTOupdate object) {
        try{
            Order object1 = ordersRepository.findById(object.getOrderId()).orElse(null);
            if(object1 == null) {
                throw new Exception("Order not found");
            }
            UserSystem employee = userSystemRepository.findById(object.getEmployeeId()).orElse(null);
            if(employee == null){
                throw new Exception("employee is not found");
            }
            object1.setStatus(object.getStatus());
            object1.setUpdateDate(new Date(System.currentTimeMillis()));
            if(     object.getStatus().equals(StatusOrder.REJECT.toString()) ||
                    object.getStatus().equals(StatusOrder.CANCEL.toString())){
                //trả lại stock cho food
                for (OrderDetail orderDetail: object1.getOrderDetails()){
                    orderDetail.getFood().setStock(orderDetail.getFood().getStock() + orderDetail.getAmount());
                }
            }
            ordersRepository.save(object1);
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
    public ResponseObject<Object> updateShipperTakeOrder(String orderId, String shipperId) {
        try{
            Order object1 = ordersRepository.findById(orderId).orElse(null);
            if(object1 == null) {
                throw new Exception("Order not found");
            }
            UserSystem shipper = userSystemRepository.findById(shipperId).orElse(null);
            if(shipper == null){
                throw new Exception("shipper is not found");
            }
            if(!object1.getStatus().equals(StatusOrder.ACCEPT.toString())){
                throw new Exception("order must be accept");
            }
            object1.setStatus(StatusOrder.WAITING.toString());
            object1.setUpdateDate(new Date(System.currentTimeMillis()));
            object1.setShipper(shipper);
            ordersRepository.save(object1);

            //noti đến ch order để báo cho họ bik
            ResponseObject rs = notificationService.create(NotificationDTOcreate.builder()
                    .userId(object1.getCustomer().getId())
                    .title(shipper.getName() +" đã tiếp nhận đơn hàng " + orderId + " của bạn")
                    .build());
            if(rs.getCode() != 200){
                throw new Exception(rs.getMessage());
            }
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
    public ResponseObject<Object> finishDeilivery(String orderId, String shipperId) {
        try{
            Order object1 = ordersRepository.findById(orderId).orElse(null);
            if(object1 == null) {
                throw new Exception("Order not found");
            }
            UserSystem shipper = userSystemRepository.findById(shipperId).orElse(null);
            if(shipper == null){
                throw new Exception("shipper is not found");
            }
            if(!object1.getStatus().equals(StatusOrder.WAITING.toString())){
                throw new Exception("order must be WAITING");
            }
            object1.setStatus(StatusOrder.FINISH.toString());
            object1.setUpdateDate(new Date(System.currentTimeMillis()));
            object1.setDeliveryTime(new Date(System.currentTimeMillis()));
            object1.setShipper(shipper);
            ordersRepository.save(object1);
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
    public ResponseObject<Object> cancelOrder(String orderId, String userId, String role) {
        try{
            Order object1 = ordersRepository.findById(orderId).orElse(null);
            if(object1 == null) {
                throw new Exception("Order not found");
            }
            //valid role
            List<String>roleChekc = List.of(Role.CUSTOMER.toString(), Role.SHIPPER.toString(), Role.EMPLOYEE.toString());
            if(!roleChekc.contains(role)){
                throw new Exception("role is not valid, must be CUSTOMER/ SHIPPER or EMPLOYEE");
            }

            //valid user
            UserSystem userSystem = userSystemRepository.findById(userId).orElse(null);
            if(userSystem == null){
                throw new Exception("user is not found");
            }
            if(!roleChekc.contains(userSystem.getRole())){
                throw new Exception("role is not valid, must be CUSTOMER/ SHIPPER or EMPLOYEE");
            }
            if(role.equals(Role.CUSTOMER.toString())){
                if (!object1.getCustomer().getId().equals(userSystem.getId())) {    //check xem order phải của cus này ko
                    throw new Exception("user is not own this order");
                }
                if(     object1.getStatus().equals(StatusOrder.ACCEPT.toString()) ||
                        object1.getStatus().equals(StatusOrder.WAITING.toString()) ||
                        object1.getStatus().equals(StatusOrder.FINISH.toString())){     //nếu order đã ACCPET thì ko CANCEL đc
                    throw new Exception("order can not cancel");
                }
            }else if(role.equals(Role.SHIPPER.toString())){
                if (!object1.getShipper().getId().equals(userSystem.getId())) {    //check xem order phải của shipper này ko
                    throw new Exception("shipper is not own this order");
                }
            }
            //còn employee thì đứa nào cancel cũng đc
            object1.setStatus(StatusOrder.CANCEL.toString());
            object1.setUpdateDate(new Date(System.currentTimeMillis()));
            //trả lại stock cho food
            for (OrderDetail orderDetail: object1.getOrderDetails()){
                orderDetail.getFood().setStock(orderDetail.getFood().getStock() + orderDetail.getAmount());
            }

            ordersRepository.save(object1);
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
            Order object = ordersRepository.findById(id).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            //delete order detail
            for (OrderDetail orderdetail: object.getOrderDetails()) {
                orderDetailRepository.delete(orderdetail);
            }
            //delete payment order
            paymentOrderRepository.delete(object.getPaymentOrder());

            //delete order
            ordersRepository.delete(object);
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
