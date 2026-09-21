export interface LigneFormuleResponse {
  id: number;
  quantiteKg: number;
  pourcentage: number;
  matierePremiereId: number;
  matierePremiereNom: string;
}

export interface ResultatAnalyseResponse {
  id: number;
  nomNutriment: string;
  valeurObtenue: number;
  valeurCible: number;
  valeurCibleMax: number;
  conforme: boolean;
}

export interface FormuleResponse {
  id: number;
  dateCreation: string;
  auteur: string;
  quantiteTotale: number;
  coutTotal: number;
  coutParKg: number;
  statut: string;
  lignes: LigneFormuleResponse[];
  resultats: ResultatAnalyseResponse[];
}
