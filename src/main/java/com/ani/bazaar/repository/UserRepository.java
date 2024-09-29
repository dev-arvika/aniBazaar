package com.ani.bazaar.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ani.bazaar.entity.UserEntity;

import jakarta.transaction.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity getUserEntityByPhone(long phone);

	@EntityGraph(attributePaths = "addressEntity")
	UserEntity getUserEntityByPhoneAndOtp(long phone, int otp);
	
	@EntityGraph(attributePaths = "addressEntity")
	UserEntity findById(long id);

}
