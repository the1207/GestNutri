package com.gestNutri.dto.response;

public record LigneFormuleResponse(
	Long id,
	Double quantiteKg,
	Double pourcentage,
	Long matierePremiereId,
	String matierePremiereNom) {
}
