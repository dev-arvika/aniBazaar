package com.ani.bazaar.exception;

public class AnimalBreedNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 863614776854368819L;

	public AnimalBreedNotFoundException(String message) {
		super(message);
	}
}
