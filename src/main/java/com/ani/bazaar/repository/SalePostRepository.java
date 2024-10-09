package com.ani.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ani.bazaar.entity.SalePostEntity;

public interface SalePostRepository extends JpaRepository<SalePostEntity, Integer> {

	SalePostEntity findById(long id);

	void deleteSalePostEntityById(long id);
	
}
