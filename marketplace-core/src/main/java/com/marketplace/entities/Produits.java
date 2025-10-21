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
@Table(name = "produits", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_boutique", "slug"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produits implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit")
    private Long idProduit;
    
    @Column(name = "nom_produit", nullable = false, length = 255)
    private String nomProduit;
    
    @Column(nullable = false, length = 255)
    private String slug;
    
    @Column(name = "reference_produit", unique = true, length = 100)
    private String referenceProduit;
    
    @Column(name = "code_barre", length = 100)
    private String codeBarre;
    
    @Column(name = "description_courte", columnDefinition = "TEXT")
    private String descriptionCourte;
    
    @Column(name = "description_longue", columnDefinition = "TEXT")
    private String descriptionLongue;
    
    @Column(name = "prix_unitaire", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixUnitaire;
    
    @Column(name = "prix_promo", precision = 10, scale = 2)
    private BigDecimal prixPromo;
    
    @Column(name = "date_debut_promo")
    private LocalDateTime dateDebutPromo;
    
    @Column(name = "date_fin_promo")
    private LocalDateTime dateFinPromo;
    
    @Column(name = "quantite_stock")
    private Integer quantiteStock = 0;
    
    @Column(name = "stock_minimum")
    private Integer stockMinimum = 5;
    
    @Column(name = "stock_maximum")
    private Integer stockMaximum = 1000;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal poids;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal longueur;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal largeur;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal hauteur;
    
    @Column(name = "meta_titre", length = 255)
    private String metaTitre;
    
    @Column(name = "meta_description", columnDefinition = "TEXT")
    private String metaDescription;
    
    @Column(name = "meta_keywords", columnDefinition = "TEXT")
    private String metaKeywords;
    
    @Column(name = "est_actif")
    private Boolean estActif = true;
    
    @Column(name = "est_en_promo")
    private Boolean estEnPromo = false;
    
    @Column(name = "est_nouveau")
    private Boolean estNouveau = true;
    
    @Column(name = "est_en_vedette")
    private Boolean estEnVedette = false;
    
    @Column(name = "nombre_vues")
    private Integer nombreVues = 0;
    
    @Column(name = "nombre_ventes")
    private Integer nombreVentes = 0;
    
    @Column(name = "note_moyenne", precision = 3, scale = 2)
    private BigDecimal noteMoyenne = BigDecimal.ZERO;
    
    @Column(name = "nombre_avis")
    private Integer nombreAvis = 0;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_boutique", nullable = false)
    private Boutiques boutique;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categorie")
    private CategoriesProduits categorie;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_marque")
    private Marques marque;
    
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ImagesProduits> images;
    
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VariantesProduits> variantes;
    
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AttributsProduits> attributs;
    
    @OneToMany(mappedBy = "produit", fetch = FetchType.LAZY)
    private List<AvisProduits> avis;
    
    @ManyToMany(mappedBy = "produits")
    private List<ListesSouhaits> listesSouhaits;
    
    @ManyToMany(mappedBy = "produits")
    private List<Promotions> promotions;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateModification = LocalDateTime.now();
        if (quantiteStock == null) quantiteStock = 0;
        if (stockMinimum == null) stockMinimum = 5;
        if (stockMaximum == null) stockMaximum = 1000;
        if (estActif == null) estActif = true;
        if (estEnPromo == null) estEnPromo = false;
        if (estNouveau == null) estNouveau = true;
        if (estEnVedette == null) estEnVedette = false;
        if (nombreVues == null) nombreVues = 0;
        if (nombreVentes == null) nombreVentes = 0;
        if (noteMoyenne == null) noteMoyenne = BigDecimal.ZERO;
        if (nombreAvis == null) nombreAvis = 0;
    }
    
    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }
}