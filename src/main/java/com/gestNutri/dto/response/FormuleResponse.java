package com.gestNutri.dto.response;

import java.time.LocalDate;
import java.util.List;

public record FormuleResponse(
	Long id,
	LocalDate dateCreation,
	String auteur,
	Double quantiteTotale,
	Double coutTotal,
	Double coutParKg,
	String statut,
	List<LigneFormuleResponse> lignes,
	List<ResultatAnalyseResponse> resultats) {
}
