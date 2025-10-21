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
@Table(name = "commandes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commandes implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande")
    private Long idCommande;
    
    @Column(name = "numero_commande", unique = true, nullable = false, length = 50)
    private String numeroCommande;
    
    @Column(name = "montant_produits", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantProduits;
    
    @Column(name = "montant_livraison", precision = 10, scale = 2)
    private BigDecimal montantLivraison = BigDecimal.ZERO;
    
    @Column(name = "montant_remise", precision = 10, scale = 2)
    private BigDecimal montantRemise = BigDecimal.ZERO;
    
    @Column(name = "montant_taxes", precision = 10, scale = 2)
    private BigDecimal montantTaxes = BigDecimal.ZERO;
    
    @Column(name = "montant_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTotal;
    
    @Column(name = "nom_client", length = 200)
    private String nomClient;
    
    @Column(name = "email_client", length = 255)
    private String emailClient;
    
    @Column(name = "telephone_client", length = 20)
    private String telephoneClient;
    
    @Column(name = "mode_livraison", length = 100)
    private String modeLivraison;
    
    @Column(name = "frais_livraison", precision = 10, scale = 2)
    private BigDecimal fraisLivraison = BigDecimal.ZERO;
    
    @Column(name = "date_livraison_estimee")
    private LocalDate dateLivraisonEstimee;
    
    @Column(name = "date_livraison_reelle")
    private LocalDateTime dateLivraisonReelle;
    
    @Column(name = "numero_tracking", length = 100)
    private String numeroTracking;
    
    @Column(name = "note_client", columnDefinition = "TEXT")
    private String noteClient;
    
    @Column(name = "note_admin", columnDefinition = "TEXT")
    private String noteAdmin;
    
    @Column(name = "date_commande")
    private LocalDateTime dateCommande;
    
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur")
    private Utilisateurs utilisateur;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_statut")
    private StatutsCommande statut;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_adresse_livraison")
    private Adresses adresseLivraison;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_adresse_facturation")
    private Adresses adresseFacturation;
    
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LignesCommande> lignesCommande;
    
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistoriqueCommandes> historiques;
    
    @OneToMany(mappedBy = "commande", fetch = FetchType.LAZY)
    private List<Paiements> paiements;
    
    @PrePersist
    protected void onCreate() {
        dateCommande = LocalDateTime.now();
        dateModification = LocalDateTime.now();
        if (montantLivraison == null) montantLivraison = BigDecimal.ZERO;
        if (montantRemise == null) montantRemise = BigDecimal.ZERO;
        if (montantTaxes == null) montantTaxes = BigDecimal.ZERO;
        if (fraisLivraison == null) fraisLivraison = BigDecimal.ZERO;
    }
    
    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }
}