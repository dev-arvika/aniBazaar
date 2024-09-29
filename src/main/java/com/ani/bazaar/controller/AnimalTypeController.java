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

import com.ani.bazaar.dto.AnimalTypeRequestDto;
import com.ani.bazaar.dto.AnimalTypeResponseDto;
import com.ani.bazaar.entity.AnimalTypeEntity;
import com.ani.bazaar.exception.AnimalTypeNotFoundException;
import com.ani.bazaar.repository.AnimalTypeRepository;
import com.ani.bazaar.service.AnimalTypeService;

import jakarta.validation.Valid;

@RestController
public class AnimalTypeController {
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	AnimalTypeService animalTypeService;

	@Autowired
	AnimalTypeRepository animalTypeRepository;

	@GetMapping("api/animal/type/{id}")
	public ResponseEntity<AnimalTypeResponseDto> retriveAnimalTypeById(@PathVariable int id) {
		AnimalTypeEntity animalTypeEntity = animalTypeRepository.findById(id);
		if (animalTypeEntity == null)
			throw new AnimalTypeNotFoundException("Id:" + id);

		AnimalTypeResponseDto animalTypeResponseDto = new AnimalTypeResponseDto();
		modelMapper.map(animalTypeEntity, animalTypeResponseDto);
		return new ResponseEntity<>(animalTypeResponseDto, HttpStatus.OK);
	}

	@GetMapping("api/animal/types")
	public ResponseEntity<List<AnimalTypeEntity>> getAllAnimalType() {
		List<AnimalTypeEntity> animals = animalTypeRepository.findAll();
		return new ResponseEntity<>(animals, HttpStatus.OK);
	}

	@PostMapping("api/animal/type")
	public ResponseEntity<AnimalTypeResponseDto> saveAnimalType(
			@RequestBody AnimalTypeRequestDto animalTypeRequestDto) {

		AnimalTypeEntity animalTypeEntity = modelMapper.map(animalTypeRequestDto, AnimalTypeEntity.class);
		AnimalTypeEntity savedAnimalType = null;
		savedAnimalType = animalTypeService.save(animalTypeEntity);
		AnimalTypeResponseDto animalTypeResponseDto = new AnimalTypeResponseDto();
		modelMapper.map(savedAnimalType, animalTypeResponseDto);

		return new ResponseEntity<>(animalTypeResponseDto, HttpStatus.CREATED);

	}

	@PutMapping("api/animal/type/{id}")
	public ResponseEntity<AnimalTypeResponseDto> updateAnimalType(@Valid @PathVariable int id,
			@RequestBody AnimalTypeRequestDto animalTypeRequestDto) {
		AnimalTypeEntity animalTypeDtls = animalTypeRepository.findById(id);
		if (animalTypeDtls == null)
			throw new AnimalTypeNotFoundException("Id:" + id);

		modelMapper.map(animalTypeRequestDto, animalTypeDtls);
		animalTypeService.save(animalTypeDtls);
		AnimalTypeResponseDto animalTypeResponseDto = new AnimalTypeResponseDto();
		modelMapper.map(animalTypeDtls, animalTypeResponseDto);
		return new ResponseEntity<>(animalTypeResponseDto, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("api/animal/type/{id}")
	public ResponseEntity<String> deleteAnimalType(@PathVariable int id) {
		AnimalTypeEntity animalTypeEntity = animalTypeRepository.findById(id);
		if (animalTypeEntity == null)
			throw new AnimalTypeNotFoundException("Id:" + id);

		animalTypeRepository.deleteAnimalTypeEntityById(id);
		return new ResponseEntity<>("Animal Type Removed.", HttpStatus.ACCEPTED);
	}

}
