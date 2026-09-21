package com.gestNutri.service;

import com.gestNutri.dto.response.LigneFormuleResponse;

import java.util.List;

public interface LigneFormuleService {
	List<LigneFormuleResponse> findByFormuleId(Long formuleId);
}
