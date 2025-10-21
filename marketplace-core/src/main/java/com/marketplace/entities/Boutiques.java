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
@Table(name = "boutiques")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Boutiques implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_boutique")
    private Long idBoutique;
    
    @Column(name = "nom_boutique", nullable = false, length = 255)
    private String nomBoutique;
    
    @Column(unique = true, nullable = false, length = 255)
    private String slug;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(length = 500)
    private String logo;
    
    @Column(length = 500)
    private String banniere;
    
    @Column(name = "email_boutique", length = 255)
    private String emailBoutique;
    
    @Column(name = "telephone_boutique", length = 20)
    private String telephoneBoutique;
    
    @Column(name = "adresse_boutique", columnDefinition = "TEXT")
    private String adresseBoutique;
    
    @Column(length = 100)
    private String ville;
    
    @Column(length = 100)
    private String pays = "Sénégal";
    
    @Column(name = "numero_siret", length = 50)
    private String numeroSiret;
    
    @Column(name = "numero_tva", length = 50)
    private String numeroTva;
    
    @Column(name = "raison_sociale", length = 255)
    private String raisonSociale;
    
    @Column(name = "est_active")
    private Boolean estActive = true;
    
    @Column(name = "est_verifiee")
    private Boolean estVerifiee = false;
    
    @Column(name = "commission_pourcentage", precision = 5, scale = 2)
    private BigDecimal commissionPourcentage = new BigDecimal("10.00");
    
    @Column(name = "note_moyenne", precision = 3, scale = 2)
    private BigDecimal noteMoyenne = BigDecimal.ZERO;
    
    @Column(name = "nombre_avis")
    private Integer nombreAvis = 0;
    
    @Column(name = "nombre_ventes")
    private Integer nombreVentes = 0;
    
    @Column(name = "horaires_ouverture", columnDefinition = "JSONB")
    private String horairesOuverture;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    @Column(name = "date_verification")
    private LocalDateTime dateVerification;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_gerant")
    private Utilisateurs gerant;
    
    @OneToMany(mappedBy = "boutique", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Produits> produits;
    
    @OneToMany(mappedBy = "boutique", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DocumentsBoutique> documents;
    
    @ManyToMany
    @JoinTable(
        name = "boutique_categories",
        joinColumns = @JoinColumn(name = "id_boutique"),
        inverseJoinColumns = @JoinColumn(name = "id_categorie_boutique")
    )
    private List<CategoriesBoutiques> categories;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateModification = LocalDateTime.now();
        if (estActive == null) estActive = true;
        if (estVerifiee == null) estVerifiee = false;
        if (pays == null) pays = "Sénégal";
        if (commissionPourcentage == null) commissionPourcentage = new BigDecimal("10.00");
        if (noteMoyenne == null) noteMoyenne = BigDecimal.ZERO;
        if (nombreAvis == null) nombreAvis = 0;
        if (nombreVentes == null) nombreVentes = 0;
    }
    
    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }
}