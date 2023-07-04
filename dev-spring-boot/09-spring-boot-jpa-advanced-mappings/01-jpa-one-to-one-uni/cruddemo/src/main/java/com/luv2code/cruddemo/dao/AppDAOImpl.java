package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.Instructor;
import com.luv2code.cruddemo.entity.InstructorDetail;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


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
}



