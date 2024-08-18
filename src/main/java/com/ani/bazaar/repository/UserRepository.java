package com.ani.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ani.bazaar.entity.UserEntity;

import jakarta.transaction.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	UserEntity getUserEntityByPhone(long phone);

	UserEntity getUserEntityByPhoneAndOtp(long phone, int otp);

}
