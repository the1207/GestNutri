package com.gestNutri.service.implementation;

import com.gestNutri.dto.resquest.UtilisateurResquest;
import com.gestNutri.dto.response.UtilisateurResponse;
import com.gestNutri.entities.Utilisateur;
import com.gestNutri.exception.ResourceNotFoundException;
import com.gestNutri.repository.UtilisateurRepository;
import com.gestNutri.service.UtilisateurService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurImplementation implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurImplementation(UtilisateurRepository utilisateurRepository,
                                      PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UtilisateurResponse create(UtilisateurResquest request) {
        if (utilisateurRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe deja.");
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(request.email());
        utilisateur.setMotDePasse(passwordEncoder.encode(request.motDePasse()));
        utilisateur.setRole(request.role());

        Utilisateur saved = utilisateurRepository.save(utilisateur);
        return new UtilisateurResponse(saved.getId(), saved.getEmail(), saved.getRole());
    }

    @Override
    public void delete(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utilisateur non trouve.");
        }
        utilisateurRepository.deleteById(id);
    }

    @Override
    public List<UtilisateurResponse> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(u -> new UtilisateurResponse(u.getId(), u.getEmail(), u.getRole()))
                .toList();
    }
}
