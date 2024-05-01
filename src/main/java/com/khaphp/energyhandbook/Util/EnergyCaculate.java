package com.khaphp.energyhandbook.Util;

import com.khaphp.energyhandbook.Constant.Gender;
import com.khaphp.energyhandbook.Dto.CaculateEnergy.CaloriesReport;
import com.khaphp.energyhandbook.Dto.CaculateEnergy.EnergyBMIReport;
import reactor.core.publisher.Flux;

import java.util.Set;

public class EnergyCaculate {
    /**
     * Caculate BMI
     * BMI is Body Mass Index (ch số mỡ trong cơ thể)
     * < 18.5 -> dưới chuẫn
     * 18.5-24.9 -> bình thường
     * 25.0-29.9 -> thừa cân
     * 30.0-34.9 -> béo phì cấp độ 1
     * 35.0-39.9 -> báo phì cấp độ 2
     * 40.0-lên -> báo phì cấp độ 3
     * @param weight: unit is kg
     * @param height: unit is m
     * @return
     */

    public static EnergyBMIReport caculateBMI(double weight, double height) {
        double BMI = weight / (height * height);
        EnergyBMIReport report = new EnergyBMIReport();
        report.setBmi(BMI);
        if (BMI < 18.5) {
            report.setStatus("Dưới chuẩn");
        } else if (BMI >= 18.5 && BMI <= 24.9) {
            report.setStatus("Bình thường");
        } else if (BMI >= 25.0 && BMI <= 29.9) {
            report.setStatus("Thừa cân");
        } else if (BMI >= 30.0 && BMI <= 34.9) {
            report.setStatus("Béo phì cấp độ I");
        } else if (BMI >= 35.0 && BMI <= 39.9) {
            report.setStatus("Béo phì cấp độ II");
        } else if (BMI >= 40.0) {
            report.setStatus("Béo phì cấp độ III");
        }
        return report;
    }

    /**
     * Caculate Energy by TDEE = BMR x R.
     * R là hệ số vận động của một người trong một ngày
     *          Vận động ít (chỉ làm việc văn phòng, ngủ và ăn): R = 1,2
     *          Vận động nhẹ (tập thể dục 1-3 lần/tuần): R = 1,375
     *          Vận động vừa (tập thể dục 3-5 lần/tuần, vận động mỗi ngày): R = 1,55
     *          Vận động nặng (tập thể dục 6-7 lần/tuần, hay chơi thể thao, vận động thường xuyên): R = 1,725
     *          Vận động rất nặng (tập thể dục 2 lần/ngày, người lao động phổ thông): R = 1,9.
     *
     * BMR: là công thức để tính tỷ lệ trao đổi chất cơ bản (Basal Metabolic Rate).
     *        Nam: BMR = (13,397 x weight) + (4,799 x height) – (5,677 x age) + 88,362.
     *        Nữ: BMR = (9,247 x weight) + (3,098 x height) – (4,330 x age) + 447,593
     *
     * TDEE là tổng năng lượng tiêu thụ mỗi ngày (Total Daily Energy Expenditure) được tính dựa trên chỉ số BMR và R
     *      TDEE = BMR x R
     *
     * @param weight: unit is kg
     * @param height: unit is cm
     * @param age: unit is year
     * @param gender: MALE or FEMALE
     * @param r: là chỉ số vận động
     * @return
     */
    public static CaloriesReport caculateEnergy(double weight, double height, double age, String gender, double r) {
        double bmr = 0;
        if (gender.equals(Gender.MALE.toString())) {
            bmr = (13.397 * weight) + (4.799 * height) - (5.677 * age) + 88.362;
        } else if (gender.equals(Gender.FEMALE.toString())) {
            bmr = (9.247 * weight) + (3.098 * height) - (4.330 * age) + 447.593;
        } else{
            throw new IllegalArgumentException("Gender must be MALE or FEMALE");
        }

        //check r
        Set<Double> rSet = Set.of(1.2, 1.375, 1.55, 1.725, 1.9);
        if(!rSet.contains(r)) {
            throw new IllegalArgumentException("R must be 1.2, 1.375, 1.55, 1.725, 1.9");
        }

        double tdee = bmr * r;

        CaloriesReport report = new CaloriesReport();
        report.setTdee(tdee);
        report.setBmr(bmr);
        report.setR(r + "");
        return report;
    }
}
