package com.ani.bazaar.dto;

import lombok.Data;

@Data
public class UserResponseDto {
	private String userName;
	private Long phone;
	private Long waPhone;
	private String email;
    private String work;
    private String dob;
    private String selectLang;
	private String deviceToken;
	private String userImage;
}
