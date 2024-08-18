package com.ani.bazaar.dto;

import lombok.Data;

@Data
public class VerifyRequestDto {
	private long phone;
	private int otp;
}
