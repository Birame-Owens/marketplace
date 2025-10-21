package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "factures")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factures implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_facture")
    private Long idFacture;
    
    @Column(name = "numero_facture", unique = true, nullable = false, length = 50)
    private String numeroFacture;
    
    @Column(name = "montant_ht", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantHt;
    
    @Column(name = "montant_tva", precision = 10, scale = 2)
    private BigDecimal montantTva = BigDecimal.ZERO;
    
    @Column(name = "montant_ttc", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTtc;
    
    @Column(name = "taux_tva", precision = 5, scale = 2)
    private BigDecimal tauxTva = new BigDecimal("18.00");
    
    @Column(name = "nom_client", length = 200)
    private String nomClient;
    
    @Column(name = "adresse_facturation", columnDefinition = "TEXT")
    private String adresseFacturation;
    
    @Column(name = "email_client", length = 255)
    private String emailClient;
    
    @Column(name = "nom_boutique", length = 255)
    private String nomBoutique;
    
    @Column(name = "adresse_boutique", columnDefinition = "TEXT")
    private String adresseBoutique;
    
    @Column(name = "siret_boutique", length = 50)
    private String siretBoutique;
    
    @Column(name = "chemin_pdf", length = 500)
    private String cheminPdf;
    
    @Column(name = "date_emission", nullable = false)
    private LocalDate dateEmission;
    
    @Column(name = "date_echeance")
    private LocalDate dateEcheance;
    
    @Column(name = "date_paiement")
    private LocalDate datePaiement;
    
    @Column(name = "statut_facture", length = 50)
    private String statutFacture = "emise";
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commande")
    private Commandes commande;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_boutique")
    private Boutiques boutique;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur")
    private Utilisateurs utilisateur;
    
    @OneToMany(mappedBy = "facture", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AvoirFactures> avoirs;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (dateEmission == null) dateEmission = LocalDate.now();
        if (montantTva == null) montantTva = BigDecimal.ZERO;
        if (tauxTva == null) tauxTva = new BigDecimal("18.00");
        if (statutFacture == null) statutFacture = "emise";
    }
}