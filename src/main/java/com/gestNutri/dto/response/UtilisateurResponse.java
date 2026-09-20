package com.gestNutri.dto.response;

import com.gestNutri.entities.Role;

public record UtilisateurResponse(
        Long id,
        String email,
        Role role
) {
}
