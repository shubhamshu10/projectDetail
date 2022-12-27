package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Employee;


public interface EmployeeRepository extends JpaRepository<Employee,Integer>{
      public Employee findByEmail(String email);
}
