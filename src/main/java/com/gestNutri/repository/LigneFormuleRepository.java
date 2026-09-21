package com.gestNutri.repository;

import com.gestNutri.entities.LigneFormule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneFormuleRepository extends JpaRepository<LigneFormule, Long> {
	List<LigneFormule> findByFormuleId(Long formuleId);
}
