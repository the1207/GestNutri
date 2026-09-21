package com.gestNutri.mapper;

import com.gestNutri.dto.response.ResultatAnalyseResponse;
import com.gestNutri.entities.ResultatAnalyse;
import org.springframework.stereotype.Component;

@Component
public class ResultatAnalyseMapper {
	public ResultatAnalyseResponse toResponse(ResultatAnalyse resultat) {
		return new ResultatAnalyseResponse(
				resultat.getId(),
				resultat.getNomNutriment(),
				resultat.getValeurObtenue(),
				resultat.getValeurCible(),
				resultat.getValeurCibleMax(),
				resultat.getConforme());
	}
}
