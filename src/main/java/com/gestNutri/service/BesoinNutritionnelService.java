package com.gestNutri.service;

import com.gestNutri.dto.resquest.BesoinNutritionnelResquest;
import com.gestNutri.dto.response.BesoinNutritionnelResponse;

import java.util.List;

public interface BesoinNutritionnelService {
	BesoinNutritionnelResponse create(BesoinNutritionnelResquest request);

	List<BesoinNutritionnelResponse> findAll();

	void delete(Long id);
}
