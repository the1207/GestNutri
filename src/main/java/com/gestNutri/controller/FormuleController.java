package com.gestNutri.controller;

import com.gestNutri.dto.response.FormuleResponse;
import com.gestNutri.service.FormuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/formules")
public class FormuleController {
	private final FormuleService formuleService;

	public FormuleController(FormuleService formuleService) {
		this.formuleService = formuleService;
	}

	@GetMapping
	public ResponseEntity<List<FormuleResponse>> findAll() {
		return ResponseEntity.ok(formuleService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<FormuleResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok(formuleService.findById(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		formuleService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/{id}/export/csv", produces = "text/csv")
	public ResponseEntity<byte[]> exportCsv(@PathVariable Long id) {
		FormuleResponse formule = formuleService.findById(id);
		StringBuilder csv = new StringBuilder("Type;Nom;Quantite kg;Pourcentage;Valeur obtenue;Cible min;Cible max;Conforme\n");
		for (var ligne : formule.lignes()) {
			csv.append("Ligne;").append(csv(ligne.matierePremiereNom())).append(';')
					.append(ligne.quantiteKg()).append(';').append(ligne.pourcentage())
					.append(";;;;\n");
		}
		for (var resultat : formule.resultats()) {
			csv.append("Analyse;").append(csv(resultat.nomNutriment())).append(";;;")
					.append(resultat.valeurObtenue()).append(';').append(resultat.valeurCible()).append(';')
					.append(resultat.valeurCibleMax()).append(';').append(resultat.conforme()).append('\n');
		}

		return fichier(csv.toString().getBytes(StandardCharsets.UTF_8), "text/csv", "formule-" + id + ".csv");
	}

	@GetMapping(value = "/{id}/export/pdf", produces = "application/pdf")
	public ResponseEntity<byte[]> exportPdf(@PathVariable Long id) {
		FormuleResponse formule = formuleService.findById(id);
		StringBuilder texte = new StringBuilder()
				.append("Formule ").append(formule.id()).append("\n")
				.append("Date: ").append(formule.dateCreation()).append("\n")
				.append("Auteur: ").append(formule.auteur()).append("\n")
				.append("Quantite totale: ").append(formule.quantiteTotale()).append(" kg\n")
				.append("Cout total: ").append(formule.coutTotal()).append("\n")
				.append("Cout par kg: ").append(formule.coutParKg()).append("\n")
				.append("Statut: ").append(formule.statut()).append("\n\nComposition\n");
		for (var ligne : formule.lignes()) {
			texte.append(ligne.matierePremiereNom()).append(" - ").append(ligne.quantiteKg())
					.append(" kg (").append(ligne.pourcentage()).append("%)\n");
		}
		texte.append("\nAnalyse nutritionnelle\n");
		for (var resultat : formule.resultats()) {
			texte.append(resultat.nomNutriment()).append(" - obtenu: ").append(resultat.valeurObtenue())
					.append(", cible: ").append(resultat.valeurCible()).append(" a ")
					.append(resultat.valeurCibleMax()).append(", conforme: ").append(resultat.conforme()).append('\n');
		}

		return fichier(pdf(texte.toString()), MediaType.APPLICATION_PDF_VALUE, "formule-" + id + ".pdf");
	}

	private ResponseEntity<byte[]> fichier(byte[] contenu, String type, String nom) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(type));
		headers.setContentDisposition(ContentDisposition.attachment().filename(nom).build());
		return ResponseEntity.ok().headers(headers).body(contenu);
	}

	private String csv(String valeur) {
		return '"' + valeur.replace("\"", "\"\"") + '"';
	}

	private byte[] pdf(String texte) {
		String[] lignes = texte.replaceAll("[^\\x20-\\x7E\\n]", "?").split("\\n");
		StringBuilder contenu = new StringBuilder("BT /F1 10 Tf 50 780 Td");
		for (String ligne : lignes) {
			contenu.append(" (").append(ligne.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)"))
					.append(") Tj 0 -14 Td");
		}
		contenu.append(" ET");
		String flux = contenu.toString();
		String[] objets = {
				"<< /Type /Catalog /Pages 2 0 R >>",
				"<< /Type /Pages /Kids [3 0 R] /Count 1 >>",
				"<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] /Resources << /Font << /F1 5 0 R >> >> /Contents 4 0 R >>",
				"<< /Length " + flux.getBytes(StandardCharsets.ISO_8859_1).length + " >>\nstream\n" + flux + "\nendstream",
				"<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>"
		};
		StringBuilder pdf = new StringBuilder("%PDF-1.4\n");
		int[] offsets = new int[objets.length + 1];
		for (int index = 0; index < objets.length; index++) {
			offsets[index + 1] = pdf.toString().getBytes(StandardCharsets.ISO_8859_1).length;
			pdf.append(index + 1).append(" 0 obj\n").append(objets[index]).append("\nendobj\n");
		}
		int xref = pdf.toString().getBytes(StandardCharsets.ISO_8859_1).length;
		pdf.append("xref\n0 ").append(objets.length + 1).append("\n0000000000 65535 f \n");
		for (int offset : offsets) {
			if (offset == 0) continue;
			pdf.append(String.format("%010d 00000 n \n", offset));
		}
		pdf.append("trailer\n<< /Size ").append(objets.length + 1).append(" /Root 1 0 R >>\nstartxref\n")
				.append(xref).append("\n%%EOF");
		return pdf.toString().getBytes(StandardCharsets.ISO_8859_1);
	}
}
