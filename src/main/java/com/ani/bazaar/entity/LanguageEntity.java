package com.ani.bazaar.entity;


import java.time.LocalDateTime;

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
@Entity(name = "USER_LANGUAGE")
public class LanguageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long langId;
	
    private String language;
    
    private String description;
    
	private LocalDateTime createdAt;
}
