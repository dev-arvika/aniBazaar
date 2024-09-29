package com.ani.bazaar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private int id;	
	
	@Column(name="animal_type")
	private String animalType;
	
	@Column(name="type_icon")
	private String typeIcon;
	
	private String gender;
}
