package com.gestNutri.service.implementation;

import com.gestNutri.dto.resquest.MoteurOptimisationResquest;
import com.gestNutri.dto.response.MoteurOptimisationResponse;
import com.gestNutri.entities.BesoinNutritionnel;
import com.gestNutri.entities.Formule;
import com.gestNutri.entities.LigneFormule;
import com.gestNutri.entities.MatierePremiere;
import com.gestNutri.entities.ProfilNutritionnel;
import com.gestNutri.entities.ResultatAnalyse;
import com.gestNutri.mapper.FormuleMapper;
import com.gestNutri.repository.FormuleRepository;
import com.gestNutri.repository.MatierePremiereRepository;
import com.gestNutri.repository.ProfilNutritionnelRepository;
import com.gestNutri.service.MoteurOptimisationService;
import com.gestNutri.util.NutrientType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.NoFeasibleSolutionException;
import org.apache.commons.math3.optim.linear.NonNegativeConstraint;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.springframework.stereotype.Service;

@Service
public class MoteurOptimisationImplementation implements MoteurOptimisationService {
	private final MatierePremiereRepository matierePremiereRepository;
	private final ProfilNutritionnelRepository profilNutritionnelRepository;
	private final FormuleRepository formuleRepository;
	private final FormuleMapper formuleMapper;

	public MoteurOptimisationImplementation(MatierePremiereRepository matierePremiereRepository,
			ProfilNutritionnelRepository profilNutritionnelRepository, FormuleRepository formuleRepository,
			FormuleMapper formuleMapper) {
		this.matierePremiereRepository = matierePremiereRepository;
		this.profilNutritionnelRepository = profilNutritionnelRepository;
		this.formuleRepository = formuleRepository;
		this.formuleMapper = formuleMapper;
	}

	@Override
	public MoteurOptimisationResponse resoudre(MoteurOptimisationResquest request) {
		if (request == null || request.matieresPremieresIds() == null || request.matieresPremieresIds().isEmpty()
				|| request.profilNutritionnelId() == null || request.quantiteTotale() <= 0) {
			return new MoteurOptimisationResponse(null, false,
					"La requete doit contenir des matieres, un profil et une quantite totale positive.");
		}

		List<MatierePremiere> matieres = matierePremiereRepository.findAllById(request.matieresPremieresIds());
		if (matieres.size() != new HashSet<>(request.matieresPremieresIds()).size()) {
			return new MoteurOptimisationResponse(null, false,
					"Une ou plusieurs matieres premieres sont introuvables.");
		}
		ProfilNutritionnel profil = profilNutritionnelRepository.findById(request.profilNutritionnelId()).orElse(null);
		if (profil == null) {
			return new MoteurOptimisationResponse(null, false, "Profil nutritionnel non trouve.");
		}

		double quantiteTotale = request.quantiteTotale();
		int nombreMatieres = matieres.size();
		double[] couts = new double[nombreMatieres];
		List<LinearConstraint> contraintes = new ArrayList<>();
		double[] somme = new double[nombreMatieres];
		for (int i = 0; i < nombreMatieres; i++) {
			MatierePremiere matiere = matieres.get(i);
			couts[i] = valeur(matiere.getPrixUnitaire());
			somme[i] = 1.0;
			double[] ligne = new double[nombreMatieres];
			ligne[i] = 1.0;
			contraintes.add(new LinearConstraint(ligne, Relationship.GEQ,
					valeur(matiere.getTauxIncorporationMin()) / 100.0 * quantiteTotale));
			contraintes.add(new LinearConstraint(ligne, Relationship.LEQ,
					valeur(matiere.getTauxIncorporationMax()) / 100.0 * quantiteTotale));
		}
		contraintes.add(new LinearConstraint(somme, Relationship.EQ, quantiteTotale));

		for (BesoinNutritionnel besoin : profil.getBesoins()) {
			NutrientType type = typePour(besoin.getNomNutriment());
			if (type == null) {
				continue;
			}
			double[] ligne = new double[nombreMatieres];
			for (int i = 0; i < nombreMatieres; i++) {
				double valeur = type.valeurPour(matieres.get(i));
				ligne[i] = type.estPourcentage() ? valeur / 100.0 : valeur;
			}
			double minimum = valeur(besoin.getValeurMin());
			double maximum = valeur(besoin.getValeurMax());
			if (type.estPourcentage()) {
				minimum = minimum / 100.0 * quantiteTotale;
				maximum = maximum / 100.0 * quantiteTotale;
			} else {
				minimum *= quantiteTotale;
				maximum *= quantiteTotale;
			}
			contraintes.add(new LinearConstraint(ligne, Relationship.GEQ, minimum));
			contraintes.add(new LinearConstraint(ligne, Relationship.LEQ, maximum));
		}

		try {
			PointValuePair solution = new SimplexSolver().optimize(
					new LinearObjectiveFunction(couts, 0), new LinearConstraintSet(contraintes), GoalType.MINIMIZE,
					new NonNegativeConstraint(true));
			Formule formule = construireFormule(request, profil, matieres, solution);
			Formule sauvegardee = formuleRepository.save(formule);
			return new MoteurOptimisationResponse(formuleMapper.toResponse(sauvegardee), true, null);
		} catch (NoFeasibleSolutionException | org.apache.commons.math3.optim.linear.UnboundedSolutionException e) {
			return new MoteurOptimisationResponse(null, false,
					"Aucune combinaison ne respecte simultanement toutes les contraintes.");
		}
	}

	private Formule construireFormule(MoteurOptimisationResquest request, ProfilNutritionnel profil,
			List<MatierePremiere> matieres, PointValuePair solution) {
		double[] quantites = solution.getPoint();
		double quantiteTotale = request.quantiteTotale();
		Formule formule = new Formule();
		formule.setDateCreation(LocalDate.now());
		formule.setAuteur("systeme");
		formule.setQuantiteTotale(quantiteTotale);
		formule.setCoutTotal(solution.getValue());
		formule.setCoutParKg(solution.getValue() / quantiteTotale);
		formule.setStatut("CALCULEE");
		formule.setProfilNutritionnel(profil);

		for (int i = 0; i < matieres.size(); i++) {
			if (quantites[i] < 0.0001) {
				continue;
			}
			LigneFormule ligne = new LigneFormule();
			ligne.setQuantiteKg(quantites[i]);
			ligne.setPourcentage(quantites[i] / quantiteTotale * 100.0);
			ligne.setFormule(formule);
			ligne.setMatierePremiere(matieres.get(i));
			formule.getLignes().add(ligne);
		}

		for (BesoinNutritionnel besoin : profil.getBesoins()) {
			NutrientType type = typePour(besoin.getNomNutriment());
			if (type == null) {
				continue;
			}
			double total = 0;
			for (int i = 0; i < matieres.size(); i++) {
				double valeur = type.valeurPour(matieres.get(i));
				total += quantites[i] * (type.estPourcentage() ? valeur / 100.0 : valeur);
			}
			double obtenu = type.estPourcentage() ? total / quantiteTotale * 100.0 : total / quantiteTotale;
			ResultatAnalyse resultat = new ResultatAnalyse();
			resultat.setNomNutriment(besoin.getNomNutriment());
			resultat.setValeurObtenue(obtenu);
			resultat.setValeurCible(besoin.getValeurMin());
			resultat.setValeurCibleMax(besoin.getValeurMax());
			resultat.setConforme(obtenu >= valeur(besoin.getValeurMin())
					&& obtenu <= valeur(besoin.getValeurMax()));
			resultat.setFormule(formule);
			formule.getResultats().add(resultat);
		}
		return formule;
	}

	private NutrientType typePour(String nom) {
		if (nom == null) {
			return null;
		}
		try {
			return NutrientType.valueOf(nom.trim().toUpperCase());
		} catch (IllegalArgumentException exception) {
			return null;
		}
	}

	private double valeur(Double valeur) {
		return valeur == null ? 0.0 : valeur;
	}
}
