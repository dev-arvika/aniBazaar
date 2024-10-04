package com.ani.bazaar.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserRequestDto {
	private String userName;
	private String email;
	private Long phone;
    private Long waPhone;
    private String work;
    private LocalDate dob;
    private String selectLang;
	private String deviceToken;
}
