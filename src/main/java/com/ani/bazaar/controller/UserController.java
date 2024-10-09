package com.ani.bazaar.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ani.bazaar.dto.UserRequestDto;
import com.ani.bazaar.dto.UserResponseDto;
import com.ani.bazaar.entity.UserEntity;
import com.ani.bazaar.exception.UserNotFoundException;
import com.ani.bazaar.repository.UserRepository;
import com.ani.bazaar.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

	@Value("${default.user.image}")
	private String defaultUserImage;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
	@GetMapping("/api/users")
	public List<UserEntity> retrieveAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/api/users/{id}")
	public ResponseEntity<UserResponseDto> retriveUserById(@PathVariable long id) {
		UserEntity user = userRepository.findById(id);
		if (user == null)
			throw new UserNotFoundException("id: " + id);

		UserResponseDto userResponseDto = new UserResponseDto();
		modelMapper.map(user, userResponseDto);
		userResponseDto.setDob(user.getDob().format(formatter));
		userResponseDto.setSelectLang(user.getSelectLang().getLanguage());
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user-image/")
				.path(user.getUserImage() != null ? user.getUserImage() : defaultUserImage).toUriString();
		userResponseDto.setUserImage(fileDownloadUri);
		return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
	}

	@PatchMapping("/api/users/{id}")
	public ResponseEntity<UserResponseDto> updateUser(@Valid @PathVariable long id,
			@RequestBody UserRequestDto userRequestDto) {
		
		UserEntity updatedUser = userService.updateUser(id, userRequestDto);
		UserResponseDto userResponseDto = new UserResponseDto();
		modelMapper.map(updatedUser, userResponseDto);
		userResponseDto.setSelectLang(updatedUser.getSelectLang().getLanguage());
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user-image/")
				.path(updatedUser.getUserImage() != null ? updatedUser.getUserImage() : defaultUserImage).toUriString();
		userResponseDto.setUserImage(fileDownloadUri);
		return new ResponseEntity<>(userResponseDto, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/api/users/{id}")
	public void deleteUserById(@PathVariable long id) {
		userRepository.deleteById(id);
	}
}
