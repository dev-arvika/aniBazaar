package com.ani.bazaar.controller;



import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ani.bazaar.dto.UserRequestDto;
import com.ani.bazaar.dto.UserResponseDto;
import com.ani.bazaar.entity.UserEntity;
import com.ani.bazaar.exception.UserNotFoundException;
import com.ani.bazaar.repository.UserRepository;

import jakarta.validation.Valid;


@RestController
public class UserController {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/api/users")
	public List<UserEntity> retrieveAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/api/users/{id}")
	public ResponseEntity<UserResponseDto>  retriveUserById(@PathVariable long id) {
		UserEntity user = userRepository.findById(id);
		if (user == null)
			throw new UserNotFoundException("id: "+id);

		UserResponseDto userResponseDto= new UserResponseDto();
		modelMapper.map(user, userResponseDto);	
		userResponseDto.setSelectLang(user.getSelectLang().getLanguage());
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/user-image/")
                .path(user.getUserPhoto())
                .toUriString();
		userResponseDto.setUserPhoto(fileDownloadUri);
		return new ResponseEntity<>(userResponseDto,HttpStatus.OK);
	}

	@PutMapping("/api/users/{id}")
	public ResponseEntity<UserResponseDto> saveUser(@Valid @PathVariable long id, @RequestBody UserRequestDto userRequestDto) {
		UserEntity userDtls = userRepository.findById(id);
		if (userDtls == null)
			throw new UserNotFoundException("Id:"+id);
		
		modelMapper.map(userRequestDto, userDtls);
		userDtls.setModifiedAt(LocalDateTime.now());
		userRepository.save(userDtls);
		UserResponseDto userResponseDto = new UserResponseDto();
		modelMapper.map(userDtls, userResponseDto);
		return new ResponseEntity<>(userResponseDto, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/api/users/{id}")
	public void deleteUserById(@PathVariable long id) {
		userRepository.deleteById(id);
	}
}
