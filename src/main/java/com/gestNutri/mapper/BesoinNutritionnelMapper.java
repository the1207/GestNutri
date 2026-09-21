package com.gestNutri.mapper;

import com.gestNutri.dto.response.BesoinNutritionnelResponse;
import com.gestNutri.entities.BesoinNutritionnel;
import org.springframework.stereotype.Component;

@Component
public class BesoinNutritionnelMapper {
	public BesoinNutritionnelResponse toResponse(BesoinNutritionnel besoin) {
		return new BesoinNutritionnelResponse(
				besoin.getId(),
				besoin.getNomNutriment(),
				besoin.getValeurMin(),
				besoin.getValeurMax());
	}
}
