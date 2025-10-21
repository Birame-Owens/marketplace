package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "avis_boutiques")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvisBoutiques implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avis_boutique")
    private Long idAvisBoutique;
    
    @Column(name = "note_produits")
    private Integer noteProduits;
    
    @Column(name = "note_communication")
    private Integer noteCommunication;
    
    @Column(name = "note_livraison")
    private Integer noteLivraison;
    
    @Column(name = "note_globale", precision = 3, scale = 2)
    private BigDecimal noteGlobale;
    
    @Column(columnDefinition = "TEXT")
    private String commentaire;
    
    @Column(name = "est_verifie")
    private Boolean estVerifie = false;
    
    @Column(name = "est_publie")
    private Boolean estPublie = true;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_boutique", nullable = false)
    private Boutiques boutique;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur")
    private Utilisateurs utilisateur;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commande")
    private Commandes commande;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (estVerifie == null) estVerifie = false;
        if (estPublie == null) estPublie = true;
    }
}