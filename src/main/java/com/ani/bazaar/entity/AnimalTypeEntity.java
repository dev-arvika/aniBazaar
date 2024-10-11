package com.ani.bazaar.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ANIMAL_TYPE")
public class AnimalTypeEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;	
	
	@Column(name="animal_type")
	private String animalType;
	
	@Column(name="type_icon")
	private String typeIcon;
	
	private String gender;
	
	@OneToMany(mappedBy = "animalTypeEntity",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<AnimalBreedEntity> animalBreedEntity;
	
	@OneToMany(mappedBy = "animalTypeEntity",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<AnimalSaleEntity> animalSaleEntities;
}
