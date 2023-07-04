package com.luv2code.springboot.cruddemo.service;

import com.luv2code.springboot.cruddemo.dao.EmploeeRepository;
import com.luv2code.springboot.cruddemo.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmploeeRepository emploeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmploeeRepository emploeeRepository) {
        this.emploeeRepository = emploeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return emploeeRepository.findAll();
    }

    @Override
    public Employee findById(int theId) {
        Optional<Employee> result = emploeeRepository.findById(theId);

        Employee theEmployee = null;
        if(result.isPresent()) {
            theEmployee = result.get();
        }
        else{
            throw new RuntimeException("Did not find employee id - " + theId);
        }
        return theEmployee;
    }

    @Override
    public Employee save(Employee employee) {
        return emploeeRepository.save(employee);
    }

    @Override
    public void deleteById(int theId) {
        emploeeRepository.deleteById(theId);
    }

}
