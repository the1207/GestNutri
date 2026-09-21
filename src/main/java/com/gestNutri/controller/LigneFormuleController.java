package com.gestNutri.controller;

import com.gestNutri.dto.response.LigneFormuleResponse;
import com.gestNutri.service.LigneFormuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lignes-formule")
public class LigneFormuleController {
	private final LigneFormuleService ligneFormuleService;

	public LigneFormuleController(LigneFormuleService ligneFormuleService) {
		this.ligneFormuleService = ligneFormuleService;
	}

	@GetMapping
	public ResponseEntity<List<LigneFormuleResponse>> findByFormuleId(@RequestParam Long formuleId) {
		return ResponseEntity.ok(ligneFormuleService.findByFormuleId(formuleId));
	}
}
