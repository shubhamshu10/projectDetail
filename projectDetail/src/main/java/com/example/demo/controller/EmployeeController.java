package com.example.demo.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.example.demo.entity.Employee;
import com.example.demo.entity.Project;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ProjectRepository;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository empRepo;
	@Autowired
	private ProjectRepository projectRepo;
	@ModelAttribute
	 public void addCommonData(Principal p, Model m) {
		 String email=p.getName();
		 Employee employee= empRepo.findByEmail(email);
		 m.addAttribute("employee",employee);
	 }
	 @GetMapping("/add project")
		public String home() {
			return "employee/add project";
		}
	 @GetMapping("/viewProject/{page}")
		public String viewProject(@PathVariable int page, Model m, Principal p) {

			String email = p.getName();
			Employee emp = empRepo.findByEmail(email);

			Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
			Page<Project> project = projectRepo.findProjectByEmployee(emp.getId(), pageable);

			m.addAttribute("pageNo", page);
			m.addAttribute("totalPage", project.getTotalPages());
			m.addAttribute("project", project);
			m.addAttribute("totalElement", project.getTotalElements());

			return "employee/viewProject";
		}
	 @GetMapping("/editProject/{id}")
		public String editProject(@PathVariable int id, Model m) {

			Optional<Project> n = projectRepo.findById(id);
			if (n != null) {
				Project project = n.get();
				m.addAttribute("project", project);
			}

			return "employee/editProject";
		}
	 @PostMapping("/updateProject")
		public String updateProject(@ModelAttribute Project project, HttpSession session, Principal p) {
		 String email = p.getName();
			Employee u = empRepo.findByEmail(email);
			project.setEmployee(u);

			Project updateProject = projectRepo.save(project);

			if (updateProject != null) {
				session.setAttribute("msg", "Project Update Sucessfully");
			} else {
				session.setAttribute("msg", "Something wrong on server");
			}

			//System.out.println(project);

			return "redirect:/employee/viewProject/0";
		}

		@GetMapping("/deleteProject/{id}")
		public String deleteProject(@PathVariable int id,HttpSession session) {
			
			Optional<Project> notes=projectRepo.findById(id);
			if(notes!=null)
			{
				projectRepo.delete(notes.get());
				session.setAttribute("msg", "Project Delete Successfully");
			}
			
			return "redirect:/employee/viewProject/0";
		}

		@GetMapping("/viewProfile")
		public String viewProfile() {
			return "employee/viewProfile";
		}

		@PostMapping("/saveProject")
		public String saveProject(@ModelAttribute Project project, HttpSession session, Principal p) {
			String email = p.getName();
			Employee u = empRepo.findByEmail(email);
			project.setEmployee(u);

			Project n = projectRepo.save(project);

			if (n != null) {
				session.setAttribute("msg", "Project Added Sucessfully");
			} else {
				session.setAttribute("msg", "Something wrong on server");
			}

			return "redirect:/employee/add project";
		}
		
		@PostMapping("/updateEmployee")
		public String updateEmployee(@ModelAttribute Employee employee,HttpSession session,Model m)
		{
			Optional<Employee> Olduser=empRepo.findById(employee.getId());
			
			if(Olduser!=null)
			{
				employee.setPassword(Olduser.get().getPassword());
				employee.setRole(Olduser.get().getRole());
				employee.setEmail(Olduser.get().getEmail());
				
				Employee updateEmployee=empRepo.save(employee);
				if(updateEmployee!=null)
				{
					m.addAttribute("employee",updateEmployee);
					session.setAttribute("msg", "Profile Update Sucessfully..");
				}
				
			}
			
			
			return "redirect:/employee/viewProfile";
		}
}
