package com.example.demo.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.OrderRepository;

@Controller
@RequestMapping(value = "/order")
public class OrderController {
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@RequestMapping(value = "/")
	public String index(Model model,@RequestParam(name = "customer_id",required = false)Long customer_id) {
		Order order = new Order();
		Customer customer = null;
		if(customer_id != null) {
			customer = customerRepository.findById(customer_id).get();
			order.setCustomer(customer);
		}
		model.addAttribute("order", order);
		if(customer == null || customer_id == 0L) {
			model.addAttribute("orders", orderRepository.findAll());
		}else {
			model.addAttribute("orders", orderRepository.findByCustomer(customer));
		}
		
		model.addAttribute("customers", customerRepository.findAll());
		model.addAttribute("employees", employeeRepository.findAll());
		return "order";
	}
	
	@RequestMapping(value = "/create")
	public String create(Order order) {
		orderRepository.save(order);
		Customer customer = customerRepository.findById(order.getCustomer().getId()).get();
		return "redirect:/order/?customer_id=" + customer.getId();
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(Model model,@PathVariable(value = "id")Long id) {
		Order order = orderRepository.findById(id).get();
		model.addAttribute("order", order);
		model.addAttribute("customers", customerRepository.findAll());
		model.addAttribute("employees", employeeRepository.findAll());
		return "order-update";
	}
	
	@RequestMapping(value = "/update/{id}")
	public String update(Order order,@PathVariable(value = "id")Long id) {
		order.setId(id);
		orderRepository.save(order);
		return "redirect:/order/?customer_id=" + order.getCustomer().getId();
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable(value = "id")Long id) {
		Customer customer = orderRepository.findById(id).get().getCustomer();
		orderRepository.deleteById(id);
		return "redirect:/order/?customer_id=" + customer.getId();
	}
	
	//---------------------------------------------------------------------------
	
	@RequestMapping(value = "/{oid}/item")
	public String viewItem(Model model, @PathVariable("oid") Long oid) {
		Order order = orderRepository.findById(oid).get();
		model.addAttribute("order", order);
		return "orderItem";
	}
	
	
}
