package com.gestNutri.service;

import com.gestNutri.dto.resquest.MoteurOptimisationResquest;
import com.gestNutri.dto.response.MoteurOptimisationResponse;

public interface MoteurOptimisationService {
	MoteurOptimisationResponse resoudre(MoteurOptimisationResquest request);
}
