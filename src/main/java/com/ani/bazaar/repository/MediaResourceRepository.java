package com.ani.bazaar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ani.bazaar.entity.MediaResourceEntity;
import com.ani.bazaar.entity.SalePostEntity;

public interface MediaResourceRepository extends JpaRepository<MediaResourceEntity, Integer>{

	List<MediaResourceEntity> findBySalePostEntity(SalePostEntity salePostEntity);
	
}
