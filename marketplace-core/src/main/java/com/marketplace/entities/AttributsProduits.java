package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "attributs_produits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributsProduits implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_attribut")
    private Long idAttribut;
    
    @Column(name = "nom_attribut", nullable = false, length = 100)
    private String nomAttribut;
    
    @Column(name = "valeur_attribut", nullable = false, columnDefinition = "TEXT")
    private String valeurAttribut;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit", nullable = false)
    private Produits produit;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}