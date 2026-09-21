package com.gestNutri.controller;

import com.gestNutri.dto.resquest.MatierePremiereResquest;
import com.gestNutri.dto.response.MatierePremiereResponse;
import com.gestNutri.service.MatierePremiereService;
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
@RequestMapping("/api/matieres-premieres")
public class MatierePremiereController {
	private final MatierePremiereService matierePremiereService;

	public MatierePremiereController(MatierePremiereService matierePremiereService) {
		this.matierePremiereService = matierePremiereService;
	}

	@PostMapping
	public ResponseEntity<MatierePremiereResponse> create(@Valid @RequestBody MatierePremiereResquest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(matierePremiereService.create(request));
	}

	@GetMapping
	public ResponseEntity<List<MatierePremiereResponse>> findAll() {
		return ResponseEntity.ok(matierePremiereService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<MatierePremiereResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok(matierePremiereService.findById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<MatierePremiereResponse> update(@PathVariable Long id,
			@Valid @RequestBody MatierePremiereResquest request) {
		return ResponseEntity.ok(matierePremiereService.update(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		matierePremiereService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
