package com.ani.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ani.bazaar.entity.LanguageEntity;

import jakarta.transaction.Transactional;

@Transactional
public interface LanguageRepository extends JpaRepository<LanguageEntity, Integer>{
	
	LanguageEntity findById(int id);
	
	LanguageEntity findByLanguage(String language);
	
	
}
