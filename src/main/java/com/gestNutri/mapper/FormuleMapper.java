package com.gestNutri.mapper;

import com.gestNutri.dto.response.FormuleResponse;
import com.gestNutri.entities.Formule;
import org.springframework.stereotype.Component;

@Component
public class FormuleMapper {
	public FormuleResponse toResponse(Formule formule) {
		return new FormuleResponse(
				formule.getId(),
				formule.getDateCreation(),
				formule.getAuteur(),
				formule.getQuantiteTotale(),
				formule.getCoutTotal(),
				formule.getCoutParKg(),
				formule.getStatut());
	}
}
