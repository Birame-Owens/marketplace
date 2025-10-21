package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "avis_produits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvisProduits implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avis")
    private Long idAvis;
    
    @Column(nullable = false)
    private Integer note;
    
    @Column(length = 255)
    private String titre;
    
    @Column(columnDefinition = "TEXT")
    private String commentaire;
    
    @Column(name = "est_verifie")
    private Boolean estVerifie = false;
    
    @Column(name = "est_signale")
    private Boolean estSignale = false;
    
    @Column(name = "est_publie")
    private Boolean estPublie = true;
    
    @Column(name = "reponse_vendeur", columnDefinition = "TEXT")
    private String reponseVendeur;
    
    @Column(name = "date_reponse")
    private LocalDateTime dateReponse;
    
    @Column(name = "nombre_votes_utile")
    private Integer nombreVotesUtile = 0;
    
    @Column(name = "nombre_votes_inutile")
    private Integer nombreVotesInutile = 0;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit", nullable = false)
    private Produits produit;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur")
    private Utilisateurs utilisateur;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commande")
    private Commandes commande;
    
    @OneToMany(mappedBy = "avis", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ImagesAvis> images;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateModification = LocalDateTime.now();
        if (estVerifie == null) estVerifie = false;
        if (estSignale == null) estSignale = false;
        if (estPublie == null) estPublie = true;
        if (nombreVotesUtile == null) nombreVotesUtile = 0;
        if (nombreVotesInutile == null) nombreVotesInutile = 0;
    }
    
    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }
}