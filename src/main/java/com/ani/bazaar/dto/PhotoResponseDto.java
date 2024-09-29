package com.ani.bazaar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PhotoResponseDto {
	private String photoFile;
}
