package com.luv2code.cruddemo;

import com.luv2code.cruddemo.dao.StudentDAO;
import com.luv2code.cruddemo.entity.Student;
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
	public CommandLineRunner commandLineRunner(StudentDAO studentDAO) {
		return runner -> {
//			createStudent(studentDAO);
			createMultipleStudents(studentDAO);
//			readStudent(studentDAO);
//			queryForStudents(studentDAO);
//			queryForStudentsByLastName(studentDAO);

//			updateStudent(studentDAO);

//			deleteStudent(studentDAO);

//			deleteAllStudent(studentDAO);
		};

	}

	private void deleteAllStudent(StudentDAO studentDAO) {
		int numberRowsDelete = studentDAO.deleteAll();
		System.out.println(numberRowsDelete);
	}

	private void deleteStudent(StudentDAO studentDAO) {
		studentDAO.delete(5);
	}

	private void updateStudent(StudentDAO studentDAO) {
		int studentId = 1;
		Student myStudent = studentDAO.findById(1);

		myStudent.setLast_name("thao");

		studentDAO.update(myStudent);

	}

	private void queryForStudentsByLastName(StudentDAO studentDAO) {
		List<Student> theStudents = studentDAO.findByLastName("nguyen");
		for(Student student : theStudents) {
			System.out.println(student);
		}
	}

	private void queryForStudents(StudentDAO studentDAO) {
		List<Student> theStudents = studentDAO.findAll();

		for(Student student : theStudents){
			System.out.println(student);
		}

	}

	private void readStudent(StudentDAO studentDAO) {
		Student student = new Student("HAHA", "NANA", "NANA@gmail.com");

		studentDAO.save(student);

		Student myStudent = studentDAO.findById(student.getId());

		System.out.println(myStudent);
	}

	private void createMultipleStudents(StudentDAO studentDAO) {
		System.out.println("Creating 3 new student object ...");
		Student tempStudent1 = new Student("le", "nguyen", "nguyen@gmail.com");
		Student tempStudent2= new Student("nguyen", "an", "an@gmail.com");
		Student tempStudent3 = new Student("le", "nam", "nam@gmail.com");

		//save student object
		System.out.println("Saving the student ...");
		studentDAO.save(tempStudent1);
		studentDAO.save(tempStudent2);
		studentDAO.save(tempStudent3);
	}

	private void createStudent(StudentDAO studentDAO) {

		//create the student Object
		System.out.println("Creating new student object ...");
		Student tempStudent = new Student("le", "nguyen", "nguyen@gmail.com");

		//save student object
		System.out.println("Saving the student ...");
		studentDAO.save(tempStudent);

		//display id of the saved student
		System.out.println("Saved student. Generated id: " + tempStudent.getId());

	}


}