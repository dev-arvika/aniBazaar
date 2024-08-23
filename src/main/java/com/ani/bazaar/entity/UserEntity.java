package com.ani.bazaar.entity;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "USER_DETAILS")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Size(min = 2, message = "Name should be atleast 2 charectors")
	@Column(name = "user_name")
	private String userName;

	@Column(name = "phone", unique=true)
	private Long phone;

	private String email;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "address_id")
	private AddressEntity addressEntity;

	@Column(name = "device_token")
	private String deviceToken;

	@Column(name = "user_photo")
	private String userPhoto;

	private Integer otp;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "modified_at")
	private LocalDateTime modifiedAt;
}
