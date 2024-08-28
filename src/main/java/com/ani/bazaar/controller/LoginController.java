package com.ani.bazaar.controller;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ani.bazaar.dto.LoginRequestDto;
import com.ani.bazaar.dto.LoginResponseDto;
import com.ani.bazaar.dto.VerifyRequestDto;
import com.ani.bazaar.dto.VerifyResponseDto;
import com.ani.bazaar.entity.UserEntity;
import com.ani.bazaar.service.UserService;

import jakarta.validation.Valid;

@RestController
public class LoginController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	UserService userService;

	@PostMapping("/api/login")
	public ResponseEntity<LoginResponseDto> saveUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
		UserEntity savedUser = null;
		UserEntity userDtls = null;
		userDtls = userService.getUserDtlsByPhone(loginRequestDto.getPhone());
		if (userDtls != null) {
			userDtls.setOtp(1234);
			userDtls.setModifiedAt(LocalDateTime.now());
			savedUser = userService.save(userDtls);
		} else {
			UserEntity userEntity = modelMapper.map(loginRequestDto, UserEntity.class);
			userEntity.setOtp(1234);
			userEntity.setCreatedAt(LocalDateTime.now());
			savedUser = userService.save(userEntity);
		}
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setMessage("OTP Generated");
		return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
	}

	@PostMapping("/api/user/verify")
	public ResponseEntity<VerifyResponseDto> verifyUser(@Valid @RequestBody VerifyRequestDto verifyRequestDto) {
		UserEntity userDtls = userService.verifyByPhoneAndOtp(verifyRequestDto.getPhone(), verifyRequestDto.getOtp());
		VerifyResponseDto verifyResponseDto = new VerifyResponseDto();
		if (userDtls != null) {
			verifyResponseDto.setUserId(userDtls.getId());
			verifyResponseDto.setName(userDtls.getUserName());
			verifyResponseDto.setAccessToken("accessToken");
			verifyResponseDto.setRefreshToken("refreshToken");
			verifyResponseDto.setSlug("ram-0xkihcsi4e");
			return new ResponseEntity<>(verifyResponseDto, HttpStatus.OK);
		} else {
			verifyResponseDto.setError("Unauthorized");
			return new ResponseEntity<>(verifyResponseDto, HttpStatus.UNAUTHORIZED);
		}

	}

}
