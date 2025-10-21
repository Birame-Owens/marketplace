package com.marketplace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "statistiques_boutiques", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_boutique", "date_statistique"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatistiquesBoutiques implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statistique")
    private Long idStatistique;
    
    @Column(name = "date_statistique", nullable = false)
    private LocalDate dateStatistique;
    
    @Column(name = "nombre_commandes")
    private Integer nombreCommandes = 0;
    
    @Column(name = "chiffre_affaires", precision = 10, scale = 2)
    private BigDecimal chiffreAffaires = BigDecimal.ZERO;
    
    @Column(name = "montant_commissions", precision = 10, scale = 2)
    private BigDecimal montantCommissions = BigDecimal.ZERO;
    
    @Column(name = "nombre_produits_vendus")
    private Integer nombreProduitsVendus = 0;
    
    @Column(name = "nombre_vues_produits")
    private Integer nombreVuesProduits = 0;
    
    @Column(name = "nombre_nouveaux_clients")
    private Integer nombreNouveauxClients = 0;
    
    @Column(name = "nombre_clients_recurrents")
    private Integer nombreClientsRecurrents = 0;
    
    @Column(name = "taux_conversion", precision = 5, scale = 2)
    private BigDecimal tauxConversion = BigDecimal.ZERO;
    
    @Column(name = "panier_moyen", precision = 10, scale = 2)
    private BigDecimal panierMoyen = BigDecimal.ZERO;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_boutique", nullable = false)
    private Boutiques boutique;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (nombreCommandes == null) nombreCommandes = 0;
        if (chiffreAffaires == null) chiffreAffaires = BigDecimal.ZERO;
        if (montantCommissions == null) montantCommissions = BigDecimal.ZERO;
        if (nombreProduitsVendus == null) nombreProduitsVendus = 0;
        if (nombreVuesProduits == null) nombreVuesProduits = 0;
        if (nombreNouveauxClients == null) nombreNouveauxClients = 0;
        if (nombreClientsRecurrents == null) nombreClientsRecurrents = 0;
        if (tauxConversion == null) tauxConversion = BigDecimal.ZERO;
        if (panierMoyen == null) panierMoyen = BigDecimal.ZERO;
    }
}