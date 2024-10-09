package com.ani.bazaar.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ani.bazaar.dto.SalePostResponseDto;
import com.ani.bazaar.entity.SalePostEntity;
import com.ani.bazaar.repository.SalePostRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SalePostServiceImpl implements SalePostService {

	@Autowired
	SalePostRepository salePostRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public SalePostEntity save(SalePostEntity salePostEntity) {
		return salePostRepository.save(salePostEntity);
	}

	@Override
	public List<SalePostResponseDto> getAllSalePost() {
		List<SalePostEntity> saleposts = salePostRepository.findAll();
		return saleposts.stream().map(salepost -> modelMapper.map(salepost, SalePostResponseDto.class))
								.collect(Collectors.toList());
	}

}
