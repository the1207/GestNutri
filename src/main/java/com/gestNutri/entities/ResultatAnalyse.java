package com.gestNutri.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class ResultatAnalyse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nomNutriment;
	private Double valeurObtenue;
	private Double valeurCible;
	private Double valeurCibleMax;
	private Boolean conforme;

	@ManyToOne
	private Formule formule;
}
