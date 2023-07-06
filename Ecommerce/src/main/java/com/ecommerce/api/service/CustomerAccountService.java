package com.ecommerce.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.api.dao.CustomerAccountDao;
import com.ecommerce.api.entity.CustomerAccountDetails;

@Service
public class CustomerAccountService {

	@Autowired
	CustomerAccountDao customerAccountDao;

	public ResponseEntity<CustomerAccountDetails> customerAccountDetails(String phoneNumber, String email) {

		try {

			CustomerAccountDetails customerAccountDetails = new CustomerAccountDetails();

			// Check for phone number existence
			String availablePhoneNumber = customerAccountDao.customerAccountDetailsByPhone(phoneNumber);

			// Check for email address existence
			String availableEmailAddress = customerAccountDao.customerAccountDetailsByEmail(email);

			if (availablePhoneNumber == null && availableEmailAddress == null) {
				/* Create new user account Link Precedence as "Primary */

				LocalDateTime localDateTime = LocalDateTime.now();
				customerAccountDetails.setPhoneNumber(phoneNumber);
				customerAccountDetails.setEmailAddress(email);
				customerAccountDetails.setLinkPrecedence("primary"); // LinkedId will be NULL since it is primary
																		// account
				customerAccountDetails.setCreatedAt(localDateTime.toString().replace("T", " "));
				customerAccountDetails.setUpdatedAt(localDateTime.toString().replace("T", " "));
				customerAccountDetails.setDeletedAt(null);
				// Save the data
				customerAccountDao.save(customerAccountDetails);
			} else {
				String availableAccId = null;

				if (availableEmailAddress != null) {
					availableAccId = availableEmailAddress;
				} else {
					if (availablePhoneNumber != null) {
						availableAccId = availablePhoneNumber;
					}
				}

				System.out.println("availableAccId :"+availableAccId);
				
				int linkedId = customerAccountDao.getLinkedId(availableAccId);
				LocalDateTime localDateTime = LocalDateTime.now();
				customerAccountDetails.setPhoneNumber(phoneNumber);
				customerAccountDetails.setEmailAddress(email);

				customerAccountDetails.setLinkPrecedence("secondary");
				customerAccountDetails.setCreatedAt(localDateTime.toString().replace("T", " "));
				customerAccountDetails.setUpdatedAt(localDateTime.toString().replace("T", " "));
				customerAccountDetails.setDeletedAt(null);
				// Save the data
				customerAccountDao.save(customerAccountDetails);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
}
