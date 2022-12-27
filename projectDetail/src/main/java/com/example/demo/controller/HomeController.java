package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;

@Controller
public class HomeController {
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
    private EmployeeRepository repo;
    @GetMapping("/")
    public String home() {
    	return "home";
    }
    @GetMapping("/login")
	public String login()
	{
		return "login";
	}
	
	@GetMapping("/signup")
	public String signup()
	{
		return "signup";
	}
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute Employee emp, Model m, HttpSession http) {
		emp.setPassword(encoder.encode(emp.getPassword()));
		emp.setRole("Role_Employee");
		Employee e = repo.save(emp);
		if(e!=null) {
			http.setAttribute("msg", "Registration Successfully");
		}else {
			http.setAttribute("msg", "Something wrong on server");
		}
		return "redirect:/signup";
	}
}
