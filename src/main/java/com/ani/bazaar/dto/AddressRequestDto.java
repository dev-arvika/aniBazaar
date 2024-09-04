package com.ani.bazaar.dto;

import lombok.Data;

@Data
public class AddressRequestDto {

	private String address;
    private String lat;
    private String lng;
    private Integer pincode;
}