package com.ani.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ani.bazaar.entity.AnimalBreedEntity;

import jakarta.transaction.Transactional;

@Transactional
public interface AnimalBreedRepository extends JpaRepository<AnimalBreedEntity, Integer> {

	AnimalBreedEntity findById(long id);

	void deleteAnimalBreedEntityById(long id);
}
