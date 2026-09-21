package com.gestNutri.service.implementation;

import com.gestNutri.dto.resquest.BesoinNutritionnelResquest;
import com.gestNutri.dto.response.BesoinNutritionnelResponse;
import com.gestNutri.entities.BesoinNutritionnel;
import com.gestNutri.entities.ProfilNutritionnel;
import com.gestNutri.exception.ResourceNotFoundException;
import com.gestNutri.mapper.BesoinNutritionnelMapper;
import com.gestNutri.repository.BesoinNutritionnelRepository;
import com.gestNutri.repository.ProfilNutritionnelRepository;
import com.gestNutri.service.BesoinNutritionnelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BesoinNutritionnelImplementation implements BesoinNutritionnelService {
	private final BesoinNutritionnelRepository besoinNutritionnelRepository;
	private final ProfilNutritionnelRepository profilNutritionnelRepository;
	private final BesoinNutritionnelMapper besoinNutritionnelMapper;

	public BesoinNutritionnelImplementation(BesoinNutritionnelRepository besoinNutritionnelRepository,
			ProfilNutritionnelRepository profilNutritionnelRepository,
			BesoinNutritionnelMapper besoinNutritionnelMapper) {
		this.besoinNutritionnelRepository = besoinNutritionnelRepository;
		this.profilNutritionnelRepository = profilNutritionnelRepository;
		this.besoinNutritionnelMapper = besoinNutritionnelMapper;
	}

	@Override
	@Transactional
	public BesoinNutritionnelResponse create(BesoinNutritionnelResquest request) {
		ProfilNutritionnel profil = profilNutritionnelRepository.findById(request.profilNutritionnelId())
				.orElseThrow(() -> new ResourceNotFoundException("Profil nutritionnel non trouve."));

		BesoinNutritionnel besoin = new BesoinNutritionnel();
		besoin.setNomNutriment(request.nomNutriment());
		besoin.setValeurMin(request.valeurMin());
		besoin.setValeurMax(request.valeurMax());
		profil.getBesoins().add(besoin);
		profilNutritionnelRepository.save(profil);

		return besoinNutritionnelMapper.toResponse(besoin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BesoinNutritionnelResponse> findAll() {
		return besoinNutritionnelRepository.findAll().stream()
				.map(besoinNutritionnelMapper::toResponse)
				.toList();
	}

	@Override
	public void delete(Long id) {
		if (!besoinNutritionnelRepository.existsById(id)) {
			throw new ResourceNotFoundException("Besoin nutritionnel non trouve.");
		}
		besoinNutritionnelRepository.deleteById(id);
	}
}
