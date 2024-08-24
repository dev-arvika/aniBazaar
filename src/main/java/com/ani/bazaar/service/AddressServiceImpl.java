package com.ani.bazaar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ani.bazaar.entity.AddressEntity;
import com.ani.bazaar.repository.AddressRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressRepository addressRepository;

	@Override
	public AddressEntity save(AddressEntity addressEntity) {
		return addressRepository.save(addressEntity);
	}

	

}
