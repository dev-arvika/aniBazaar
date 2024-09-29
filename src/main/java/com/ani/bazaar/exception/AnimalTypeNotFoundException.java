package com.ani.bazaar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AnimalTypeNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8552303435869377502L;

	public AnimalTypeNotFoundException(String message){		
		super(message);
	}
}
