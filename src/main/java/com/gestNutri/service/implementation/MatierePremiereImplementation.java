package com.gestNutri.service.implementation;

import com.gestNutri.dto.resquest.MatierePremiereResquest;
import com.gestNutri.dto.response.MatierePremiereResponse;
import com.gestNutri.entities.MatierePremiere;
import com.gestNutri.exception.ResourceNotFoundException;
import com.gestNutri.mapper.MatierePremiereMapper;
import com.gestNutri.repository.MatierePremiereRepository;
import com.gestNutri.service.MatierePremiereService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatierePremiereImplementation implements MatierePremiereService {
	private final MatierePremiereRepository matierePremiereRepository;
	private final MatierePremiereMapper matierePremiereMapper;

	public MatierePremiereImplementation(MatierePremiereRepository matierePremiereRepository,
			MatierePremiereMapper matierePremiereMapper) {
		this.matierePremiereRepository = matierePremiereRepository;
		this.matierePremiereMapper = matierePremiereMapper;
	}

	@Override
	public MatierePremiereResponse create(MatierePremiereResquest request) {
		MatierePremiere matiere = new MatierePremiere();
		appliquer(matiere, request);
		return matierePremiereMapper.toResponse(matierePremiereRepository.save(matiere));
	}

	@Override
	public List<MatierePremiereResponse> findAll() {
		return matierePremiereRepository.findAll().stream()
				.map(matierePremiereMapper::toResponse)
				.toList();
	}

	@Override
	public MatierePremiereResponse findById(Long id) {
		return matierePremiereMapper.toResponse(trouver(id));
	}

	@Override
	public MatierePremiereResponse update(Long id, MatierePremiereResquest request) {
		MatierePremiere matiere = trouver(id);
		appliquer(matiere, request);
		return matierePremiereMapper.toResponse(matierePremiereRepository.save(matiere));
	}

	@Override
	public void delete(Long id) {
		if (!matierePremiereRepository.existsById(id)) {
			throw new ResourceNotFoundException("Matiere premiere non trouvee.");
		}
		matierePremiereRepository.deleteById(id);
	}

	private MatierePremiere trouver(Long id) {
		return matierePremiereRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Matiere premiere non trouvee."));
	}

	private void appliquer(MatierePremiere matiere, MatierePremiereResquest request) {
		matiere.setNom(request.nom());
		matiere.setMatiereSeche(request.matiereSeche());
		matiere.setCelluloseBrute(request.celluloseBrute());
		matiere.setMatieresGrasses(request.matieresGrasses());
		matiere.setEnergieMetabolisable(request.energieMetabolisable());
		matiere.setProteinesBrutes(request.proteinesBrutes());
		matiere.setLysine(request.lysine());
		matiere.setMethionine(request.methionine());
		matiere.setAas(request.aas());
		matiere.setCalcium(request.calcium());
		matiere.setPhosphore(request.phosphore());
		matiere.setSodium(request.sodium());
		matiere.setPrixUnitaire(request.prixUnitaire());
		matiere.setTauxIncorporationMin(request.tauxIncorporationMin());
		matiere.setTauxIncorporationMax(request.tauxIncorporationMax());
		matiere.setDisponible(request.disponible());
	}
}
