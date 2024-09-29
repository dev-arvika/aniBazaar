package com.ani.bazaar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ani.bazaar.entity.AnimalTypeEntity;
import com.ani.bazaar.repository.AnimalTypeRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AnimalTypeServiceImpl implements AnimalTypeService {

	@Autowired
	AnimalTypeRepository animalTypeRepository;
	
	@Override
	public AnimalTypeEntity save(AnimalTypeEntity animalTypeEntity) {
		return animalTypeRepository.save(animalTypeEntity);
	}

}
