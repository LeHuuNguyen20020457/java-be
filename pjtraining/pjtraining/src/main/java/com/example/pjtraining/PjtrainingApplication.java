package com.example.pjtraining;

import com.example.pjtraining.apacheVelocity.ReadExcel;
import com.example.pjtraining.apacheVelocity.VelocityWriter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class PjtrainingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PjtrainingApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {

		ReadExcel readExcel = new ReadExcel();
		VelocityWriter velocityWriter = new VelocityWriter(readExcel);
		return runner -> {
			velocityWriter.velocityReadFileGenEntity();
		};
	}

}
