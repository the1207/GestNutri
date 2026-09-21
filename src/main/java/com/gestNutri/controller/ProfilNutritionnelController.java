package com.gestNutri.controller;

import com.gestNutri.dto.resquest.ProfilNutritionnelResquest;
import com.gestNutri.dto.response.ProfilNutritionnelResponse;
import com.gestNutri.service.ProfilNutritionnelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/profils-nutritionnels")
public class ProfilNutritionnelController {
	private final ProfilNutritionnelService profilNutritionnelService;

	public ProfilNutritionnelController(ProfilNutritionnelService profilNutritionnelService) {
		this.profilNutritionnelService = profilNutritionnelService;
	}

	@PostMapping
	public ResponseEntity<ProfilNutritionnelResponse> create(@Valid @RequestBody ProfilNutritionnelResquest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(profilNutritionnelService.create(request));
	}

	@GetMapping
	public ResponseEntity<List<ProfilNutritionnelResponse>> findAll() {
		return ResponseEntity.ok(profilNutritionnelService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProfilNutritionnelResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok(profilNutritionnelService.findById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProfilNutritionnelResponse> update(@PathVariable Long id,
			@Valid @RequestBody ProfilNutritionnelResquest request) {
		return ResponseEntity.ok(profilNutritionnelService.update(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		profilNutritionnelService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
