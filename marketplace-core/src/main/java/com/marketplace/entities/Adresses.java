package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "adresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adresses implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adresse")
    private Long idAdresse;
    
    @Column(name = "type_adresse", length = 50)
    private String typeAdresse; // 'livraison', 'facturation', 'principale'
    
    @Column(name = "nom_complet", length = 200)
    private String nomComplet;
    
    @Column(length = 20)
    private String telephone;
    
    @Column(name = "adresse_ligne1", nullable = false, length = 255)
    private String adresseLigne1;
    
    @Column(name = "adresse_ligne2", length = 255)
    private String adresseLigne2;
    
    @Column(nullable = false, length = 100)
    private String ville;
    
    @Column(name = "code_postal", nullable = false, length = 20)
    private String codePostal;
    
    @Column(length = 100)
    private String region;
    
    @Column(nullable = false, length = 100)
    private String pays = "Sénégal";
    
    @Column(name = "est_par_defaut")
    private Boolean estParDefaut = false;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    // Relation ManyToOne avec Utilisateur
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateurs utilisateur;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (estParDefaut == null) estParDefaut = false;
        if (pays == null) pays = "Sénégal";
    }
}