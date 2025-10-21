package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lignes_commande")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LignesCommande implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ligne_commande")
    private Long idLigneCommande;
    
    @Column(name = "nom_produit", nullable = false, length = 255)
    private String nomProduit;
    
    @Column(name = "reference_produit", length = 100)
    private String referenceProduit;
    
    @Column(nullable = false)
    private Integer quantite;
    
    @Column(name = "prix_unitaire", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixUnitaire;
    
    @Column(name = "prix_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixTotal;
    
    @Column(name = "taux_commission", precision = 5, scale = 2)
    private BigDecimal tauxCommission;
    
    @Column(name = "montant_commission", precision = 10, scale = 2)
    private BigDecimal montantCommission;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commande", nullable = false)
    private Commandes commande;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit")
    private Produits produit;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_variante")
    private VariantesProduits variante;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_boutique")
    private Boutiques boutique;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (prixTotal == null && prixUnitaire != null && quantite != null) {
            prixTotal = prixUnitaire.multiply(new BigDecimal(quantite));
        }
    }
}