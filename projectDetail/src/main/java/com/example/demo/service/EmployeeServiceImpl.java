package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
@Service
public class EmployeeServiceImpl implements UserDetailsService{
	public EmployeeServiceImpl() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Autowired
	private EmployeeRepository repository;

	public EmployeeServiceImpl(EmployeeRepository repository) {
		super();
		this.repository = repository;
	}
/*	public List<Employee> getAllEmployee(){
		return repository.findAll();
	}

	public Employee saveEmployee(Employee emp) {
		return repository.save(emp);
	}

	public Employee getEmployeeById(int id) {
		return repository.findById(id).get();
	}

	public Employee updateEmployee(Employee employee) {
		return repository.save(employee);
	}

	public void deleteEmployee(int id) {
		repository.deleteById(id);
	}*/
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee emp = repository.findByEmail(username);
		if(emp==null) {
			throw new UsernameNotFoundException("employee not exist");
		}
		else {
			CustomDetails custom = new CustomDetails(emp);
			return custom;
		}
	}	
}
