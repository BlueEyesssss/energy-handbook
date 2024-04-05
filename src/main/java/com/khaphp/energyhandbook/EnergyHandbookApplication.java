package com.khaphp.energyhandbook;

import com.khaphp.energyhandbook.entity.UserSystem;
import com.khaphp.energyhandbook.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EnergyHandbookApplication {
	@Autowired
	private CustomerRepository customerRepository;

	public static void main(String[] args) {
		SpringApplication.run(EnergyHandbookApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(){
//		return args -> {
//			UserSystem customer = new UserSystem();
//			customer.setName("Nguyen Van A");
//			customerRepository.save(customer);
//			UserSystem customerb = new UserSystem();
//			customerb.setName("Nguyen Van BBBBBBB");
//			customerRepository.save(customerb);
//		};
//	}

}
