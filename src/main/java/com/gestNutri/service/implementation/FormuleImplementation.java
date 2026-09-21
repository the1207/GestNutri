package com.gestNutri.service.implementation;

import com.gestNutri.dto.response.FormuleResponse;
import com.gestNutri.exception.ResourceNotFoundException;
import com.gestNutri.mapper.FormuleMapper;
import com.gestNutri.repository.FormuleRepository;
import com.gestNutri.service.FormuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FormuleImplementation implements FormuleService {
	private final FormuleRepository formuleRepository;
	private final FormuleMapper formuleMapper;

	public FormuleImplementation(FormuleRepository formuleRepository, FormuleMapper formuleMapper) {
		this.formuleRepository = formuleRepository;
		this.formuleMapper = formuleMapper;
	}

	@Override
	@Transactional(readOnly = true)
	public List<FormuleResponse> findAll() {
		return formuleRepository.findAll().stream().map(formuleMapper::toResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public FormuleResponse findById(Long id) {
		return formuleRepository.findById(id)
				.map(formuleMapper::toResponse)
				.orElseThrow(() -> new ResourceNotFoundException("Formule non trouvee."));
	}

	@Override
	public void delete(Long id) {
		if (!formuleRepository.existsById(id)) {
			throw new ResourceNotFoundException("Formule non trouvee.");
		}
		formuleRepository.deleteById(id);
	}
}
