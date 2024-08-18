package com.ani.bazaar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ani.bazaar.entity.UserEntity;
import com.ani.bazaar.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserEntity save(UserEntity userEntity) {
		return userRepository.save(userEntity);
	}

	@Override
	public UserEntity getUserDtlsByPhone(long phone) {
		return userRepository.getUserEntityByPhone(phone);
	}

	@Override
	public UserEntity verifyByPhoneAndOtp(long phone, int otp) {
		return userRepository.getUserEntityByPhoneAndOtp(phone, otp);
	}

}
