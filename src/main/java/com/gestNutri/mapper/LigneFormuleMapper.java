package com.gestNutri.mapper;

import com.gestNutri.dto.response.LigneFormuleResponse;
import com.gestNutri.entities.LigneFormule;
import org.springframework.stereotype.Component;

@Component
public class LigneFormuleMapper {
	public LigneFormuleResponse toResponse(LigneFormule ligne) {
		return new LigneFormuleResponse(
				ligne.getId(),
				ligne.getQuantiteKg(),
				ligne.getPourcentage(),
				ligne.getMatierePremiere().getId(),
				ligne.getMatierePremiere().getNom());
	}
}
