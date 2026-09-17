package com.gestNutri.dto.resquest;

import java.util.List;

public record MoteurOptimisationResquest(
	List<Long> matieresPremieresIds,
	Long profilNutritionnelId,
	double quantiteTotale) {
}
