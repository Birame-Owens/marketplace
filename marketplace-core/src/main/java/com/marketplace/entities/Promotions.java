package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "promotions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotions implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_promotion")
    private Long idPromotion;
    
    @Column(name = "nom_promotion", nullable = false, length = 255)
    private String nomPromotion;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "type_promotion", length = 50)
    private String typePromotion;
    
    @Column(name = "pourcentage_reduction", precision = 5, scale = 2)
    private BigDecimal pourcentageReduction;
    
    @Column(name = "est_active")
    private Boolean estActive = true;
    
    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;
    
    @Column(name = "date_fin", nullable = false)
    private LocalDateTime dateFin;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_boutique")
    private Boutiques boutique;
    
    @ManyToMany
    @JoinTable(
        name = "produits_promotion",
        joinColumns = @JoinColumn(name = "id_promotion"),
        inverseJoinColumns = @JoinColumn(name = "id_produit")
    )
    private List<Produits> produits;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (estActive == null) estActive = true;
    }
}