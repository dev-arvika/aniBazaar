package com.ani.bazaar.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AddressResponseDto {
	
	private String address;
    private String lat;
    private String lng;
    private Integer pincode;
    private LocalDateTime modifiedAt;
}
