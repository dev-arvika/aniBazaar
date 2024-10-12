package com.ani.bazaar.service;

import java.util.List;

import com.ani.bazaar.dto.AnimalSaleResponseDto;
import com.ani.bazaar.entity.AnimalSaleEntity;

public interface AnimalSaleService {

	AnimalSaleEntity save(AnimalSaleEntity animalSaleEntity);
	
	List<AnimalSaleResponseDto> getAllAnimal();	
	
}
