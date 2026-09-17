package com.gestNutri.dto.resquest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BesoinNutritionnelResquest(
	@NotBlank(message = "Le nom du nutriment est obligatoire") String nomNutriment,
	@NotNull Double valeurMin,
	@NotNull Double valeurMax,
	@NotNull(message = "Le profil nutritionnel est obligatoire") Long profilNutritionnelId) {
}
