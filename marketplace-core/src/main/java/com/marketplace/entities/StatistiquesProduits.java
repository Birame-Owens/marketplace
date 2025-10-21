package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "statistiques_produits", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_produit", "date_statistique"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatistiquesProduits implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statistique_produit")
    private Long idStatistiqueProduit;
    
    @Column(name = "date_statistique", nullable = false)
    private LocalDate dateStatistique;
    
    @Column(name = "nombre_vues")
    private Integer nombreVues = 0;
    
    @Column(name = "nombre_ajouts_panier")
    private Integer nombreAjoutsPanier = 0;
    
    @Column(name = "nombre_ventes")
    private Integer nombreVentes = 0;
    
    @Column(name = "taux_conversion", precision = 5, scale = 2)
    private BigDecimal tauxConversion = BigDecimal.ZERO;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit", nullable = false)
    private Produits produit;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (nombreVues == null) nombreVues = 0;
        if (nombreAjoutsPanier == null) nombreAjoutsPanier = 0;
        if (nombreVentes == null) nombreVentes = 0;
        if (tauxConversion == null) tauxConversion = BigDecimal.ZERO;
    }
}