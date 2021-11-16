package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;
	
	
	@RequestMapping(value = "/")
	public String index(Model model) {
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		model.addAttribute("customers", customerRepository.findAll());
		return "customer";
	}
	
	@RequestMapping(value = "/create")
	public String create(Customer customer) {
		customerRepository.save(customer);
		return "redirect:/customer/";
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable(value = "id")Long id,Model model) {
		Customer customer = customerRepository.findById(id).get();
		model.addAttribute("customer", customer);
		model.addAttribute("customers", customerRepository.findAll());
		return "customer-update";
	}
	
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable(value = "id")Long id) {
		customerRepository.deleteById(id);
		return "redirect:/customer/";
	}
	
	
}
