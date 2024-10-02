package com.ani.bazaar.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private AddressEntity addressEntity;

	@Column(name = "device_token")
	private String deviceToken;

	@Column(name = "user_photo")
	private String userPhoto;

	private Integer otp;
	
	@Column(name = "wa_phone", unique = true)
    private Long waPhone;

    private String work;

    private LocalDate dob;

    @ManyToOne
	@JoinColumn(name = "select_lang", nullable = false)
    @JsonManagedReference
    private LanguageEntity selectLang;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "modified_at")
	private LocalDateTime modifiedAt;
}
