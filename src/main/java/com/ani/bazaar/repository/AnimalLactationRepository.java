package com.ani.bazaar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ani.bazaar.entity.AnimalLactationEntity;

import jakarta.transaction.Transactional;

@Transactional
public interface AnimalLactationRepository extends JpaRepository<AnimalLactationEntity, Integer> {

	List<AnimalLactationEntity> findAll();

}
