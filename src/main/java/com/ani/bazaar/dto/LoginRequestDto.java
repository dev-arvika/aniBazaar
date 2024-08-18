package com.ani.bazaar.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
	private long phone;
	private boolean resend;
}
