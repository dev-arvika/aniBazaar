package com.ani.bazaar.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.ani.bazaar.utils.AnimalSaleStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity( name = "ANIMAL_SALE")
public class AnimalSaleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "animal_id")
	private Long animalId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;
	
	@ManyToOne
	@JoinColumn(name = "animal_type_id")
	private AnimalTypeEntity animalTypeEntity;
	
	@Column(name = "lactation")
	private int lactation;
	
	@Column(name = "liters_of_milk_daily")
	private int litersOfMilkDaily;
	
	@Column(name = "milk_capacity")
	private int milkCapacity;
	
	@Column(name = "has_delivered_baby")
	private boolean hasDeliveredBaby;
	
	@Column(name = "delivery_date")
	private LocalDateTime deiveryDate;
	
	@Column(name = "is_pregnant")
	private boolean isPregnant;
	
	@Column(name = "months_of_pregnant")
	private int monthsOfPregnant;
	
	@Column(name = "calf_with_animal")
	private String calfWithAnimal;
	
	@Column(name = "more_info")
	private String moreInfo;
	
	@Column(name = "selling_price")
	private int sellingPrice;
	
	@OneToMany(mappedBy = "animalSaleEntity",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<MediaResourceEntity> mediaResourceEntity;
	
	@Enumerated(EnumType.STRING) // Store as string in the database
    private AnimalSaleStatus status;
	
	@Column(name = "is_prime", columnDefinition = "boolean default false")
	private boolean isPrime;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
}
