package com.gestNutri.service;

import com.gestNutri.dto.response.FormuleResponse;

import java.util.List;

public interface FormuleService {
	List<FormuleResponse> findAll();

	FormuleResponse findById(Long id);

	void delete(Long id);
}
