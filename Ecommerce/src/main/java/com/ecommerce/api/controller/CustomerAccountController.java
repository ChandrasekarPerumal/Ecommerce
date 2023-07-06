package com.ecommerce.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.api.service.CustomerAccountService;

@RestController
@RequestMapping("contact/api/v1")
public class CustomerAccountController {

	@GetMapping("/test")
	public String display() {
		return "Testing successful";
	}
	
	@Autowired
	private CustomerAccountService customerAccountService;

	
	@PostMapping("/identity")
	public void createCustomerAccount(@RequestParam(value = "phoneNumber") String phoneNumber,
			@RequestParam(value = "email") String emailAddress) {
		System.out.println(phoneNumber +" " +emailAddress);
		customerAccountService.customerAccountDetails(phoneNumber, emailAddress);
		

	}
}
