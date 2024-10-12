package com.ani.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ani.bazaar.entity.AnimalSaleEntity;

import jakarta.transaction.Transactional;

@Transactional
public interface AnimalSaleRepository extends JpaRepository<AnimalSaleEntity, Long> {

	AnimalSaleEntity findById(long id);

	void deleteSalePostEntityById(long id);
	
}
