package com.ani.bazaar.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ADDRESS")
public class AddressEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String address;
    
    private Integer pincode;
    
    private String lat;
    
    private String lng;

    @OneToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "user_details_id")
	private UserEntity userEntity;
}
