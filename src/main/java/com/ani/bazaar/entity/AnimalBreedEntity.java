package com.ani.bazaar.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ANIMAL_BREED")
public class AnimalBreedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "breed_id")
	private long breedId;

	@ManyToOne
	@JoinColumn(name = "animal_type_id")
	@JsonBackReference
	private AnimalTypeEntity animalTypeEntity;

	@Column(name = "animal_breed")
	private String animalBreed;
}
