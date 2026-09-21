package com.gestNutri.dto.response;

public record MatierePremiereResponse(
	Long id,
	String nom,
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
	Double prixUnitaire,
	Double tauxIncorporationMin,
	Double tauxIncorporationMax,
	Boolean disponible) {
}
