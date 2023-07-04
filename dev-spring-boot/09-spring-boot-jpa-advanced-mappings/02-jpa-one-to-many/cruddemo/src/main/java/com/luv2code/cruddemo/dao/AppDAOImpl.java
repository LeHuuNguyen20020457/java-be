package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Course;
import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class AppDAOImpl implements AppDAO{
    private EntityManager entityManager;

    @Autowired
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor theInstructor) {
        entityManager.persist(theInstructor);
    }

    @Override
    public Instructor findInstructorById(int theId) {
        return entityManager.find(Instructor.class, theId);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int theId) {
        Instructor theInstructor = entityManager.find(Instructor.class, theId);

        List<Course> courses = theInstructor.getCourses();

        for(Course course : courses) {
            course.setInstructor(null);
        }

        entityManager.remove(theInstructor);

    }

    @Override
    public InstructorDetail findInstructorDetailById(int theId) {
        InstructorDetail theInstructorDetail = entityManager.find(InstructorDetail.class, theId);
        return theInstructorDetail;
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int theId) {
        InstructorDetail instructorDetail = entityManager.find(InstructorDetail.class, theId);

        //xoá instructorDetail mà không xoá instructor
        //đoạn code này phá vỡ mối liên kết 2 chiều
        instructorDetail.getInstructor().setInstructorDetail(null);

        entityManager.remove(instructorDetail);
    }

    @Override
    public List<Course> findCourseByInstructorId(int theId) {
        TypedQuery<Course> theQuery = entityManager.createQuery("select c from Course c WHERE instructor.id = :data", Course.class);

        theQuery.setParameter("data", theId);
        List<Course> theCourses = theQuery.getResultList();

        return theCourses;
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int theId) {

        TypedQuery<Instructor> theQuery = entityManager.createQuery(
                "select i from Instructor i " +
                "JOIN FETCH i.courses " +
                "JOIN FETCH i.instructorDetail " +
                "WHERE i.id = :data", Instructor.class);
        theQuery.setParameter("data", theId);

        Instructor tempInstructor = theQuery.getSingleResult();

        return tempInstructor;

    }

    @Override
    @Transactional
    public void update(Instructor instructor) {
        entityManager.merge(instructor);
    }

    @Override
    @Transactional
    public void update(Course course) {
        entityManager.merge(course);
    }

    @Override
    public Course findCourseById(int theId) {
        Course course = entityManager.find(Course.class, theId);
        return course;
    }

    @Override
    @Transactional
    public void deleteCountById(int theId) {
        Course course = entityManager.find(Course.class, theId);
        entityManager.remove(course);
    }
}



