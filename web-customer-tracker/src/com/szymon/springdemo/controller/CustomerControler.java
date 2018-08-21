package com.szymon.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.szymon.springdemo.entity.Customer;
import com.szymon.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerControler {
	
	@Autowired
	private CustomerService customerService;

	@GetMapping("/list")
	public String listCustomers(Model theModel) {
		List<Customer> customers = customerService.getCustomers();
		
		theModel.addAttribute("customers", customers);
		
		return "list-customers";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {	
		Customer theCustomer = new Customer();
		theModel.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}

	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
		customerService.saveCustomer(theCustomer);
		
		return "redirect:/customer/list";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int customerId,
									Model theModel) {

		Customer theCustomer = customerService.getCustomer(customerId);
		theModel.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}

	@GetMapping("/deleteCustomer")
	public String deleteCustomer(@RequestParam("customerId") int customerId) {

		customerService.deleteCustomer(customerId);
		//Customer theCustomer = customerService.getCustomer(customerId);
		//System.out.println(theCustomer);
		
		return "redirect:/customer/list";
	}

	@PostMapping("/search")
	public String search(@RequestParam("theSearchName") String theSearchName
			, Model theModel) {
		
		List<Customer> customers = customerService.searchCustomersByName(theSearchName);		
		theModel.addAttribute("customers", customers);
		
		return "list-customers";
	}
}


