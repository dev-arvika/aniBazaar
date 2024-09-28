package com.ani.bazaar.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserResponseDto {
	private String userName;
	private long phone;
	private String email;
	private String deviceToken;
	private String userPhoto;
	private LocalDateTime modifiedAt;
}
