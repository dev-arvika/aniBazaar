package com.ani.bazaar.service;

import java.util.List;

import com.ani.bazaar.dto.SalePostResponseDto;
import com.ani.bazaar.entity.AnimalSaleEntity;

public interface AnimalSaleService {

	AnimalSaleEntity save(AnimalSaleEntity animalSaleEntity);
	
	List<SalePostResponseDto> getAllAnimal();	
	
}
