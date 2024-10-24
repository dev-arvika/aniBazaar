package com.ani.bazaar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LanguageResponseDto {
	private String language;
	private String description;
}
