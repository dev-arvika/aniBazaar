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

import com.ani.bazaar.dto.SalePostRequestDto;
import com.ani.bazaar.dto.SalePostResponseDto;
import com.ani.bazaar.entity.AnimalTypeEntity;
import com.ani.bazaar.entity.MediaResourceEntity;
import com.ani.bazaar.entity.SalePostEntity;
import com.ani.bazaar.entity.UserEntity;
import com.ani.bazaar.exception.SalePostNotFoundExeption;
import com.ani.bazaar.exception.UserNotFoundException;
import com.ani.bazaar.repository.AnimalTypeRepository;
import com.ani.bazaar.repository.MediaResourceRepository;
import com.ani.bazaar.repository.SalePostRepository;
import com.ani.bazaar.repository.UserRepository;
import com.ani.bazaar.service.SalePostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AnimalSaleController {
	@Autowired
	SalePostRepository salePostRepository;

	@Autowired
	SalePostService salePostService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AnimalTypeRepository animalTypeRepository;

	@Autowired
	MediaResourceRepository mediaResourceRepository;

	@Autowired
	ModelMapper modelMapper;

	@PostMapping("/users/{userid}/animal-sale")
	public ResponseEntity<SalePostResponseDto> saveSalePost(@Valid @PathVariable("userid") long userId,
			@RequestBody SalePostRequestDto salePostRequestDto) {
		UserEntity userDtls = userRepository.findById(userId);
		if (userDtls == null)
			throw new UserNotFoundException("Id:" + userId);
		String[] animalImages = salePostRequestDto.getAnimalImages().split(",");
		StringJoiner animalImageUriJoiner = new StringJoiner(", ");
		AnimalTypeEntity animalTypeEntity = animalTypeRepository.findById(salePostRequestDto.getAnimalTypeId());

		SalePostEntity salePostEntity = modelMapper.map(salePostRequestDto, SalePostEntity.class);
		salePostEntity.setCreatedAt(LocalDateTime.now());
		salePostEntity.setUserEntity(userDtls);
		salePostEntity.setAnimalTypeEntity(animalTypeEntity);

		SalePostEntity savedSalePost = null;
		savedSalePost = salePostService.save(salePostEntity);

		for (String imageName : animalImages) {
			MediaResourceEntity mediaResourceEntity = new MediaResourceEntity();
			mediaResourceEntity.setMediaPath(imageName);
			mediaResourceEntity.setSalePostEntity(savedSalePost);
			mediaResourceEntity.setCreatedAt(LocalDateTime.now());
			mediaResourceRepository.save(mediaResourceEntity);

			String animalImageUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/animal-image/")
					.path(imageName).toUriString();

			animalImageUriJoiner.add(animalImageUri);
		}
		String animalImageUri = animalImageUriJoiner.toString();

		SalePostResponseDto salePostResponseDto = new SalePostResponseDto();
		modelMapper.map(savedSalePost, salePostResponseDto);
		salePostResponseDto.setAnimalImage(animalImageUri);
		System.out.println("animalImages" + salePostResponseDto.getAnimalImages());
		return new ResponseEntity<>(salePostResponseDto, HttpStatus.CREATED);
	}

	@GetMapping("/animal/salepost/{id}")
	public ResponseEntity<SalePostResponseDto> getSalePostById(@PathVariable long id) {
		SalePostEntity salePostEntity = salePostRepository.findById(id);
		if (salePostEntity == null)
			throw new SalePostNotFoundExeption("Id:" + id);

		SalePostResponseDto salePostResponseDto = new SalePostResponseDto();
		modelMapper.map(salePostEntity, salePostResponseDto);
		return new ResponseEntity<>(salePostResponseDto, HttpStatus.OK);
	}

	@GetMapping("/animal/saleposts")
	public ResponseEntity<List<SalePostResponseDto>> getSalePosts() {
		List<SalePostResponseDto> saleposts = salePostService.getAllSalePost();
		return new ResponseEntity<>(saleposts, HttpStatus.OK);
	}

	@PutMapping("/animal/{userid}/salepost/{postid}")
	public ResponseEntity<SalePostResponseDto> updateSalePost(@Valid @PathVariable("userid") long userId,
			@PathVariable("animaltypeid") int animalTypeId, @PathVariable("postid") int salePostId,
			@RequestBody SalePostRequestDto salePostRequestDto) {

		UserEntity userDtls = userRepository.findById(userId);
		if (userDtls == null)
			throw new UserNotFoundException("Id:" + userId);

		SalePostEntity salePostEntity = salePostRepository.findById(salePostId);
		if (salePostEntity == null)
			throw new SalePostNotFoundExeption("Id:" + salePostId);

		modelMapper.map(salePostRequestDto, salePostEntity);
		salePostService.save(salePostEntity);
		SalePostResponseDto salePostResponseDto = new SalePostResponseDto();
		modelMapper.map(salePostEntity, salePostResponseDto);
		return new ResponseEntity<>(salePostResponseDto, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/animal/salepost/{id}")
	public ResponseEntity<String> deleteSalePost(@PathVariable int id) {
		SalePostEntity salePostEntity = salePostRepository.findById(id);
		if (salePostEntity == null)
			throw new SalePostNotFoundExeption("Id:" + id);

		salePostRepository.deleteById(id);
		return new ResponseEntity<>("Sale Post Removed.", HttpStatus.OK);
	}

}
