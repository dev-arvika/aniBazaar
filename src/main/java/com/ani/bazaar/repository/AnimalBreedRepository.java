package com.ani.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ani.bazaar.entity.AnimalBreedEntity;

import jakarta.transaction.Transactional;

@Transactional
public interface AnimalBreedRepository extends JpaRepository<AnimalBreedEntity, Integer> {

	AnimalBreedEntity findByBreedId(long id);

	void deleteAnimalBreedEntityByBreedId(long id);
}
