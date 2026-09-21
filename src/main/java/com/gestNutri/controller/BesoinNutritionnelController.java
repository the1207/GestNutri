package com.gestNutri.controller;

import com.gestNutri.dto.resquest.BesoinNutritionnelResquest;
import com.gestNutri.dto.response.BesoinNutritionnelResponse;
import com.gestNutri.service.BesoinNutritionnelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/besoins-nutritionnels")
public class BesoinNutritionnelController {
	private final BesoinNutritionnelService besoinNutritionnelService;

	public BesoinNutritionnelController(BesoinNutritionnelService besoinNutritionnelService) {
		this.besoinNutritionnelService = besoinNutritionnelService;
	}

	@PostMapping
	public ResponseEntity<BesoinNutritionnelResponse> create(@Valid @RequestBody BesoinNutritionnelResquest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(besoinNutritionnelService.create(request));
	}

	@GetMapping
	public ResponseEntity<List<BesoinNutritionnelResponse>> findAll() {
		return ResponseEntity.ok(besoinNutritionnelService.findAll());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		besoinNutritionnelService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
