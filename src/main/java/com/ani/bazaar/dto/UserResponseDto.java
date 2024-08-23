package com.ani.bazaar.dto;

import lombok.Data;

@Data
public class UserResponseDto {
	private String userName;
	private String email;
	private String deviceToken;
	private String userPhoto;
}
