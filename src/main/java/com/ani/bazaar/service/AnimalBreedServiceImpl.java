package com.ani.bazaar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ani.bazaar.entity.AnimalBreedEntity;
import com.ani.bazaar.repository.AnimalBreedRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AnimalBreedServiceImpl implements AnimalBreedService {
	@Autowired
	AnimalBreedRepository animalBreedRepository;

	@Override
	public AnimalBreedEntity save(AnimalBreedEntity animalBreedEntity) {
		return animalBreedRepository.save(animalBreedEntity);
	}

}
