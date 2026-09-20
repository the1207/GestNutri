package com.gestNutri.config;

import com.gestNutri.entities.MatierePremiere;
import com.gestNutri.repository.MatierePremiereRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MatierePremiereDataInitializer {

    @Bean
    public CommandLineRunner initMatieresPremieres(MatierePremiereRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }

            List<MatierePremiere> matieres = List.of(
                creer("Mais jaune", 85.0, 2.2, 4.2, 3300.0, 8.5, 0.24, 0.2, 0.38, 0.02, 0.3, 0.01, 150.0, 0.0, 60.0),
                creer("Son de ble", 86.0, 9.6, 4.5, 1440.0, 15.99, 0.58, 0.23, 0.45, 0.14, 1.3, 0.05, 120.0, 0.0, 15.0),
                creer("Tourteau de soja", 90.3, 7.4, 1.7, 2420.0, 43.0, 2.7, 0.6, 1.3, 0.34, 0.7, 0.01, 350.0, 0.0, 25.0),
                creer("Tourteau d'arachide", 89.6, 6.8, 3.4, 2410.0, 49.0, 1.7, 0.49, 1.18, 0.16, 0.6, 0.02, 300.0, 0.0, 20.0),
                creer("Tourteau de coton", 93.5, 14.6, 4.6, 2368.0, 42.5, 1.72, 0.59, 1.24, 0.21, 0.05, 0.0, 250.0, 0.0, 10.0),
                creer("Farine de poisson", 92.0, 0.0, 4.11, 2820.0, 46.0, 3.5, 1.3, 1.85, 2.6, 0.9, 0.0, 800.0, 0.0, 8.0),
                creer("Cossette de manioc", 85.0, 3.0, 0.0, 3300.0, 2.2, 0.07, 0.03, 0.05, 0.2, 0.15, 0.03, 100.0, 0.0, 15.0),
                creer("Dreche", 89.0, 15.3, 0.0, 2400.0, 25.0, 0.7, 0.35, 0.6, 0.3, 0.5, 0.3, 130.0, 0.0, 15.0),
                creer("Tourteau de palmiste", 91.1, 15.0, 10.0, 2450.0, 16.0, 0.6, 0.3, 0.65, 0.3, 0.6, 0.02, 140.0, 0.0, 15.0),
                creer("Coquille d'huitre", 100.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 38.0, 0.05, 0.0, 80.0, 0.0, 8.0),
                creer("Lysine", 98.0, 0.0, 0.0, 0.0, 78.4, 78.4, 0.0, 0.0, 0.0, 0.0, 0.0, 2500.0, 0.0, 0.3),
                creer("Methionine", 100.0, 0.0, 0.0, 0.0, 99.0, 0.0, 99.0, 0.0, 0.0, 0.0, 0.0, 3000.0, 0.0, 0.3),
                creer("Phosphate bicalcique", 92.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 24.0, 18.0, 0.0, 400.0, 0.0, 2.0),
                creer("Nacl", 95.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 39.0, 200.0, 0.0, 0.5),
                creer("Soja graine", 89.6, 0.0, 17.9, 3900.0, 37.0, 2.35, 0.52, 1.15, 0.25, 0.57, 0.01, 400.0, 0.0, 15.0),
                creer("Son de mais", 89.9, 0.0, 0.0, 0.0, 10.1, 0.25, 0.15, 0.36, 0.03, 0.23, 0.0, 90.0, 0.0, 15.0),
                creer("Son de riz", 90.0, 0.0, 15.5, 2950.0, 9.7, 0.56, 0.22, 0.42, 0.07, 1.4, 0.05, 100.0, 0.0, 15.0)
            );

            repository.saveAll(matieres);
            System.out.println(matieres.size() + " matieres premieres inserees.");
        };
    }

    private MatierePremiere creer(String nom, double ms, double cb, double mg, double em, double pb,
                                  double lys, double met, double aas, double ca, double p, double na,
                                  double prix, double tauxMin, double tauxMax) {
        MatierePremiere matiere = new MatierePremiere();
        matiere.setNom(nom);
        matiere.setMatiereSeche(ms);
        matiere.setCelluloseBrute(cb);
        matiere.setMatieresGrasses(mg);
        matiere.setEnergieMetabolisable(em);
        matiere.setProteinesBrutes(pb);
        matiere.setLysine(lys);
        matiere.setMethionine(met);
        matiere.setAas(aas);
        matiere.setCalcium(ca);
        matiere.setPhosphore(p);
        matiere.setSodium(na);
        matiere.setPrixUnitaire(prix);
        matiere.setTauxIncorporationMin(tauxMin);
        matiere.setTauxIncorporationMax(tauxMax);
        matiere.setDisponible(true);
        return matiere;
    }
}
