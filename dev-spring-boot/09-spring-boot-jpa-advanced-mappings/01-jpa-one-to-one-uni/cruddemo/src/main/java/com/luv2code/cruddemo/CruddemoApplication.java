package com.luv2code.cruddemo;

import com.luv2code.cruddemo.dao.AppDAO;
import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO) {
		return runner -> {
//			createInstructor(appDAO);

//			findInstructor(appDAO);

//			deleteInstructor(appDAO);

//			findInstructorDetail(appDAO);

			deleteInstructorDetail(appDAO);
		};
	}

	private void deleteInstructorDetail(AppDAO appDAO) {

		appDAO.deleteInstructorDetailById(3);
	}


	private void findInstructorDetail(AppDAO appDAO) {
		InstructorDetail  instructorDetail = appDAO.findInstructorDetailById(1);
		System.out.println(instructorDetail.getInstructor());
	}

	private void deleteInstructor(AppDAO appDAO) {
		appDAO.deleteInstructorById(2);
	}

	private void findInstructor(AppDAO appDAO) {
		Instructor tempInstructor = appDAO.findInstructorById(1);
		System.out.println(tempInstructor);
		System.out.println(tempInstructor.getInstructorDetail());
	}

	private void createInstructor(AppDAO appDAO) {

		// create the instructor
		Instructor tempInstructor = new Instructor("Chad", "Darby",  "dardy@gmail.com");

		InstructorDetail temInstructorDetail = new InstructorDetail(
				"https://www.youtube.com/watch?v=UJKsgoZL8W4&list=RDUJKsgoZL8W4&start_radio=1",
				"vui ve"
		);
		tempInstructor.setInstructorDetail(temInstructorDetail);

		System.out.println("Saving instructor: " + tempInstructor);

		appDAO.save(tempInstructor);

	}
}



