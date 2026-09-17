package com.gestNutri.util;

import com.gestNutri.entities.MatierePremiere;
import java.util.function.Function;

public enum NutrientType {
    PROTEINES_BRUTES(MatierePremiere::getProteinesBrutes, true),
    LYSINE(MatierePremiere::getLysine, true),
    METHIONINE(MatierePremiere::getMethionine, true),
    CALCIUM(MatierePremiere::getCalcium, true),
    PHOSPHORE(MatierePremiere::getPhosphore, true),
    SODIUM(MatierePremiere::getSodium, true),
    MATIERES_GRASSES(MatierePremiere::getMatieresGrasses, true),
    CELLULOSE_BRUTE(MatierePremiere::getCelluloseBrute, true),
    ENERGIE_METABOLISABLE(MatierePremiere::getEnergieMetabolisable, false);

    private final Function<MatierePremiere, Double> extracteur;
    private final boolean pourcentage;

    NutrientType(Function<MatierePremiere, Double> extracteur, boolean pourcentage) {
        this.extracteur = extracteur;
        this.pourcentage = pourcentage;
    }

    public double valeurPour(MatierePremiere matierePremiere) {
        Double valeur = extracteur.apply(matierePremiere);
        return valeur == null ? 0.0 : valeur;
    }

    public boolean estPourcentage() {
        return pourcentage;
    }
}
