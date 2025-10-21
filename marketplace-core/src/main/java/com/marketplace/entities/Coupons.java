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
@Table(name = "coupons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupons implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coupon")
    private Long idCoupon;
    
    @Column(name = "code_coupon", unique = true, nullable = false, length = 50)
    private String codeCoupon;
    
    @Column(name = "type_reduction", nullable = false, length = 20)
    private String typeReduction;
    
    @Column(name = "valeur_reduction", nullable = false, precision = 10, scale = 2)
    private BigDecimal valeurReduction;
    
    @Column(name = "montant_minimum", precision = 10, scale = 2)
    private BigDecimal montantMinimum;
    
    @Column(name = "montant_maximum_reduction", precision = 10, scale = 2)
    private BigDecimal montantMaximumReduction;
    
    @Column(name = "nombre_utilisations_max")
    private Integer nombreUtilisationsMax;
    
    @Column(name = "nombre_utilisations_actuel")
    private Integer nombreUtilisationsActuel = 0;
    
    @Column(name = "nombre_utilisations_par_client")
    private Integer nombreUtilisationsParClient = 1;
    
    @Column(name = "est_actif")
    private Boolean estActif = true;
    
    @Column(name = "est_public")
    private Boolean estPublic = true;
    
    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;
    
    @Column(name = "date_fin", nullable = false)
    private LocalDateTime dateFin;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_boutique")
    private Boutiques boutique;
    
    @OneToMany(mappedBy = "coupon", fetch = FetchType.LAZY)
    private List<UtilisationsCoupons> utilisations;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (nombreUtilisationsActuel == null) nombreUtilisationsActuel = 0;
        if (nombreUtilisationsParClient == null) nombreUtilisationsParClient = 1;
        if (estActif == null) estActif = true;
        if (estPublic == null) estPublic = true;
    }
}