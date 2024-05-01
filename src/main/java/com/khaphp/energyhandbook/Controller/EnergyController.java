package com.khaphp.energyhandbook.Controller;

import com.khaphp.energyhandbook.Dto.CaculateEnergy.CaloriesReport;
import com.khaphp.energyhandbook.Dto.CaculateEnergy.EnergyBMIReport;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Util.EnergyCaculate;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/energy")
public class EnergyController {
    @GetMapping("/bmi")
    public ResponseEntity<ResponseObject> calculateBMI(@Parameter(description = "weight in kg") double weight,
                                                       @Parameter(description = "height in m") double height) {
        try{
            EnergyCaculate energyCaculate = new EnergyCaculate();
            EnergyBMIReport report = energyCaculate.caculateBMI(weight, height);
            return ResponseEntity.ok(ResponseObject.builder().data(report).build());
        }catch (Exception e){
            return ResponseEntity.ok(ResponseObject.builder().data(e.getMessage()).build());
        }
    }

    @GetMapping("/tdee")
    public ResponseEntity<ResponseObject> calculateTDEE(@Parameter(description = "weight in kg") double weight,
                                                        @Parameter(description = "height in cm") double height,
                                                        @Parameter(description = "age in year") double age,
                                                        @Parameter(description = "gender MALE or FEMALE") String gender,
                                                        @Parameter(description = "R = 1.2, 1.375, 1.55, 1.725, 1.9") double r) {
        try{
            EnergyCaculate energyCaculate = new EnergyCaculate();
            CaloriesReport report = energyCaculate.caculateEnergy(weight, height, age, gender, r);
            return ResponseEntity.ok(ResponseObject.builder().data(report).build());
        }catch (Exception e){
            return ResponseEntity.ok(ResponseObject.builder().data(e.getMessage()).build());
        }
    }
}
