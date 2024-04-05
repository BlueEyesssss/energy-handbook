package com.khaphp.energyhandbook;

import com.khaphp.energyhandbook.Constant.Role;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Dto.usersystem.UserSystemDTOcreate;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.Repository.UserSystemRepository;
import com.khaphp.energyhandbook.Service.UserSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EnergyHandbookApplication {
	@Autowired
	private UserSystemService userService;
	@Autowired
	private UserSystemRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(EnergyHandbookApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(){
		return args -> {
			String emailDefaultCustomer = "customer@energy.handbook.com";
			String emailDefaultEmployee = "employee@energy.handbook.com";
			String emailDefaultShipper = "shipper@energy.handbook.com";

			UserSystem userSystem = userRepository.findByEmail(emailDefaultCustomer);
			if(userSystem == null){
				userService.create(UserSystemDTOcreate.builder()
						.name("Default Customer")
						.email(emailDefaultCustomer)
						.build(), Role.CUSTOMER.toString());
			}

			userSystem = null;
			userSystem = userRepository.findByEmail(emailDefaultEmployee);
			if(userSystem == null){
				userService.create(UserSystemDTOcreate.builder()
						.name("Default Employee")
						.email(emailDefaultEmployee)
						.build(), Role.EMPLOYEE.toString());
			}

			userSystem = null;
			userSystem = userRepository.findByEmail(emailDefaultShipper);
			if(userSystem == null){
				userService.create(UserSystemDTOcreate.builder()
						.name("Default Shipper")
						.email(emailDefaultShipper)
						.build(), Role.SHIPPER.toString());
			}
		};
	}

}
