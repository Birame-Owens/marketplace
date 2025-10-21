package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "variantes_produits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariantesProduits implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_variante")
    private Long idVariante;
    
    @Column(name = "nom_variante", nullable = false, length = 100)
    private String nomVariante;
    
    @Column(name = "valeur_variante", nullable = false, length = 100)
    private String valeurVariante;
    
    @Column(name = "reference_variante", length = 100)
    private String referenceVariante;
    
    @Column(name = "prix_supplement", precision = 10, scale = 2)
    private BigDecimal prixSupplement = BigDecimal.ZERO;
    
    @Column(name = "quantite_stock")
    private Integer quantiteStock = 0;
    
    @Column(name = "image_variante", length = 500)
    private String imageVariante;
    
    @Column(name = "est_active")
    private Boolean estActive = true;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit", nullable = false)
    private Produits produit;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (prixSupplement == null) prixSupplement = BigDecimal.ZERO;
        if (quantiteStock == null) quantiteStock = 0;
        if (estActive == null) estActive = true;
    }
}