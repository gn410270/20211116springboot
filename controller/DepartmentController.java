package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;

@Controller
@RequestMapping(value = "/department")
public class DepartmentController {
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@RequestMapping(value = {"/","index"})
	public String index(Model model) {
		Department department = new Department();
		model.addAttribute("department", department);
		model.addAttribute("departments", departmentRepository.findAll());
		return "department";
	}
	
	@RequestMapping(value = "/create")
	public String create(Department department) {
		departmentRepository.save(department);
		return "redirect:/department/";
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable(value = "id")Long id,Model model) {
		Department department = departmentRepository.findById(id).get();
		model.addAttribute("department", department);
		model.addAttribute("departments", departmentRepository.findAll());
		return "department-update";
	}
	
	@RequestMapping(value = "/update/{id}")
	public String update(@PathVariable(value = "id")Long id,Department department) {
		department.setId(id);
		departmentRepository.save(department);
		return "redirect:/department/";
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable(value = "id")Long id) {
		departmentRepository.deleteById(id);
		return "redirect:/department/";
	}
	
}
