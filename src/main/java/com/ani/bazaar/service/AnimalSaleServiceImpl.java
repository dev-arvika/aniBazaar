package com.ani.bazaar.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ani.bazaar.dto.SalePostResponseDto;
import com.ani.bazaar.entity.AnimalSaleEntity;
import com.ani.bazaar.repository.AnimalSaleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AnimalSaleServiceImpl implements AnimalSaleService {

	@Autowired
	AnimalSaleRepository animalSaleRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public AnimalSaleEntity save(AnimalSaleEntity animalSaleEntity) {
		return animalSaleRepository.save(animalSaleEntity);
	}

	@Override
	public List<SalePostResponseDto> getAllAnimal() {
		List<AnimalSaleEntity> saleposts = animalSaleRepository.findAll();
		return saleposts.stream().map(salepost -> modelMapper.map(salepost, SalePostResponseDto.class))
								.collect(Collectors.toList());
	}

}
