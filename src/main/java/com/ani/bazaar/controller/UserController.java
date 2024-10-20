package com.ani.bazaar.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ani.bazaar.dto.LanguageResponseDto;
import com.ani.bazaar.dto.UserRequestDto;
import com.ani.bazaar.dto.UserResponseDto;
import com.ani.bazaar.entity.LanguageEntity;
import com.ani.bazaar.entity.UserEntity;
import com.ani.bazaar.exception.UserNotFoundException;
import com.ani.bazaar.repository.LanguageRepository;
import com.ani.bazaar.repository.UserRepository;
import com.ani.bazaar.service.UserService;
import com.ani.bazaar.utils.DateUtils;
import com.ani.bazaar.utils.FileUploadUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LanguageRepository languageRepository;

	@GetMapping("/users")
	public List<UserEntity> retrieveAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<UserResponseDto> retriveUserById(@PathVariable long id) {
		UserEntity user = userRepository.findById(id);
		if (user == null)
			throw new UserNotFoundException("id: " + id);

		UserResponseDto userResponseDto = toDTO(user);
		userResponseDto.setUserImage(FileUploadUtils.getImageDownloadUri(user.getUserImage(), "/api/user-image/"));
		return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
	}

	@PatchMapping("/users/{id}")
	public ResponseEntity<UserResponseDto> updateUser(@Valid @PathVariable long id,
			@RequestBody UserRequestDto userRequestDto) {
		UserEntity updatedUser = userService.updateUser(id, userRequestDto);

		UserResponseDto userResponseDto = toDTO(updatedUser);
		userResponseDto
				.setUserImage(FileUploadUtils.getImageDownloadUri(updatedUser.getUserImage(), "/api/user-image/"));
		return new ResponseEntity<>(userResponseDto, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable long id) {
		userRepository.deleteById(id);
	}

	@GetMapping("/users/languages")
	public List<LanguageResponseDto> retrieveAllLanguages() {

		List<LanguageEntity> languageEntities = languageRepository.findAll();
		List<LanguageResponseDto> dtos = languageEntities.stream()
			    .map(entity -> modelMapper.map(entity, LanguageResponseDto.class))
			    .toList();
		return dtos;
	}

	public UserResponseDto toDTO(UserEntity user) {
		UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);
		userResponseDto.setEmail(user.getEmail() != null ? user.getEmail() : "");
		userResponseDto.setWaPhone(user.getWaPhone() != null ? user.getWaPhone() : 0);
		userResponseDto.setWork(user.getWork() != null ? user.getWork() : "");
		userResponseDto.setUserName(user.getUserName() != null ? user.getUserName() : "");
		userResponseDto.setDob(DateUtils.dateToString(user.getDob()));
		userResponseDto.setSelectLang(user.getSelectLang().getLanguage());
		userResponseDto.setDeviceToken(user.getDeviceToken() != null ? user.getDeviceToken() : "");
		return userResponseDto;
	}
}
