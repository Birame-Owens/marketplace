package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "configurations_boutique")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationsBoutique implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_configuration")
    private Long idConfiguration;
    
    @Column(name = "livraison_gratuite_au_dessus", precision = 10, scale = 2)
    private BigDecimal livraisonGratuiteAuDessus;
    
    @Column(name = "delai_preparation_jours")
    private Integer delaiPreparationJours = 2;
    
    @Column(name = "retour_autorise")
    private Boolean retourAutorise = true;
    
    @Column(name = "delai_retour_jours")
    private Integer delaiRetourJours = 14;
    
    @Column(name = "modes_paiement_acceptes", columnDefinition = "JSONB")
    private String modesPaiementAcceptes;
    
    @Column(name = "email_notification", length = 255)
    private String emailNotification;
    
    @Column(name = "telephone_notification", length = 20)
    private String telephoneNotification;
    
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_boutique", unique = true)
    private Boutiques boutique;
    
    @PrePersist
    protected void onCreate() {
        dateModification = LocalDateTime.now();
        if (delaiPreparationJours == null) delaiPreparationJours = 2;
        if (retourAutorise == null) retourAutorise = true;
        if (delaiRetourJours == null) delaiRetourJours = 14;
    }
    
    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }
}