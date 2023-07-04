package com.luv2code.cruddemo;

import com.luv2code.cruddemo.dao.AppDAO;
import com.luv2code.cruddemo.entity.Course;
import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

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

//			deleteInstructorDetail(appDAO);

//			createInstructorWithCourses(appDAO);

//			findInstructorWithCourses(appDAO);

//			findCousesForInstructor(appDAO);

//			findInstructorWithCoursesJoinFetch(appDAO);

//			updateInstructor(appDAO);

//			updateCourse(appDAO);

			deleteCourseById(appDAO);
		};
	}

	private void deleteCourseById(AppDAO appDAO) {
		int theId = 10;
		appDAO.deleteCountById(theId);
	}

	private void updateCourse(AppDAO appDAO) {
		int theId = 10;

		Course tempCourse = appDAO.findCourseById(theId);

		tempCourse.setTitle("yêu em");

		appDAO.update(tempCourse);
	}

	private void updateInstructor(AppDAO appDAO) {
		int theId = 1;
		Instructor tempInstructor = appDAO.findInstructorById(theId);

		tempInstructor.setFirstName("Thao");

		appDAO.update(tempInstructor);

	}

	private void findInstructorWithCoursesJoinFetch(AppDAO appDAO) {
		int theId = 1;

		Instructor instructor = appDAO.findInstructorByIdJoinFetch(theId);

		System.out.println(instructor);
		System.out.println(instructor.getCourses());
		System.out.println(instructor.getInstructorDetail());

	}

	private void findCousesForInstructor(AppDAO appDAO) {
		int theId = 1;
		Instructor tempInstructor = appDAO.findInstructorById(theId);

		List<Course> courses = appDAO.findCourseByInstructorId(theId);

		tempInstructor.setCourses(courses);

		System.out.println(tempInstructor.getCourses());
	}

	private void findInstructorWithCourses(AppDAO appDAO) {

		int theId = 1;

		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println(tempInstructor);
		System.out.println(tempInstructor.getCourses());
		System.out.println("Done!");
	}


	private void createInstructorWithCourses(AppDAO appDAO) {
		Instructor tempInstructor = new Instructor("Susan", "public",  "susan@gmail.com");

		InstructorDetail temInstructorDetail = new InstructorDetail(
				"https://www.youtube.com",
				"susan vui ve"
		);
		tempInstructor.setInstructorDetail(temInstructorDetail);

		Course tempCourse1 = new Course("lt java backend");
		Course tempCourse2 = new Course("lt NodeJS backend");

		tempInstructor.add(tempCourse1);
		tempInstructor.add(tempCourse2);


		//save the instructor
		//
		// Note: this will ALSO save the courses
		// because of CaseCadeType.PERSIST
		//
		System.out.println(tempInstructor);
		System.out.println(tempInstructor.getCourses());

		appDAO.save(tempInstructor);

	}

	private void deleteInstructorDetail(AppDAO appDAO) {

		appDAO.deleteInstructorDetailById(3);
	}


	private void findInstructorDetail(AppDAO appDAO) {
		InstructorDetail  instructorDetail = appDAO.findInstructorDetailById(1);
		System.out.println(instructorDetail.getInstructor());
	}

	private void deleteInstructor(AppDAO appDAO) {
		appDAO.deleteInstructorById(1);
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



