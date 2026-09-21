package com.gestNutri.mapper;

import com.gestNutri.dto.response.FormuleResponse;
import com.gestNutri.entities.Formule;
import org.springframework.stereotype.Component;

@Component
public class FormuleMapper {
	private final LigneFormuleMapper ligneFormuleMapper;
	private final ResultatAnalyseMapper resultatAnalyseMapper;

	public FormuleMapper(LigneFormuleMapper ligneFormuleMapper, ResultatAnalyseMapper resultatAnalyseMapper) {
		this.ligneFormuleMapper = ligneFormuleMapper;
		this.resultatAnalyseMapper = resultatAnalyseMapper;
	}

	public FormuleResponse toResponse(Formule formule) {
		return new FormuleResponse(
				formule.getId(),
				formule.getDateCreation(),
				formule.getAuteur(),
				formule.getQuantiteTotale(),
				formule.getCoutTotal(),
				formule.getCoutParKg(),
				formule.getStatut(),
				formule.getLignes().stream().map(ligneFormuleMapper::toResponse).toList(),
				formule.getResultats().stream().map(resultatAnalyseMapper::toResponse).toList());
	}
}
