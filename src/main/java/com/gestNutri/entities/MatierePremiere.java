package com.gestNutri.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MatierePremiere {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private Double prixUnitaire;
	private Double tauxIncorporationMin;
	private Double tauxIncorporationMax;
	private Double proteinesBrutes;
	private Double lysine;
	private Double methionine;
	private Double calcium;
	private Double phosphore;
	private Double sodium;
	private Double matieresGrasses;
	private Double celluloseBrute;
	private Double energieMetabolisable;
}
