package com.ani.bazaar.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ani.bazaar.entity.MediaResourceEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class SalePostResponseDto {

	@JsonIgnore
	private long id;
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
	@JsonBackReference
	private List<MediaResourceEntity> mediaResourceEntity;
	private String status;
	private boolean isPrime;
	private LocalDateTime createdAt;
	@JsonIgnore
	private String animalImage;

	public String getAnimalImages() {	// custom method to retrieve image paths 
		if (mediaResourceEntity == null || mediaResourceEntity.isEmpty()) {
			if(animalImage.isEmpty()) {	// if resource entity not fetched, return from animalImage string
				return "";
			}
			else {
				return this.getAnimalImage();
			}
		}
		String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/salepost/" + this.getId() + "/images/").toUriString();
		return mediaResourceEntity.stream().map(media -> baseUrl + media.getMediaPath()) // prepend the base URL
				.collect(Collectors.joining(", "));

	}

}
