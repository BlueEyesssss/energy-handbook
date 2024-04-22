package com.khaphp.energyhandbook.Configuration;

import com.khaphp.energyhandbook.Constant.Role;
import com.khaphp.energyhandbook.Dto.Notification.NotificationDTOcreate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.Repository.UserSystemRepository;
import com.khaphp.energyhandbook.Service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;
import java.util.List;

@Configuration
@EnableScheduling
@Slf4j
public class BackGroundJob {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserSystemRepository userSystemRepository;

    @Scheduled(cron = "1 0 0 * * ?")
//    @Scheduled(cron = "10 * * * * *")
    public void birthdayJob() {
        //format in db: 2001-01-01 01:01:01.000000
        //lấy date hiện tại ra
        Calendar calendar = Calendar.getInstance();
        String date = calendar.get(Calendar.DATE) + "";
        if(date.length() == 1) {
            date = "0" + date;
        }
        String month = (calendar.get(Calendar.MONTH) + 1) + "";
        if(month.length() == 1) {
            month = "0" + month;
        }
        String dateNoew = month + "-" + date;
        System.out.println("check birthday: " + dateNoew);
        //lấy hết user có ngày sinh hôm nay ra
        List<UserSystem> listCustomer = userSystemRepository.findByBirthdayByRole("%" + dateNoew + "%", Role.CUSTOMER.toString());
        if(listCustomer.size() > 0){
            listCustomer.forEach(x -> {
                System.out.println(x.getName());
                ResponseObject rs = notificationService.create(NotificationDTOcreate.builder()
                        .userId(x.getId())
                        .title("Chúc mừng sinh nhật")
                        .description("Energy HandBook chúc mừng sinh nhật " + x.getName())
                        .build());
                if(rs.getCode() != 200){
                    log.error("Noti sinh nhật cho "+ x.getName()  +": " + rs.getMessage());
                }
            });
        }
    }
}
