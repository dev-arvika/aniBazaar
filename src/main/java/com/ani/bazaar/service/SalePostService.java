package com.ani.bazaar.service;

import java.util.List;

import com.ani.bazaar.dto.SalePostResponseDto;
import com.ani.bazaar.entity.SalePostEntity;

public interface SalePostService {

	SalePostEntity save(SalePostEntity salePostEntity);
	
	List<SalePostResponseDto> getAllSalePost();	
	
}
