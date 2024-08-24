package com.ani.bazaar.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ani.bazaar.dto.AddressRequestDto;
import com.ani.bazaar.dto.AddressResponseDto;
import com.ani.bazaar.entity.AddressEntity;
import com.ani.bazaar.entity.UserEntity;
import com.ani.bazaar.exception.AddressAlreadyExistException;
import com.ani.bazaar.exception.AddressNotFoundException;
import com.ani.bazaar.exception.UserNotFoundException;
import com.ani.bazaar.repository.AddressRepository;
import com.ani.bazaar.repository.UserRepository;
import com.ani.bazaar.service.AddressService;

import jakarta.validation.Valid;


@RestController
public class AddressController {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private AddressService addressService;
	
	@GetMapping("/api/addresses")
	public List<AddressEntity> retrieveAllAddresses() {
		return addressRepository.findAll();
	}

	@GetMapping("api/users/{userid}/address/{id}")
	public EntityModel<AddressEntity>  retriveAddressById(@PathVariable long userid, @PathVariable long id) {
		AddressEntity address = addressRepository.findById(id);
		if (address == null)
			throw new AddressNotFoundException("Id: "+id);

		EntityModel<AddressEntity> entityModel = EntityModel.of(address);
		
		WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrieveAllAddresses());
		entityModel.add(link.withRel("all-addresses"));
		return entityModel;
	}
	
	@PostMapping("api/users/{userid}/address")
	public ResponseEntity<AddressResponseDto> saveAddress(@Valid @PathVariable long userid, @RequestBody AddressRequestDto addressRequestDto) {
		UserEntity userDtls = userRepository.findById(userid);
		if (userDtls == null)
			throw new UserNotFoundException("Id:"+userid);
		try {
		AddressEntity addressEntity = modelMapper.map(addressRequestDto, AddressEntity.class);
		AddressEntity savedAddress = null;
		addressEntity.setUserEntity(userDtls);
		addressEntity.setCreatedAt(LocalDateTime.now());
		savedAddress = addressService.save(addressEntity);
		
		AddressResponseDto addressResponseDto = new AddressResponseDto();
		modelMapper.map(savedAddress, addressResponseDto);
		return new ResponseEntity<>(addressResponseDto, HttpStatus.CREATED);
		}catch(Exception ex) {
			throw new AddressAlreadyExistException("userId:"+userid);
		}
	}

	@PutMapping("/api/users/{userid}/address/{id}")
	public ResponseEntity<AddressResponseDto> updateAddress(@Valid @PathVariable long userid, @PathVariable long id, @RequestBody AddressRequestDto addressRequestDto) {
		UserEntity userDtls = userRepository.findById(userid);
		if (userDtls == null)
			throw new UserNotFoundException("Id:"+userid);
		
		AddressEntity address = addressRepository.findById(id);
		if (address == null)
			throw new AddressNotFoundException("Id: "+id);
		
		modelMapper.map(addressRequestDto, address);
		address.setModifiedAt(LocalDateTime.now());
		addressService.save(address);
		
		AddressResponseDto addressResponseDto = new AddressResponseDto();
		modelMapper.map(address, addressResponseDto);
		return new ResponseEntity<>(addressResponseDto, HttpStatus.ACCEPTED);
	}
	
	/*
	 * @DeleteMapping("/api/users/{id}") public void deleteUserById(@PathVariable
	 * long id) { userRepository.deleteById(id); }
	 */
}
