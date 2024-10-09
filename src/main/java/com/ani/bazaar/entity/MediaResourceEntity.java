package com.ani.bazaar.entity;

import java.time.LocalDateTime;

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
@Entity(name = "MEDIA_RESOURCES")
public class MediaResourceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name= "media_path")
	private String mediaPath;
	
	@ManyToOne
	@JoinColumn(name = "sale_post_id")
	@JsonBackReference
	private SalePostEntity salePostEntity;
	
	@Column(name= "created_at")
	private LocalDateTime createdAt;
}
