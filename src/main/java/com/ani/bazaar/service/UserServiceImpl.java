package com.ani.bazaar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ani.bazaar.dto.UserRequestDto;
import com.ani.bazaar.entity.LanguageEntity;
import com.ani.bazaar.entity.UserEntity;
import com.ani.bazaar.exception.UserNotFoundException;
import com.ani.bazaar.repository.LanguageRepository;
import com.ani.bazaar.repository.UserRepository;
import com.ani.bazaar.utils.DateUtils;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	LanguageRepository languageRepository;

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

	@Override
	public UserEntity updateUser(long userId, UserRequestDto userRequestDto) {
		Optional<UserEntity> optionalUser = Optional.of(userRepository.findById(userId));
		UserEntity existingUser = optionalUser.orElseThrow(() -> new UserNotFoundException("Id:" + userId));
		try {
			// Check and update fields if they are different
			if (userRequestDto.getUserName() != null && !userRequestDto.getUserName().isBlank()
					&& !userRequestDto.getUserName().equals(existingUser.getUserName())) {
				existingUser.setUserName(userRequestDto.getUserName());
			}
			if (userRequestDto.getEmail() != null && !userRequestDto.getEmail().isBlank()
					&& !userRequestDto.getEmail().equals(existingUser.getEmail())) {
				existingUser.setEmail(userRequestDto.getEmail());
			}
			if (userRequestDto.getPhone() != null && !userRequestDto.getPhone().equals(existingUser.getPhone())) {
				existingUser.setPhone(userRequestDto.getPhone());
			}
			if (userRequestDto.getWaPhone() != null && !userRequestDto.getWaPhone().equals(existingUser.getWaPhone())) {
				existingUser.setWaPhone(userRequestDto.getWaPhone());
			}
			if (userRequestDto.getWork() != null && !userRequestDto.getWork().isBlank()
					&& !userRequestDto.getWork().equals(existingUser.getWork())) {
				existingUser.setWork(userRequestDto.getWork());
			}
			if (userRequestDto.getDob() != null && !userRequestDto.getDob().isBlank()
					&& !userRequestDto.getDob().equals(DateUtils.dateToString(existingUser.getDob()))) {
				existingUser.setDob(DateUtils.stringToDate(userRequestDto.getDob()));
			}
			if (userRequestDto.getSelectLang() != null && !userRequestDto.getSelectLang().isBlank()
					&& !userRequestDto.getSelectLang().equals(existingUser.getSelectLang().getLanguage())) {
				LanguageEntity languageEntity = languageRepository.findByLanguage(userRequestDto.getSelectLang());
				existingUser.setSelectLang(languageEntity);
			}
			if (userRequestDto.getDeviceToken() != null
					&& !userRequestDto.getDeviceToken().equals(existingUser.getDeviceToken())) {
				existingUser.setDeviceToken(userRequestDto.getDeviceToken());
			}

			return userRepository.save(existingUser);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getMessage());
		}
	}

}
