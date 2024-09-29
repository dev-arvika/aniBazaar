package com.ani.bazaar.entity;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
    private long id;
    
    private String address;
    
    private Integer pincode;
    
    private String lat;
    
    private String lng;
    
    @Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "modified_at")
	private LocalDateTime modifiedAt;

    @OneToOne
	@JoinColumn(name = "user_details_id", unique = true)
    @JsonBackReference
	private UserEntity userEntity;
}
