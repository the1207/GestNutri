package com.gestNutri.dto.resquest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProfilNutritionnelResquest(
	@NotBlank String nomCategorie,
	@NotBlank String stade,
	@NotNull Boolean estPersonnalise) {
}
