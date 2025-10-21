package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "utilisations_coupons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisationsCoupons implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisation")
    private Long idUtilisation;
    
    @Column(name = "montant_reduction", precision = 10, scale = 2)
    private BigDecimal montantReduction;
    
    @Column(name = "date_utilisation")
    private LocalDateTime dateUtilisation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_coupon")
    private Coupons coupon;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commande")
    private Commandes commande;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur")
    private Utilisateurs utilisateur;
    
    @PrePersist
    protected void onCreate() {
        dateUtilisation = LocalDateTime.now();
    }
}