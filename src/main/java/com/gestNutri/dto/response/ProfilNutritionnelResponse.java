package com.gestNutri.dto.response;

import java.util.List;

public record ProfilNutritionnelResponse(
	Long id,
	String nomCategorie,
	String stade,
	Boolean estPersonnalise,
	List<BesoinNutritionnelResponse> besoins) {
}
