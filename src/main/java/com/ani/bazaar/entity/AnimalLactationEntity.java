package com.ani.bazaar.entity;

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
@Entity(name = "ANIMAL_LACTATION")
public class AnimalLactationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long lactId;

	private String lactation;
}
