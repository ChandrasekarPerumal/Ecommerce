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

	@Query("select t.phoneNumber from CustomerAccountDetails t  where t.phoneNumber =:n and t.linkPrecedence=:m")
	String customerAccountDetailsByPhone(@Param("n") String phoneNumber,@Param("m") String link);
	
	@Query("select t.email from CustomerAccountDetails t  where t.email =:n  and t.linkPrecedence=:m ")
	String customerAccountDetailsByEmail(@Param("n") String email,@Param("m") String link);
	
	@Query("select t.id from CustomerAccountDetails t where  t.phoneNumber =:n and t.linkPrecedence=:m ")
	Integer getLinkedIdByPhone(@Param("n") String availablePhoneNumber,@Param("m") String link);
	
	@Query("select t.id from CustomerAccountDetails t where  t.email =:n and t.linkPrecedence=:m ")
	Integer getLinkedIdByEmail(@Param("n") String availableEmailAddress,@Param("m") String link);
	
	@Query("select t.id from CustomerAccountDetails t where  t.phoneNumber =:n or t.email =:m")
	Integer getLinkedId(@Param("n") String availablePhoneNumber,@Param("m") String availableEmailAddress);
	
	
	@Query("select t from CustomerAccountDetails t  where t.phoneNumber =:n and t.linkPrecedence=:m")
	CustomerAccountDetails customerObject(@Param("n") String phoneNumber,@Param("m") String link);
	
	@Query("select t from CustomerAccountDetails t  where t.email =:n and t.linkPrecedence=:m")
	CustomerAccountDetails customerObjectbyemail(@Param("n") String email,@Param("m") String link);
	
	

}
