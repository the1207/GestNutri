package com.gestNutri.config;

import com.gestNutri.entities.Role;
import com.gestNutri.entities.Utilisateur;
import com.gestNutri.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminDataInitializer {

    private static final String ADMIN_EMAIL = "admin1@gestnutri.local";
    private static final String ADMIN_PASSWORD = "admin123";

    @Bean
    public CommandLineRunner initAdmin(
            UtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            if (utilisateurRepository.findByEmail(ADMIN_EMAIL).isPresent()) {
                return;
            }

            Utilisateur admin = new Utilisateur();
            admin.setEmail(ADMIN_EMAIL);
            admin.setMotDePasse(passwordEncoder.encode(ADMIN_PASSWORD));
            admin.setRole(Role.ADMINISTRATEUR);
            utilisateurRepository.save(admin);
        };
    }
}
