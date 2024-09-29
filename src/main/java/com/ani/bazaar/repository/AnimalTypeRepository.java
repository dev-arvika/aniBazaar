package com.ani.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ani.bazaar.entity.AnimalTypeEntity;

import jakarta.transaction.Transactional;

@Transactional
public interface AnimalTypeRepository extends JpaRepository<AnimalTypeEntity, Integer>{
	
	AnimalTypeEntity findById(int id);
	
	void deleteAnimalTypeEntityById(int id);
	
}
