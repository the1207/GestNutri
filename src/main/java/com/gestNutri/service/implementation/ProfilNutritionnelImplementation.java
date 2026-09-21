package com.gestNutri.service.implementation;

import com.gestNutri.dto.resquest.ProfilNutritionnelResquest;
import com.gestNutri.dto.response.ProfilNutritionnelResponse;
import com.gestNutri.entities.ProfilNutritionnel;
import com.gestNutri.exception.ResourceNotFoundException;
import com.gestNutri.mapper.ProfilNutritionnelMapper;
import com.gestNutri.repository.ProfilNutritionnelRepository;
import com.gestNutri.service.ProfilNutritionnelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfilNutritionnelImplementation implements ProfilNutritionnelService {
	private final ProfilNutritionnelRepository profilNutritionnelRepository;
	private final ProfilNutritionnelMapper profilNutritionnelMapper;

	public ProfilNutritionnelImplementation(ProfilNutritionnelRepository profilNutritionnelRepository,
			ProfilNutritionnelMapper profilNutritionnelMapper) {
		this.profilNutritionnelRepository = profilNutritionnelRepository;
		this.profilNutritionnelMapper = profilNutritionnelMapper;
	}

	@Override
	public ProfilNutritionnelResponse create(ProfilNutritionnelResquest request) {
		ProfilNutritionnel profil = new ProfilNutritionnel();
		appliquer(profil, request);
		return profilNutritionnelMapper.toResponse(profilNutritionnelRepository.save(profil));
	}

	@Override
	public List<ProfilNutritionnelResponse> findAll() {
		return profilNutritionnelRepository.findAll().stream()
				.map(profilNutritionnelMapper::toResponse)
				.toList();
	}

	@Override
	public ProfilNutritionnelResponse findById(Long id) {
		return profilNutritionnelMapper.toResponse(trouver(id));
	}

	@Override
	public ProfilNutritionnelResponse update(Long id, ProfilNutritionnelResquest request) {
		ProfilNutritionnel profil = trouver(id);
		appliquer(profil, request);
		return profilNutritionnelMapper.toResponse(profilNutritionnelRepository.save(profil));
	}

	@Override
	public void delete(Long id) {
		if (!profilNutritionnelRepository.existsById(id)) {
			throw new ResourceNotFoundException("Profil nutritionnel non trouve.");
		}
		profilNutritionnelRepository.deleteById(id);
	}

	private ProfilNutritionnel trouver(Long id) {
		return profilNutritionnelRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Profil nutritionnel non trouve."));
	}

	private void appliquer(ProfilNutritionnel profil, ProfilNutritionnelResquest request) {
		profil.setNom(request.nomCategorie());
		profil.setStade(request.stade());
		profil.setEstPersonnalise(request.estPersonnalise());
	}
}
