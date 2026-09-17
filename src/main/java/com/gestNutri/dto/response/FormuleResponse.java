package com.gestNutri.dto.response;

import java.time.LocalDate;

public record FormuleResponse(
	Long id,
	LocalDate dateCreation,
	String auteur,
	Double quantiteTotale,
	Double coutTotal,
	Double coutParKg,
	String statut) {
}
