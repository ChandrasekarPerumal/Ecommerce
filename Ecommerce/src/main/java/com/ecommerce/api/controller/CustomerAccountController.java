package com.ecommerce.api.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity createCustomerAccount(@RequestParam(value = "phoneNumber") String phoneNumber,
			@RequestParam(value = "email") String emailAddress) {

		JSONObject jsonResponse = new JSONObject();

		try {
			jsonResponse = customerAccountService.customerAccountDetails(phoneNumber, emailAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!jsonResponse.isEmpty()) {
			return ResponseEntity.ok(jsonResponse);
		}

		return ResponseEntity.badRequest().body("Something went wrong - Please check the information");

	}
}
