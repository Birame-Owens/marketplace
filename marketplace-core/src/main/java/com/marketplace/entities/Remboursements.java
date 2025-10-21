package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "remboursements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Remboursements implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_remboursement")
    private Long idRemboursement;
    
    @Column(name = "montant_rembourse", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantRembourse;
    
    @Column(name = "raison_remboursement", columnDefinition = "TEXT")
    private String raisonRemboursement;
    
    @Column(name = "statut_remboursement", length = 50)
    private String statutRemboursement;
    
    @Column(name = "numero_transaction_remboursement", length = 255)
    private String numeroTransactionRemboursement;
    
    @Column(name = "date_demande")
    private LocalDateTime dateDemande;
    
    @Column(name = "date_validation")
    private LocalDateTime dateValidation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paiement")
    private Paiements paiement;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commande")
    private Commandes commande;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur_demande")
    private Utilisateurs utilisateurDemande;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur_validation")
    private Utilisateurs utilisateurValidation;
    
    @PrePersist
    protected void onCreate() {
        dateDemande = LocalDateTime.now();
    }
}