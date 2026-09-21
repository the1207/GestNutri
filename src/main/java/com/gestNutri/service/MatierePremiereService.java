package com.gestNutri.service;

import com.gestNutri.dto.resquest.MatierePremiereResquest;
import com.gestNutri.dto.response.MatierePremiereResponse;

import java.util.List;

public interface MatierePremiereService {
	MatierePremiereResponse create(MatierePremiereResquest request);

	List<MatierePremiereResponse> findAll();

	MatierePremiereResponse findById(Long id);

	MatierePremiereResponse update(Long id, MatierePremiereResquest request);

	void delete(Long id);
}
