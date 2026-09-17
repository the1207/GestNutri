package com.gestNutri.dto.resquest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record MoteurOptimisationResquest(
		@NotNull List<Long> matieresPremieresIds,
		@NotNull Long profilNutritionnelId,
		@NotNull @Positive Double quantiteTotale) {
}
