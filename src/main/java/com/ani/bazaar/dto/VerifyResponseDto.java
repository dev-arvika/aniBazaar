package com.ani.bazaar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class VerifyResponseDto {
	private String accessToken;
	private String refreshToken;
	private String name;
	private String slug;//"ram-0xkihcsi4e"
    private long userId; //15508285300
    private String error;//"Unauthorized"
}
