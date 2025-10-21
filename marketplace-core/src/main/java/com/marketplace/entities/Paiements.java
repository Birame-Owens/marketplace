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
@Table(name = "paiements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paiements implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paiement")
    private Long idPaiement;
    
    @Column(name = "numero_transaction", unique = true, length = 255)
    private String numeroTransaction;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montant;
    
    @Column(length = 10)
    private String devise = "XOF";
    
    @Column(name = "statut_paiement", nullable = false, length = 50)
    private String statutPaiement;
    
    @Column(name = "donnees_paiement", columnDefinition = "JSONB")
    private String donneesPaiement;
    
    @Column(name = "dernier_chiffres_carte", length = 4)
    private String dernierChiffresCarte;
    
    @Column(name = "type_carte", length = 50)
    private String typeCarte;
    
    @Column(name = "date_paiement")
    private LocalDateTime datePaiement;
    
    @Column(name = "date_validation")
    private LocalDateTime dateValidation;
    
    @Column(name = "message_erreur", columnDefinition = "TEXT")
    private String messageErreur;
    
    @Column(columnDefinition = "TEXT")
    private String note;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commande")
    private Commandes commande;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_methode")
    private MethodesPaiement methode;
    
    @OneToMany(mappedBy = "paiement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Remboursements> remboursements;
    
    @PrePersist
    protected void onCreate() {
        datePaiement = LocalDateTime.now();
        if (devise == null) devise = "XOF";
    }
}