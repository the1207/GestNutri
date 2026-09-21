package com.gestNutri.mapper;

import com.gestNutri.dto.response.BesoinNutritionnelResponse;
import com.gestNutri.dto.response.ProfilNutritionnelResponse;
import com.gestNutri.entities.ProfilNutritionnel;
import org.springframework.stereotype.Component;

@Component
public class ProfilNutritionnelMapper {
    public ProfilNutritionnelResponse toResponse(ProfilNutritionnel profil) {
	return new ProfilNutritionnelResponse(
		profil.getId(),
		profil.getNom(),
		profil.getStade(),
		profil.getEstPersonnalise(),
		profil.getBesoins().stream()
			.map(besoin -> new BesoinNutritionnelResponse(
				besoin.getId(),
				besoin.getNomNutriment(),
				besoin.getValeurMin(),
				besoin.getValeurMax()))
			.toList());
    }
}
