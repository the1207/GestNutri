package com.gestNutri.dto.resquest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record MatierePremiereResquest(
	@NotBlank(message = "Le nom est obligatoire") String nom,
	Double matiereSeche,
	Double celluloseBrute,
	Double matieresGrasses,
	Double energieMetabolisable,
	Double proteinesBrutes,
	Double lysine,
	Double methionine,
	Double aas,
	Double calcium,
	Double phosphore,
	Double sodium,
	@NotNull(message = "Le prix unitaire est obligatoire")
	@PositiveOrZero(message = "Le prix unitaire doit etre positif ou nul")
	Double prixUnitaire,
	@NotNull @PositiveOrZero Double tauxIncorporationMin,
	@NotNull @PositiveOrZero Double tauxIncorporationMax,
	@NotNull Boolean disponible) {
}
