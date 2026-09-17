package com.gestNutri.controller;

import com.gestNutri.dto.resquest.MoteurOptimisationResquest;
import com.gestNutri.dto.response.MoteurOptimisationResponse;
import com.gestNutri.service.MoteurOptimisationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/moteur-optimisation")
public class MoteurOptimisationController {
	private final MoteurOptimisationService moteurOptimisationService;

	public MoteurOptimisationController(MoteurOptimisationService moteurOptimisationService) {
		this.moteurOptimisationService = moteurOptimisationService;
	}

	@PostMapping("/resoudre")
	public ResponseEntity<MoteurOptimisationResponse> resoudre(
			@RequestBody MoteurOptimisationResquest request) {
		MoteurOptimisationResponse response = moteurOptimisationService.resoudre(request);
		return response.succes() ? ResponseEntity.ok(response) : ResponseEntity.unprocessableEntity().body(response);
	}
}
