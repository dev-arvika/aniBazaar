package com.ani.bazaar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ani.bazaar.entity.MediaResourceEntity;

import jakarta.transaction.Transactional;

import com.ani.bazaar.entity.AnimalSaleEntity;

@Transactional
public interface MediaResourceRepository extends JpaRepository<MediaResourceEntity, Integer>{

	List<MediaResourceEntity> findByAnimalSaleEntity(AnimalSaleEntity animalSaleEntity);
	
}
