package com.gestNutri.dto.resquest;

import com.gestNutri.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UtilisateurResquest(
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caracteres") String motDePasse,
        @NotNull Role role
) {
}
