package com.ani.bazaar.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SalePostRequestDto {

	private long animalTypeId;
	private int lactation;
	private int litersOfMilkDaily;
	private int milkCapacity;
	private boolean hasDeliveredBaby;
	private LocalDateTime deiveryDate;
	private boolean isPregnant;
	private int monthsOfPregnant;
	private String calfWithAnimal;
	private String moreInfo;
	private int sellingPrice;
	private String status;
	private boolean isPrime;
	private String animalImages;
}
