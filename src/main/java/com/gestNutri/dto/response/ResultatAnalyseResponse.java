package com.gestNutri.dto.response;

public record ResultatAnalyseResponse(
	Long id,
	String nomNutriment,
	Double valeurObtenue,
	Double valeurCible,
	Double valeurCibleMax,
	Boolean conforme) {
}
