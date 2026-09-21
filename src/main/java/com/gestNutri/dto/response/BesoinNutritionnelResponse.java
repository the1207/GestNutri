package com.gestNutri.dto.response;

public record BesoinNutritionnelResponse(
	Long id,
	String nomNutriment,
	Double valeurMin,
	Double valeurMax) {
}
