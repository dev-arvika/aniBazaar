package com.ani.bazaar.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ani.bazaar.dto.AnimalSaleRequestDto;
import com.ani.bazaar.dto.AnimalSaleResponseDto;
import com.ani.bazaar.entity.AnimalSaleEntity;
import com.ani.bazaar.entity.AnimalTypeEntity;
import com.ani.bazaar.entity.MediaResourceEntity;
import com.ani.bazaar.entity.UserEntity;
import com.ani.bazaar.exception.SalePostNotFoundExeption;
import com.ani.bazaar.exception.UserNotFoundException;
import com.ani.bazaar.repository.AnimalSaleRepository;
import com.ani.bazaar.repository.AnimalTypeRepository;
import com.ani.bazaar.repository.MediaResourceRepository;
import com.ani.bazaar.repository.UserRepository;
import com.ani.bazaar.service.AnimalSaleService;
import com.ani.bazaar.utils.AnimalSaleStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AnimalSaleController {
	@Autowired
	AnimalSaleRepository animalSaleRepository;

	@Autowired
	AnimalSaleService animalSaleService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AnimalTypeRepository animalTypeRepository;

	@Autowired
	MediaResourceRepository mediaResourceRepository;

	@Autowired
	ModelMapper modelMapper;

	@GetMapping("/animal/sales")
	public ResponseEntity<List<AnimalSaleResponseDto>> getAllAnimal() {
		List<AnimalSaleResponseDto> animalSaleResponseDto = animalSaleService.getAllAnimal();
		return new ResponseEntity<>(animalSaleResponseDto, HttpStatus.OK);
	}

	@GetMapping("/animal/sale/{id}")
	public ResponseEntity<AnimalSaleResponseDto> getAnimalById(@PathVariable long id) {
		AnimalSaleEntity animalSaleEntity = animalSaleRepository.findByAnimalId(id);
		if (animalSaleEntity == null)
			throw new SalePostNotFoundExeption("Id:" + id);

		AnimalSaleResponseDto animalSaleResponseDto = new AnimalSaleResponseDto();
		modelMapper.map(animalSaleEntity, animalSaleResponseDto);
		return new ResponseEntity<>(animalSaleResponseDto, HttpStatus.OK);
	}

	@PostMapping("/users/{userid}/animal-sale")
	public ResponseEntity<AnimalSaleResponseDto> saveSalePost(@Valid @PathVariable("userid") long userId,
			@RequestBody AnimalSaleRequestDto animalSaleRequestDto) {
		UserEntity userDtls = userRepository.findById(userId);
		if (userDtls == null)
			throw new UserNotFoundException("Id:" + userId);

		String[] animalImages = (animalSaleRequestDto.getAnimalImages() != null
				&& !animalSaleRequestDto.getAnimalImages().isBlank())
						? animalSaleRequestDto.getAnimalImages().replaceAll("\\s+", "").split(",")
						: new String[0];

		StringJoiner animalImageUriJoiner = new StringJoiner(", ");
		AnimalTypeEntity animalTypeEntity = animalTypeRepository.findByTypeId(animalSaleRequestDto.getAnimalTypeId());
				
		AnimalSaleEntity animalSaleEntity = modelMapper.map(animalSaleRequestDto, AnimalSaleEntity.class);
		animalSaleEntity.setAnimalId(null);
		animalSaleEntity.setStatus(AnimalSaleStatus.PENDING);
		animalSaleEntity.setCreatedAt(LocalDateTime.now());
		animalSaleEntity.setUserEntity(userDtls);
		animalSaleEntity.setAnimalTypeEntity(animalTypeEntity);

		AnimalSaleEntity SavedAnimalSaleEntity = animalSaleService.save(animalSaleEntity);

		for (String imageName : animalImages) {
			MediaResourceEntity mediaResourceEntity = new MediaResourceEntity();
			mediaResourceEntity.setMediaPath(imageName);
			mediaResourceEntity.setAnimalSaleEntity(SavedAnimalSaleEntity);
			mediaResourceEntity.setCreatedAt(LocalDateTime.now());
			mediaResourceRepository.save(mediaResourceEntity);

			String animalImageUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/animal-image/")
					.path(imageName).toUriString();

			animalImageUriJoiner.add(animalImageUri);
		}
		String animalImageUri = animalImageUriJoiner.toString();

		AnimalSaleResponseDto animalSaleResponseDto = new AnimalSaleResponseDto();
		modelMapper.map(SavedAnimalSaleEntity, animalSaleResponseDto);
		animalSaleResponseDto.setAnimalImage(animalImageUri);
		return new ResponseEntity<>(animalSaleResponseDto, HttpStatus.CREATED);
	}

	@PutMapping("user/{userid}/animal-sale/{postid}")
	public ResponseEntity<AnimalSaleResponseDto> updateSalePost(@Valid @PathVariable("userid") long userId,
			@PathVariable("postid") int salePostId, @RequestBody AnimalSaleRequestDto animalSaleRequestDto) {

		UserEntity userDtls = userRepository.findById(userId);
		if (userDtls == null)
			throw new UserNotFoundException("Id:" + userId);

		AnimalSaleEntity animalSaleEntity = animalSaleRepository.findByAnimalId(salePostId);
		if (animalSaleEntity == null)
			throw new SalePostNotFoundExeption("Id:" + salePostId);

		modelMapper.map(animalSaleRequestDto, animalSaleEntity);
		animalSaleService.save(animalSaleEntity);
		AnimalSaleResponseDto animalSaleResponseDto = new AnimalSaleResponseDto();
		modelMapper.map(animalSaleEntity, animalSaleResponseDto);
		return new ResponseEntity<>(animalSaleResponseDto, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/animal-sale/{id}")
	public ResponseEntity<String> deleteSalePost(@PathVariable long id) {
		AnimalSaleEntity animalSaleEntity = animalSaleRepository.findByAnimalId(id);
		if (animalSaleEntity == null)
			throw new SalePostNotFoundExeption("Id:" + id);

		animalSaleRepository.deleteById(id);
		return new ResponseEntity<>("Sale Post Removed.", HttpStatus.OK);
	}

}
