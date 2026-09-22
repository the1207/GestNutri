package com.gestNutri.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BesoinNutritionnel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomNutriment;
    private Double valeurMin;
    private Double valeurMax;
}
