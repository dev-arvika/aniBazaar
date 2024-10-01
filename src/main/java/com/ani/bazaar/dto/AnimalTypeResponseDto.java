package com.ani.bazaar.dto;

import java.util.List;

import com.ani.bazaar.entity.AnimalBreedEntity;

import lombok.Data;

@Data
public class AnimalTypeResponseDto {
	private String animalType;
	private String typeIcon;
	private String gender;
	private List<AnimalBreedEntity> animalBreedEntity;
}
