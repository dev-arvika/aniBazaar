package com.ani.bazaar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler {

    @ExceptionHandler(value = { UserNotFoundException.class })
    public ResponseEntity<Object> userNotFoundException(Exception ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserNotFound "+ex.getMessage());
    }
    
    @ExceptionHandler(value = { AddressNotFoundException.class })
    public ResponseEntity<Object> addressNotFoundException(Exception ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AddressNotFound "+ex.getMessage());
    }
    
    @ExceptionHandler(value = { AddressAlreadyExistException.class })
    public ResponseEntity<Object> addressAlreadyExistException(Exception ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("AddressAlreadyExist "+ex.getMessage());
    }
    
    @ExceptionHandler(value = { AnimalTypeNotFoundException.class})
    public ResponseEntity<Object> animalTypeNotFoundException(Exception ex) {
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AnimalTypeNotFound "+ex.getMessage());
    }
    
	@ExceptionHandler(value = { AnimalBreedNotFoundException.class })
	public ResponseEntity<Object> animalBreedNotFoundException(Exception ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AnimalBreedNotFoundException " + ex.getMessage());
	}
	
	@ExceptionHandler(value = { SalePostNotFoundExeption.class })
	public ResponseEntity<Object> SalePostNotFoundExeption(Exception ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SalePostNotFoundExeption " + ex.getMessage());
	}
	
	@ExceptionHandler(value = { MediaResourceNotFoundException.class })
	public ResponseEntity<Object> MediaResourceNotFoundException(Exception ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MediaResourceNotFoundException " + ex.getMessage());
	}
}