package com.gestNutri.dto.resquest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LigneFormuleResquest(
	@NotNull @Positive Double quantiteKg,
	@NotNull @Positive Double pourcentage,
	@NotNull Long formuleId,
	@NotNull Long matierePremiereId) {
}
