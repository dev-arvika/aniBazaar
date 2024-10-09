package com.ani.bazaar.service;

import com.ani.bazaar.dto.UserRequestDto;
import com.ani.bazaar.entity.UserEntity;

public interface UserService {

	UserEntity save(UserEntity userEntity);

	UserEntity getUserDtlsByPhone(long phone);

	UserEntity verifyByPhoneAndOtp(long phone, int otp);

	UserEntity updateUser(long userId, UserRequestDto userRequestDto);

}
