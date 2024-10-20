package com.ani.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ani.bazaar.entity.AddressEntity;

import jakarta.transaction.Transactional;

@Transactional
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

	//AddressEntity getAddressByUserEntity(long userId);

	AddressEntity findById(long id);
}
