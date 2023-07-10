package com.ecommerce.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.api.dao.CustomerAccountDao;
import com.ecommerce.api.entity.CustomerAccountDetails;

@Service
public class CustomerAccountService {

	@Autowired
	CustomerAccountDao customerAccountDao;

	public JSONObject customerAccountDetails(String phoneNumber, String email) {

		JSONObject jsonObject = new JSONObject();

		JSONObject jsonContact = new JSONObject();

		CustomerAccountDetails customerAccountDetails = new CustomerAccountDetails();

		CustomerAccountDetails primaryCustomer = null;

		List<String> emailAddlist = new ArrayList<>();
		List<String> phoneNumberlist = new ArrayList<>();

		try {

			// Check for phone number existence
			String availablePhoneNumber = customerAccountDao.customerAccountDetailsByPhone(phoneNumber, "primary");

			// Check for email address existence
			String availableEmailAddress = customerAccountDao.customerAccountDetailsByEmail(email, "primary");

			CustomerAccountDetails responseObject = new CustomerAccountDetails();

			if (availablePhoneNumber != null)
				primaryCustomer = customerAccountDao.customerObject(availablePhoneNumber, "primary");

			if (availableEmailAddress != null)
				primaryCustomer = customerAccountDao.customerObjectbyemail(availableEmailAddress, "primary");

			
			if (availablePhoneNumber == null && availableEmailAddress == null) {
				/* Create new user account Link Precedence as "Primary */

				LocalDateTime localDateTime = LocalDateTime.now();
				customerAccountDetails.setPhoneNumber(phoneNumber);
				customerAccountDetails.setEmailAddress(email);
				customerAccountDetails.setLinkPrecedence("primary"); // will be NULL since it is primary account
				customerAccountDetails.setCreatedAt(localDateTime.toString().replace("T", " "));
				customerAccountDetails.setUpdatedAt(localDateTime.toString().replace("T", " "));
				customerAccountDetails.setDeletedAt(null);

				// Save the data
				responseObject = customerAccountDao.save(customerAccountDetails);

			} else {
				String availableAccId = null;
				
				if (primaryCustomer.getId() > 0) {
					jsonObject.put("PrimaryContactId", primaryCustomer.getId());
					emailAddlist.add(primaryCustomer.getEmailAddress());
					phoneNumberlist.add(primaryCustomer.getPhoneNumber());

				} else {
					jsonObject.put("PrimaryContactId", null);
				}


				int linkedId = getLinkedId(availableEmailAddress, availablePhoneNumber);

				if (linkedId != 0) {
				
					LocalDateTime localDateTime = LocalDateTime.now();
					customerAccountDetails.setPhoneNumber(phoneNumber);
					customerAccountDetails.setEmailAddress(email);
					customerAccountDetails.setLinkedId(String.valueOf(linkedId));
					customerAccountDetails.setLinkPrecedence("secondary");
					customerAccountDetails.setCreatedAt(localDateTime.toString().replace("T", " "));
					customerAccountDetails.setUpdatedAt(localDateTime.toString().replace("T", " "));
					customerAccountDetails.setDeletedAt(null);
					// Save the data
					responseObject = customerAccountDao.save(customerAccountDetails);
				}
			}

			if (responseObject.getLinkPrecedence().equalsIgnoreCase("secondary")) {
				jsonObject.put("SecondaryIds", responseObject.getId());
				if (!emailAddlist.contains(responseObject.getEmailAddress()))
					emailAddlist.add(responseObject.getEmailAddress());
				if (!phoneNumberlist.contains(responseObject.getPhoneNumber()))
					phoneNumberlist.add(responseObject.getPhoneNumber());
			} else {
				jsonObject.put("SecondaryIds", null);
			}

			jsonObject.put("emails", emailAddlist);
			jsonObject.put("PhoneNumbers", phoneNumberlist);

			jsonContact.put("Contact", jsonObject);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonContact;

	}

	private int getLinkedId(String availableEmailAddress, String availablePhoneNumber) {
		if (availableEmailAddress != null || availablePhoneNumber != null) {

		

			if (availableEmailAddress != null) {
				return customerAccountDao.getLinkedIdByEmail(availableEmailAddress, "primary");
			} else if (availablePhoneNumber != null) {
				return customerAccountDao.getLinkedIdByPhone(availablePhoneNumber, "primary");
			}
		}

		return 0;
	}

}
