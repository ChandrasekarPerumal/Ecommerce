package com.ecommerce.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.api.entity.CustomerAccountDetails;

@EnableJpaRepositories
@Repository
public interface CustomerAccountDao extends JpaRepository<CustomerAccountDetails, Integer> {

	@Query("select t.phoneNumber from CustomerAccountDetails t  where t.phoneNumber =:n")
	String customerAccountDetailsByPhone(@Param("n") String phoneNumber);
	
	@Query("select t.email from CustomerAccountDetails t  where t.email =:n")
	String customerAccountDetailsByEmail(@Param("n") String email);
	
	@Query("select t.linkedId from CustomerAccountDetails t where t.email =:n or t.phoneNumber =:n")
	int getLinkedId(@Param("n") String availableAccId);
	
	
	

}
