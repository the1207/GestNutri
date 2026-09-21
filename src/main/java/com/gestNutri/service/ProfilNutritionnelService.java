package com.gestNutri.service;

import com.gestNutri.dto.resquest.ProfilNutritionnelResquest;
import com.gestNutri.dto.response.ProfilNutritionnelResponse;

import java.util.List;

public interface ProfilNutritionnelService {
	ProfilNutritionnelResponse create(ProfilNutritionnelResquest request);

	List<ProfilNutritionnelResponse> findAll();

	ProfilNutritionnelResponse findById(Long id);

	ProfilNutritionnelResponse update(Long id, ProfilNutritionnelResquest request);

	void delete(Long id);
}
