package com.gestNutri.service.implementation;

import com.gestNutri.dto.response.LigneFormuleResponse;
import com.gestNutri.mapper.LigneFormuleMapper;
import com.gestNutri.repository.LigneFormuleRepository;
import com.gestNutri.service.LigneFormuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LigneFormuleImplementation implements LigneFormuleService {
	private final LigneFormuleRepository ligneFormuleRepository;
	private final LigneFormuleMapper ligneFormuleMapper;

	public LigneFormuleImplementation(LigneFormuleRepository ligneFormuleRepository,
			LigneFormuleMapper ligneFormuleMapper) {
		this.ligneFormuleRepository = ligneFormuleRepository;
		this.ligneFormuleMapper = ligneFormuleMapper;
	}

	@Override
	@Transactional(readOnly = true)
	public List<LigneFormuleResponse> findByFormuleId(Long formuleId) {
		return ligneFormuleRepository.findByFormuleId(formuleId).stream()
				.map(ligneFormuleMapper::toResponse)
				.toList();
	}
}
