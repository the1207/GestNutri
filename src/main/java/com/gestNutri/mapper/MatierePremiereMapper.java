package com.gestNutri.mapper;

import com.gestNutri.dto.response.MatierePremiereResponse;
import com.gestNutri.entities.MatierePremiere;
import org.springframework.stereotype.Component;

@Component
public class MatierePremiereMapper {
	public MatierePremiereResponse toResponse(MatierePremiere matiere) {
		return new MatierePremiereResponse(
				matiere.getId(),
				matiere.getNom(),
				matiere.getMatiereSeche(),
				matiere.getCelluloseBrute(),
				matiere.getMatieresGrasses(),
				matiere.getEnergieMetabolisable(),
				matiere.getProteinesBrutes(),
				matiere.getLysine(),
				matiere.getMethionine(),
				matiere.getAas(),
				matiere.getCalcium(),
				matiere.getPhosphore(),
				matiere.getSodium(),
				matiere.getPrixUnitaire(),
				matiere.getTauxIncorporationMin(),
				matiere.getTauxIncorporationMax(),
				matiere.getDisponible());
	}
}
