package com.ani.bazaar.service;

import com.ani.bazaar.entity.UserEntity;

public interface UserService {

	UserEntity save(UserEntity userEntity);

	UserEntity getUserDtlsByPhone(long phone);

	UserEntity verifyByPhoneAndOtp(long phone, int otp);

}
