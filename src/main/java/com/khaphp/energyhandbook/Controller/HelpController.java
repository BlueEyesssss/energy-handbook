package com.khaphp.energyhandbook.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/help")
public class HelpController {
    @GetMapping("/convert-date-str-to-long-second")
    public long convertDateStrToLongSecond(int year, int month, int day, int hour, int minute, int second){
        Date date = new Date(year - 1900, month - 1, day, hour, minute, second);
        return date.getTime()/1000;
    }

    @GetMapping("/convert-long-second-to-date-str")
    public String convertDateStrToLongSecond(long timestamp){
        Date date = new Date(timestamp * 1000);
        return date.toString();
    }
}
