package com.gestNutri.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Formule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate dateCreation;
	private String auteur;
	private Double quantiteTotale;
	private Double coutTotal;
	private Double coutParKg;
	private String statut;

	@ManyToOne
	private ProfilNutritionnel profilNutritionnel;

	@OneToMany(mappedBy = "formule", cascade = CascadeType.ALL)
	private List<LigneFormule> lignes = new ArrayList<>();

	@OneToMany(mappedBy = "formule", cascade = CascadeType.ALL)
	private List<ResultatAnalyse> resultats = new ArrayList<>();
}
