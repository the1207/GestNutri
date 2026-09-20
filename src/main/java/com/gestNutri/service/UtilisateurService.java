package com.gestNutri.service;

import com.gestNutri.dto.resquest.UtilisateurResquest;
import com.gestNutri.dto.response.UtilisateurResponse;

import java.util.List;

public interface UtilisateurService {

    UtilisateurResponse create(UtilisateurResquest request);

    void delete(Long id);

    List<UtilisateurResponse> findAll();
}
