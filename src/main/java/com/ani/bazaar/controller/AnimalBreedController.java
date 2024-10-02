package com.ani.bazaar.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ani.bazaar.dto.AnimalBreedRequestDto;
import com.ani.bazaar.dto.AnimalBreedResponseDto;
import com.ani.bazaar.entity.AnimalBreedEntity;
import com.ani.bazaar.entity.AnimalTypeEntity;
import com.ani.bazaar.exception.AnimalBreedNotFoundException;
import com.ani.bazaar.exception.AnimalTypeNotFoundException;
import com.ani.bazaar.repository.AnimalBreedRepository;
import com.ani.bazaar.repository.AnimalTypeRepository;
import com.ani.bazaar.service.AnimalBreedService;

import jakarta.validation.Valid;

@RestController
public class AnimalBreedController {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	AnimalBreedRepository animalBreedRepository;

	@Autowired
	AnimalBreedService animalBreedService;

	@Autowired
	AnimalTypeRepository animalTypeRepository;

	@GetMapping("api/animal/breed/{id}")
	public ResponseEntity<AnimalBreedResponseDto> retriveAnimalBreedById(@PathVariable int id) {
		AnimalBreedEntity animalBreedEntity = animalBreedRepository.findById(id);
		if (animalBreedEntity == null)
			throw new AnimalBreedNotFoundException("Id:" + id);

		AnimalBreedResponseDto animalBreedResponseDto = new AnimalBreedResponseDto();
		modelMapper.map(animalBreedEntity, animalBreedResponseDto);
		return new ResponseEntity<>(animalBreedResponseDto, HttpStatus.OK);
	}

	@GetMapping("api/animal/breeds")
	public ResponseEntity<List<AnimalBreedEntity>> getAllAnimalBreed() {
		List<AnimalBreedEntity> animalBreed = animalBreedRepository.findAll();
		return new ResponseEntity<>(animalBreed, HttpStatus.OK);
	}

	@PostMapping("api/animal/{id}/breed")
	public ResponseEntity<AnimalBreedResponseDto> saveAnimalBreed(@Valid @PathVariable int id,
			@RequestBody AnimalBreedRequestDto animalBreedRequestDto) {

		AnimalTypeEntity animalTypeEntity = animalTypeRepository.findById(id);
		if (animalTypeEntity == null)
			throw new AnimalTypeNotFoundException("Id :" + id);

		AnimalBreedEntity animalBreedEntity = modelMapper.map(animalBreedRequestDto, AnimalBreedEntity.class);
		animalBreedEntity.setAnimalTypeEntity(animalTypeEntity);
		AnimalBreedEntity savedAnimalBreed = null;
		savedAnimalBreed = animalBreedService.save(animalBreedEntity);
		AnimalBreedResponseDto animalBreedResponseDto = new AnimalBreedResponseDto();
		modelMapper.map(savedAnimalBreed, animalBreedResponseDto);

		return new ResponseEntity<>(animalBreedResponseDto, HttpStatus.CREATED);

	}

	@PutMapping("api/animal/breed/{id}")
	public ResponseEntity<AnimalBreedResponseDto> updateAnimalBreed(@Valid @PathVariable int id,
			@RequestBody AnimalBreedRequestDto animalBreedRequestDto) {
		AnimalBreedEntity animalBreedDtls = animalBreedRepository.findById(id);
		if (animalBreedDtls == null)
			throw new AnimalBreedNotFoundException("Id:" + id);

		modelMapper.map(animalBreedRequestDto, animalBreedDtls);
		animalBreedService.save(animalBreedDtls);
		AnimalBreedResponseDto animalBreedResponseDto = new AnimalBreedResponseDto();
		modelMapper.map(animalBreedDtls, animalBreedResponseDto);
		return new ResponseEntity<>(animalBreedResponseDto, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("api/animal/breed/{id}")
	public ResponseEntity<String> deleteAnimalBreed(@PathVariable int id) {
		AnimalBreedEntity animalBreedEntity = animalBreedRepository.findById(id);
		if (animalBreedEntity == null)
			throw new AnimalTypeNotFoundException("Id:" + id);

		animalBreedRepository.deleteAnimalBreedEntityById(id);
		return new ResponseEntity<>("Animal Breed Removed.", HttpStatus.ACCEPTED);
	}
}
