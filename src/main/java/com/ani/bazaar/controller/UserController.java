package com.ani.bazaar.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ani.bazaar.entity.UserEntity;
import com.ani.bazaar.exception.UserNotFoundException;
import com.ani.bazaar.repository.UserRepository;

import jakarta.validation.Valid;


@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/api/users")
	public List<UserEntity> retrieveAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/api/users/{id}")
	public EntityModel<Optional<UserEntity>>  retriveUserById(@PathVariable Integer id) {
		Optional<UserEntity> user = userRepository.findById(id);
		if (user == null)
			throw new UserNotFoundException("id: "+id);

		EntityModel<Optional<UserEntity>> entityModel = EntityModel.of(user);
		
		WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}

	@PostMapping("/api/users")
	public ResponseEntity<UserEntity> saveUser(@Valid @RequestBody UserEntity user) {
		UserEntity savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/api/users/{id}")
	public void deleteUserById(@PathVariable Integer id) {
		userRepository.deleteById(id);
	}
}
