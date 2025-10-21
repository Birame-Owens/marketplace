package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lignes_panier")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LignesPanier implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ligne_panier")
    private Long idLignePanier;
    
    @Column(nullable = false)
    private Integer quantite = 1;
    
    @Column(name = "prix_unitaire", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixUnitaire;
    
    @Column(name = "date_ajout")
    private LocalDateTime dateAjout;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_panier", nullable = false)
    private Paniers panier;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit")
    private Produits produit;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_variante")
    private VariantesProduits variante;
    
    @PrePersist
    protected void onCreate() {
        dateAjout = LocalDateTime.now();
        if (quantite == null) quantite = 1;
    }
}