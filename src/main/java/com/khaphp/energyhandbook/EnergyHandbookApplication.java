package com.khaphp.energyhandbook;

import com.khaphp.energyhandbook.Constant.EmailDefault;
import com.khaphp.energyhandbook.Constant.Gender;
import com.khaphp.energyhandbook.Constant.Role;
import com.khaphp.energyhandbook.Dto.FoodEncylopedia.FoodEncylopediaDTOcreate;
import com.khaphp.energyhandbook.Dto.Usersystem.UserSystemDTOcreate;
import com.khaphp.energyhandbook.Entity.FoodEncylopedia;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.Repository.FoodEncylopediaRepository;
import com.khaphp.energyhandbook.Repository.UserSystemRepository;
import com.khaphp.energyhandbook.Service.FoodEncylopediaService;
import com.khaphp.energyhandbook.Service.UserSystemService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@SpringBootApplication
@OpenAPIDefinition(
		info = @io.swagger.v3.oas.annotations.info.Info(
				title = "Energy Handbook",
				contact = @Contact(name = "Pham Huynh Phuong Kha", email = "khaphpdz@gmail.com"),
				version = "1.0.0",
				description = "Restful API for Energy Handbook"
		)
)
@SecurityScheme(
		name = "EnergyHandbook",
		in = SecuritySchemeIn.HEADER,
		type = SecuritySchemeType.HTTP,
		scheme = "Bearer",
		bearerFormat = "JWT"
)
public class EnergyHandbookApplication {
	@Autowired
	private UserSystemService userService;
	@Autowired
	private UserSystemRepository userRepository;

	@Autowired
	private FoodEncylopediaService foodEncylopediaService;
	@Autowired
	private FoodEncylopediaRepository foodEncylopediaRepository;

	public static void main(String[] args) {
		SpringApplication.run(EnergyHandbookApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public CommandLineRunner commandLineRunner(){
		return args -> {
			//default account
			UserSystem userSystem = userRepository.findByEmail(EmailDefault.CUSTOMER_EMAIL_DEFAULT);
			if(userSystem == null){
				userService.create(UserSystemDTOcreate.builder()
						.name("Default Customer")
						.email(EmailDefault.CUSTOMER_EMAIL_DEFAULT)
						.gender(Gender.MALE.toString())
						.password(UUID.randomUUID().toString())
						.username("customer")
						.build(), Role.CUSTOMER.toString());
			}

			userSystem = null;
			userSystem = userRepository.findByEmail(EmailDefault.EMPLOYEE_EMAIL_DEFAULT);
			if(userSystem == null){
				userService.create(UserSystemDTOcreate.builder()
						.name("Default Employee")
						.email(EmailDefault.EMPLOYEE_EMAIL_DEFAULT)
						.gender(Gender.MALE.toString())
						.password(UUID.randomUUID().toString())
						.username("employee")
						.build(), Role.EMPLOYEE.toString());
			}

			userSystem = null;
			userSystem = userRepository.findByEmail(EmailDefault.SHIPPER_EMAIL_DEFAULT);
			if(userSystem == null){
				userService.create(UserSystemDTOcreate.builder()
						.name("Default Shipper")
						.email(EmailDefault.SHIPPER_EMAIL_DEFAULT)
						.gender(Gender.MALE.toString())
						.password(UUID.randomUUID().toString())
						.username("shipper")
						.build(), Role.SHIPPER.toString());
			}

			userSystem = null;
			userSystem = userRepository.findByEmail(EmailDefault.ADMIN_EMAIL_DEFAULT);
			if(userSystem == null){
				userService.create(UserSystemDTOcreate.builder()
						.name("Admintrator")
						.email(EmailDefault.ADMIN_EMAIL_DEFAULT)
						.gender(Gender.MALE.toString())
						.password("11111")
						.username("admin")
						.build(), Role.ADMIN.toString());
			}

			//default food encylopedia
			UserSystem employee = userRepository.findByEmail(EmailDefault.EMPLOYEE_EMAIL_DEFAULT);
			FoodEncylopedia foodEncylopedia = foodEncylopediaRepository.findByName("...");
			if(foodEncylopedia == null){
				foodEncylopediaService.create(FoodEncylopediaDTOcreate.builder()
						.name("...")
						.calo(0)
						.unit("...")
						.employeeId(employee.getId())
						.build());
			}
		};
	}

}
